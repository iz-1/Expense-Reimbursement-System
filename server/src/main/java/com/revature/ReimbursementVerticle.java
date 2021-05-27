package com.revature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.ItemNotFoundException;
import com.revature.exception.LoginFailureException;
import com.revature.model.Employee;
import com.revature.model.Manager;
import com.revature.model.Reimbursement;
import com.revature.service.EmployeeService;
import com.revature.service.ManagerService;
import com.revature.service.ReimbursementService;
import com.revature.service.Service;
import io.vertx.core.*;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.javatuples.Pair;

import java.io.Closeable;
import java.text.MessageFormat;
import java.util.*;

/**
 * implementation of reimbursement server
 */
public class ReimbursementVerticle extends AbstractVerticle {
  final private int port = 8888;

  final private static Logger logger = LogManager.getLogger(ReimbursementVerticle.class);

  private static Map< ContextType, Object> contextTable = new HashMap<>();

  /**
   * ctor
   * @param contextTable application module contexts
   */
  public ReimbursementVerticle(Map< ContextType, Object> contextTable) {
    super();
    this.contextTable = contextTable;
  }

  /**
   * get a module
   * @param type context
   * @param <T> templated object type
   * @return context requested
   */
  public <T> T getInterface(ContextType type) {return (T) contextTable.get(type); }

  /**
   * vertx stop method/shudown
   * @param stopPromise
   * @throws Exception
   */
  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    super.stop(stopPromise);
    for(Object c : contextTable.values()) {
      if(c instanceof Closeable)
        ((Closeable)c).close();
    }
  }

  /**
   * vertx init
   * @param vertx
   * @param context
   */
  @Override
  public void init(Vertx vertx, Context context) {
    this.vertx = vertx;
    this.context = context;
  }

  /**
   * setup server to handle get/post requests
   * @param startPromise
   * @throws Exception
   */
  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    // Create router
    Router router = Router.router(vertx);

    //enable cors
    router.route().handler(CorsHandler.create(".*."));

    // route /static/*
    //router.route().handler(StaticHandler.create());

    // routing home
    //router.route("/home/*").handler(this::displayHome);

    // setup handler for post
    //router.post("/").consumes("*/json").handler(this::handlePost);
    router.route().handler(BodyHandler.create());
    router.post("/").handler(this::handlePost);

    router.route("/quit/").handler( context -> {
      vertx.close();
    });

    // Mount the handler for all incoming request at every path and HTTP method
    router.route().handler(context -> handleGet(context));

    HttpServer server = vertx.createHttpServer();

    //String response = new JsonObject().put("Hello from Vert.x!", true).encode();

    server.requestHandler(router);

/*    server.requestHandler(req -> {
      req.response().end(response);
    });*/

    server.listen(port, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        logger.info(MessageFormat.format("HTTP server started on port {0}", port));
      } else {
        startPromise.fail(http.cause());
      }
    });

  }

  /**
   * validate password
   * @param queryParams
   * @param idParam user id
   * @param service service to retrieve user from
   * @param <T> template type of object
   * @return json objet results if successfull
   * @throws LoginFailureException
   */
  public <T> JsonObject validatePassword(MultiMap queryParams, String idParam, Service<T> service) throws LoginFailureException {
    T item = service.retrieve(idParam);
    String password = getQueryParam(queryParams, "password");
    if(!((Employee)item).getPassword().equals(password))
      throw new LoginFailureException(MessageFormat.format("login attempt: {0}:{1}", idParam, password));
    JsonObject obj = new JsonObject();
    obj.put("id", ((Employee)item).getHexId());
    obj.put("type", ((Employee)item).getType());
    return obj;
  }

  /**
   * handle all get requests
   * @param context
   */
  public void handleGet(RoutingContext context) {
    // address
    String address = context.request().connection().remoteAddress().toString();
    // get the query parameter "name"
    MultiMap queryParams = context.queryParams();
    HttpServerResponse resp = context.response();

    ObjectMapper mapper = getInterface(ContextType.Jackson);
    String json = "";
    Pair<String, String> result = getParameter(queryParams);
    String idParam = result.getValue1();
    boolean getAll = idParam.equals("all");

    // get can request 1/more items
    if(result != null) {
      try {
        switch (result.getValue0()) {
          case "emp": {
            EmployeeService service = getInterface(ContextType.EmployeeService);
            if(getAll) {
              List<Employee> emps = service.retrieveAll();
              json = mapper.writeValueAsString(emps);
            } else {
              Employee e = service.retrieve(new ObjectId(idParam)); //hexId
              json = mapper.writeValueAsString(e);
            }
          } break;
          case "mgr": {
            ManagerService service = getInterface(ContextType.ManagerService);
            Manager m = service.retrieve(new ObjectId(idParam));
            json = mapper.writeValueAsString(m);
          } break;
          case "req": {
            ReimbursementService service = getInterface(ContextType.ReimbursementService);
            if(getAll) {
              List<Reimbursement> reqs = service.retrieveAll();
              json = mapper.writeValueAsString(reqs);
            } else {
              Reimbursement r = service.retrieve(new ObjectId(idParam));
              if(r == null)
                throw new ItemNotFoundException("Not Found " + idParam);

              List<Reimbursement> listResult = new ArrayList<>();
              listResult.add(r);
              json = mapper.writeValueAsString(listResult);
            }
          } break;
          case "empReq": {
            ReimbursementService service = getInterface(ContextType.ReimbursementService);
            List<Reimbursement> listResult = service.retriveAllbyUser(idParam);
            json = mapper.writeValueAsString(listResult);
          } break;
          case "login": {
            EmployeeService service = getInterface(ContextType.EmployeeService);
            JsonObject obj = validatePassword(queryParams, idParam, service);
            json = mapper.writeValueAsString(obj);
          } break;
          case "mgrlogin": {
            ManagerService service = getInterface(ContextType.ManagerService);
            JsonObject obj = validatePassword(queryParams, idParam, service);
            json = mapper.writeValueAsString(obj);
          } break;
        }

        resp.setStatusCode(200);
        resp.putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
        resp.end(json);
      } catch (ItemNotFoundException ex) {
        logger.warn(ex.getMessage());
      } catch (LoginFailureException ex) {
        logger.warn(ex.getMessage());
        JsonObject o = new JsonObject().put("401", "Invalid Credentials");
        resp.setStatusCode(401);
        resp.end(o.encodePrettily());
      } catch (JsonProcessingException ex) {
        logger.warn(ex.getMessage());
      }

      if(json.isEmpty()) {
        JsonObject o = new JsonObject().put("400", "Invalid Request");
        resp.setStatusCode(400);
        resp.end(o.encodePrettily());
      }
    }else{

      String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
      // write json response
      context.json(
        new JsonObject()
          .put("name", name)
          .put("address", address)
          .put("message", "Hello " + name + " connected from " + address)
      );
    }
  }



  /**
   * find query parameter will return partial matches
   * @param queryParams
   * @param term to find parameter of
   * @return found parameter
   */
  private String getQueryParam(MultiMap queryParams, String term) {
    return queryParams.contains(term) ? queryParams.get(term) : null;
  }

  /**
   * get parameter
   * @param queryParams
   * @return associated parameter
   */
  private Pair<String, String> getParameter(MultiMap queryParams) {

    String empReqVal = getQueryParam(queryParams, "empReq");
    if(empReqVal != null)
      return new Pair<>("empReq", empReqVal);
    String mgrLoginVal = getQueryParam(queryParams, "mgrlogin");
    if(mgrLoginVal != null)
      return new Pair<>("mgrlogin", mgrLoginVal);
    String loginVal = getQueryParam(queryParams, "login");
    if(loginVal != null)
      return new Pair<>("login", loginVal);
    String empVal = getQueryParam(queryParams, "emp");
    if(empVal != null)
      return new Pair<>("emp", empVal);
    String mgrVal = getQueryParam(queryParams, "mgr");
    if(mgrVal != null)
      return new Pair<>("mgr", mgrVal);
    String reqVal = getQueryParam(queryParams, "req");
    if(reqVal != null)
      return new Pair<>("req", reqVal);
    return null;
  }

//  private void displayHome(RoutingContext routingContext) {
//    String path = "webroot/static/index.html";
//    logger.info(MessageFormat.format("Path access {0}", path));
//    routingContext.json(new JsonObject().put("Home", "Hi"));
//    routingContext.response().send(path);
//  }

  /**
   * updates user's password
   * @param routingContext context
   * @param jsonObject json object from request
   * @return jsonObject result of update
   * @throws Exception on failure
   */
  private JsonObject handleUpdatePassword(RoutingContext routingContext, JsonObject jsonObject) throws Exception {
    String hexId = jsonObject.getString("id");
    String newPass = jsonObject.getString("password");
    String type = jsonObject.getString("cmdType");

    if (type.equals("employee")) {
      EmployeeService service = getInterface(ContextType.EmployeeService);
      service.updatePass(hexId, newPass);
    } else if (type.equals("manager")) {
      ManagerService service = getInterface(ContextType.ManagerService);
      service.updatePass(hexId, newPass);
    } else {
      throw new Exception("Invalid Type required to update password");
    }
    return new JsonObject().put("Password Updated", hexId);
  }

  /**
   * handle post request for employee
   * @param routingContext context
   * @param jsonString json post string
   * @param cmd command to execute
   * @return result of operation
   */
  private JsonObject employeePost(RoutingContext routingContext, String jsonString, String cmd) {
    EmployeeService service = getInterface(ContextType.EmployeeService);
    ObjectMapper mapper = getInterface(ContextType.Jackson);

    try {
      Employee e = mapper.readValue(jsonString, Employee.class);

      if(cmd.equals("create")) {
        e = service.create(e);
        return new JsonObject().put("Employee Created", e.getHexId());
      }else if(cmd.equals("update")) {
        service.replace(e);
        return new JsonObject().put("Employee Updated", e.getHexId());
      }
    }catch(JsonProcessingException ex){
      System.out.println(ex.getMessage());
    }
    return null;
  }

  /**
   * handle post request for manager
   * @param routingContext context
   * @param jsonString json post string
   * @param cmd command to execute
   * @return result of operation
   */
  private JsonObject managerPost(RoutingContext routingContext, String jsonString, String cmd) {
    ManagerService service = getInterface(ContextType.ManagerService);
    ObjectMapper mapper = getInterface(ContextType.Jackson);
    try{
      Manager m = mapper.readValue(jsonString, Manager.class);

      if(cmd.equals("update")) {
        service.replace(m);
      }

      return new JsonObject().put("Manager Updated", m.getHexId());
    }catch(JsonProcessingException ex){
      System.out.println(ex.getMessage());
    }
    return null;
  }

  /**
   * handle post request for reimbursement request
   * @param routingContext context
   * @param jsonString json post string
   * @param cmd command to execute
   * @return result of operation
   */
  private JsonObject requestPost(RoutingContext routingContext, String jsonString, String cmd) {
    ReimbursementService service = getInterface(ContextType.ReimbursementService);
    ObjectMapper mapper = getInterface(ContextType.Jackson);
    try {
      Reimbursement r = mapper.readValue(jsonString, Reimbursement.class);

      if (cmd.equals("create")) {
        r = service.create(r);
      } else if (cmd.equals("update")) {

      }

      return new JsonObject().put("Request Created", r.getHexId());
    }catch(JsonProcessingException ex){
      System.out.println(ex.getMessage());
    }

    return null;
  }

  /**
   * handle request review
   * @param routingContext context
   * @param jsonObject json from post request
   * @return jsonObject as result
   * @throws Exception failure to update request
   */
  private JsonObject handleReviewRequest(RoutingContext routingContext, JsonObject jsonObject) throws Exception {
    String requestId = jsonObject.getString("id");
    String reviewerId = jsonObject.getString("reviewerId");
    String status = jsonObject.getString("status");
    ReimbursementService service = getInterface(ContextType.ReimbursementService);
    Reimbursement r = service.retrieve(requestId);
    r = service.reviewRequest(r, status, reviewerId);

    if(r == null)
      throw new Exception("Unable to review request");

      return new JsonObject().put("Request Reviewed", requestId);
  }

  // get - get 1 / get all
  // post - create/update

  /**
   * handle post types by command
   * @param routingContext context
   */
  private void handlePost(RoutingContext routingContext) {
    String jsonString = routingContext.getBodyAsString();
    JsonObject jsonObject = routingContext.getBodyAsJson();

    String cmd = jsonObject.getString("command");
    String type = jsonObject.getString("cmdType");

    JsonObject result = null;

    try {

      switch(cmd) {
        case "updatePass":
          result = handleUpdatePassword(routingContext, jsonObject);
          break;
        case "reviewRequest":
          result = handleReviewRequest(routingContext, jsonObject);
          break;
        default: {
          if(type != null)
            switch(type){
              case "employee":
                result = employeePost(routingContext, jsonString, cmd);
                break;
              case "manager":
                result = managerPost(routingContext, jsonString, cmd);
                break;
              case "request":
                result = requestPost(routingContext, jsonString, cmd);
                break;
              default: break;
            }
        } break;
      }
    }catch(Exception ex) {
      ReimbursementVerticle.logger.warn(ex.getMessage());
    }

    HttpServerResponse resp = routingContext.response();
    resp.putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
    if(result == null) {
      result = new JsonObject().put("400", "Invalid Request");
      resp.setStatusCode(400);
    } else {
      resp.setStatusCode(200);
    }
    routingContext.json(result);

    //resp.end(result.encodePrettily());
  }
}
