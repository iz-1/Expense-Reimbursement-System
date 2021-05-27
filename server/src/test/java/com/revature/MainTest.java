package com.revature;

import com.revature.dao.EmployeeDao;
import com.revature.dao.ManagerDao;
import com.revature.dao.ReimbursementDao;
import com.revature.model.*;
import com.revature.service.EmployeeService;
import com.revature.service.ManagerService;
import com.revature.service.ReimbursementService;
import com.revature.utils.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class MainTest {

  @BeforeEach
  public void setup() {
    Main.contextTable = Main.getContextTable();

    EmployeeDao eDao = Main.getInterface(ContextType.EmployeeDao);
    eDao.dropAllCollection();
    ManagerDao mDao = Main.getInterface(ContextType.ManagerDao);
    mDao.dropAllCollection();
    ReimbursementDao rDao = Main.getInterface(ContextType.ReimbursementDao);
    rDao.dropAllCollection();

    ManagerService mServ = Main.getInterface(ContextType.ManagerService);
    Manager m = new Manager("mgr@work.com", "managers", "boss", "man", "123 street", 2064451234L);
    m = mServ.create(m);

    EmployeeService eServ = Main.getInterface(ContextType.EmployeeService);
    Employee e0 = new Employee("jsmith@work.com", "smithjjj", "John", "Smith", "411 Avenue NE", 2063316789L);
    e0 = eServ.create(e0);
    Employee e1 = new Employee("jdoe@work.com", "doedoedeo", "Jane", "Doe", "91st Street", 2064451234L);
    e1 = eServ.create(e1);

    ReimbursementService rServ = Main.getInterface(ContextType.ReimbursementService);
    Reimbursement r0 = Mock.getMockObjectFromJson("reimbursement", Reimbursement.class);
    r0.setRequesterId(e0.getHexId());
    r0 = rServ.create(r0);
    rServ.reviewRequest(r0, RequestStatus.denied.toString(), m.getHexId());

    Reimbursement r1 = Mock.getMockObjectFromJson("reimbursement", Reimbursement.class);
    //r1.updateDates();
    r1.setRequesterId(e0.getHexId());
    rServ.create(r1);

    Reimbursement r2 = new Reimbursement(e1.getHexId(),
      new ArrayList<>(Arrays.asList(
        new Expense("Breakfast", 800L, "5-3-21"),
        new Expense("Lunch", 1000L, "5-3-21"),
        new Expense("Dinner", 1500L, "5-3-21")
      )
      )
    );
    rServ.create(r2);
  }

  @Test
  public void testApp(){
    Main.main(null);
  }
}
