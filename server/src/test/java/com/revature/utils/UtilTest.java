package com.revature.utils;

import com.revature.model.Employee;
import com.revature.model.Manager;
import com.revature.model.Reimbursement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {



  @Test
  void jsonToPojoEmployee() {
    String type = "employee";
    Employee e = Mock.getMockObjectFromJson(type, Employee.class);
    assertNotNull(e);
  }

  @Test
  void jsonToPojoReimbursement() {
    String type = "reimbursement";
    Reimbursement e = Mock.getMockObjectFromJson(type, Reimbursement.class);
    assertNotNull(e);
  }

  @Test
  void jsonToPojoManager() {
    String type = "manager";
    Manager e = Mock.getMockObjectFromJson(type, Manager.class);
    assertNotNull(e);
  }
}
