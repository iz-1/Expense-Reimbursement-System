package com.revature;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.TimeUnit;

@ExtendWith(VertxExtension.class)
class ReimbursementVerticleTest {

  @BeforeEach
  public void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new ReimbursementVerticle(Main.getContextTable()), testContext.succeeding(id -> testContext.completeNow()));
  }

  @AfterEach
  public void verticle_deployed(Vertx vertx, VertxTestContext testContext) throws Throwable {
    testContext.completeNow();
  }

  @Test
  public void test_response(Vertx vertx, VertxTestContext testContext) {
    //Handler<RoutingContext> handler = Handler<RoutingContext> {}

    DeploymentOptions dp = new DeploymentOptions();
    dp.setMaxWorkerExecuteTimeUnit(TimeUnit.MINUTES);
    dp.setMaxWorkerExecuteTime(20);

    vertx.deployVerticle(new ReimbursementVerticle(Main.getContextTable()));
  }

  @Test
  public void testGetQueryParam() {

  }
}
