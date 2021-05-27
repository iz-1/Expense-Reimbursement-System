package com.revature.dao;

import com.mongodb.client.*;
import com.mongodb.client.result.UpdateResult;
import com.revature.exception.ItemNotFoundException;
import com.revature.exception.ItemExistsException;
import com.revature.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.eq;

/**
 * class interacts with mongo database to persist data
 */
abstract public class MongoDao<T> implements Closeable {
    protected MongoConnector connector;
    protected MongoCollection items;
    protected MongoDatabase db;

    /**
     *
     * @param connector interface to access database
     * @param databaseName database to retrieve/store data
     */
    public <T> MongoDao(MongoConnector connector, String databaseName, Class<T> clazz) {
        this.connector = connector;
        this.db = this.connector.getClient().getDatabase(databaseName);
        this.items = this.db
                      .getCollection(clazz.getSimpleName(), clazz);
    }

    /**
     * get all instances of class type
     * @param clazz class to retrive
     * @param <T> Class of objects
     */
    protected <T> void getItems(Class<T> clazz) {
        this.items = db
                .getCollection(clazz.getSimpleName(), clazz);
    }

  /**
   * get filter by name
   * @param name
   * @param val
   * @param <TItem>
   * @return
   */
    protected  <TItem> Bson getFilterByName(String name, Object val) {
      switch(name) {
        case "email":
          return eq("email", (String)val);
        case "id":
          return eq("_id", ((ObjectId)val).toHexString());
        case "requesterId":
          return eq("requesterId", (String)val);
      }
      return null;
    }

  /**
   * clear all databases
   */
  public void dropAllCollection() {
    this.items.drop();
  }

  /**
   *
   * @throws IOException
   */
  @Override
  public void close() throws IOException {
      connector.close();
  }

  /**
   *
   * @param item
   * @return
   * @throws ItemExistsException
   */
  protected T create(T item) throws ItemExistsException {
    ObjectId id = new ObjectId();
    ((MongoObject)item).setId(id.toHexString());
    this.items.insertOne(item);
    Bson filter = getFilterByName("id", id);
    return retrieve(filter);
  }

  /**
   *
   * @param existFilter
   * @return
   * @throws ItemNotFoundException
   */
  protected T retrieve(Bson existFilter) throws ItemNotFoundException {
    FindIterable result = this.items.find(existFilter);
    T doc = null;
    try {
      doc = (T) result.first();
    }catch(Exception ex) {
      System.out.println(ex.getMessage());
      throw new ItemNotFoundException("No item found " + existFilter.toString());
    }
    if(doc == null)
      throw new ItemNotFoundException("No item found " + existFilter.toString());
    return doc;
  }

  /**
   * get all items from collection
   * @param clazz class type
   * @param <T> templated object type
   * @return list of all items from collection
   * @throws ItemNotFoundException
   */
  protected <T> List<T> retrieveAll(Class<T> clazz) throws ItemNotFoundException {
    getItems(clazz);
    FindIterable<T> res = this.items.find();
    return (List<T>) StreamSupport.stream(res.spliterator(), true).collect(Collectors.toList());
  }

  /**
   * replaces an item in the database with new item
   * @param item
   * @param existFilter
   * @throws ItemNotFoundException
   */
  protected void replace(T item, Bson existFilter) throws ItemNotFoundException {
    UpdateResult res = this.items.replaceOne(existFilter, item);
  }
}
