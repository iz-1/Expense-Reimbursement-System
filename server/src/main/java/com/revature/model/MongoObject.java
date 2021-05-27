package com.revature.model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 * model objects that use mongodb's objectid
 */
abstract public class MongoObject {
    @BsonProperty("_id") protected ObjectId id;
    protected String hexId;

  /**
   *
   */
  public MongoObject() {}

  /**
     * getter
     * @return
     */
    public String getId() {
      return hexId;
    }

    /**
     * setter
     * @param hexString
     */
    public void setId(String hexString) {
      this.hexId = hexString;
      this.id = new ObjectId(hexString);
    }

  /**
   *
   * @return
   */
  public String getHexId() {
    return hexId;
  }

  /**
   *
   * @param hexId
   */
//  public void setHexId(String hexId) {
//    this.hexId = hexId;
//  }
}
