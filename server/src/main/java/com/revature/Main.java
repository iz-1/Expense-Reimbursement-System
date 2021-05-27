package com.revature;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClientSettings;
import com.revature.dao.EmployeeDao;
import com.revature.dao.ManagerDao;
import com.revature.dao.MongoConnector;
import com.revature.dao.ReimbursementDao;
import com.revature.service.EmployeeService;
import com.revature.service.ManagerService;
import com.revature.service.ReimbursementService;
import io.vertx.core.Vertx;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * server app main
 */
public class Main {
  public static Map<ContextType, Object> contextTable = null;

  /**
   * get interfaces
   * @param type context to get
   * @param <T> templated object type
   * @return context requested
   */
  public static <T> T getInterface(ContextType type) {return (T) contextTable.get(type); }

  /**
   * run our server and keep application alive with loop
   * @param args unused
   */
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    Main.initContextTable();
    vertx.deployVerticle(new ReimbursementVerticle(contextTable));

    Scanner s = new Scanner(System.in);
    while(s.nextLine().isEmpty());
  }

  /**
   * inits the context table if not already initialized
   */
  public static void initContextTable() {
    if(contextTable == null)
      contextTable = getContextTable();
  }

  /**
   * create context modules for application
   * @return map of contexts
   */
  public static Map<ContextType, Object> getContextTable() {
    Map< ContextType, Object> contextTable = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    contextTable.put(ContextType.Jackson, mapper);

    MongoClientSettings settings = MongoConnector.defaultSettings(MongoConnector.serverUrl + MongoConnector.dbName);
    MongoConnector connector = MongoConnector.createConnector(settings);

    EmployeeDao eDao = new EmployeeDao(connector, MongoConnector.dbName);
    contextTable.put(ContextType.EmployeeDao, eDao);
    contextTable.put(ContextType.EmployeeService, new EmployeeService(eDao));

    ManagerDao mDao = new ManagerDao(connector, MongoConnector.dbName);
    contextTable.put(ContextType.ManagerDao, mDao);
    contextTable.put(ContextType.ManagerService, new ManagerService(mDao));

    ReimbursementDao rDao = new ReimbursementDao(connector, MongoConnector.dbName);
    contextTable.put(ContextType.ReimbursementDao, rDao);
    contextTable.put(ContextType.ReimbursementService, new ReimbursementService(rDao));

    return contextTable;
  }
}
