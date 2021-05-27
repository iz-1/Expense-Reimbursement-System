package com.revature.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Expense;
import com.revature.model.Reimbursement;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mock classes for creating test objects
 */
public class Mock {
  /**
   * json string for objects
   * @param type
   * @return
   */
    static String getJsonObj(String type) {
      String str = "";
      switch(type){
        case "employee":
          return "{\"firstName\":\"abc\",\"lastName\":\"def\",\"email\":\"def@gmail.com\",\"address\":\"123 street\",\"phoneNumber\":1234567890,\"command\":\"create\",\"type\":\"employee\"}";
        case "reimbursement":
          return "{\"expenses\":[{\"date\":\"2021-05-08\",\"expense\":\"lunch\",\"amount\":1000},{\"date\":\"2021-05-08\",\"expense\":\"drink\",\"amount\":525},{\"date\":\"2021-05-09\",\"expense\":\"cake\",\"amount\":800}],\"total\":2325,\"startDate\":\"2021-05-08\",\"endDate\":\"2021-05-09\",\"command\":\"create\",\"type\":\"request\"}";
      }
      return "";
    }

  /**
   * converts json to pojo
   * @param type value of json string to retrive
   * @param clazz class type
   * @param <T> template object type
   * @return deserialized object
   */
    public static <T> T getMockObjectFromJson(String type, Class<T> clazz) {
      String jsonStr = getJsonObj(type);

      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      try {
        return mapper.readValue(jsonStr, clazz);
      }catch(JsonProcessingException ex) {
        System.out.println(ex.getMessage());
      }
      return null;
    }

  /**
   *
   * @return mock reimbursement
   */
    public static Reimbursement getMockReimbursement() {
      return new Reimbursement(new ObjectId().toHexString(),
        new ArrayList<>(Arrays.asList(
          Mock.getMockExpense()
        )
        )
      );
    }

  /**
   *
   * @return mock expense
   */
    public static Expense getMockExpense() {
      return new Expense("Lunch", 10L, "5-3-21");
    }

  /**
   *
   * @return mock reimbursement list
   */
    public static List<Reimbursement> getMockReimbursmentItems() {
      return new ArrayList(Arrays.asList(
        new Reimbursement(new ObjectId().toHexString(),
          new ArrayList<>(Arrays.asList(
            new Expense("Breakfast", 10L, "5-3-21")
          )
          )
        ),
        new Reimbursement(new ObjectId().toHexString(),
          new ArrayList<>(Arrays.asList(
            new Expense("Breakfast", 10L, "5-3-21"),
            new Expense("Lunch", 10L, "5-3-21")
          )
          )
        ),
        new Reimbursement(new ObjectId().toHexString(),
          new ArrayList<>(Arrays.asList(
            new Expense("Breakfast", 10L, "5-3-21"),
            new Expense("Lunch", 10L, "5-3-21"),
            new Expense("Dinner", 10L, "5-3-21")
          )
          )
        )
      ));
    }
}
