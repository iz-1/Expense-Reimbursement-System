package com.revature.dao;

import com.mongodb.ConnectionString;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.Closeable;
import java.io.IOException;

/**
 * Class handles the settings an connection to the mongo database
 */
public class MongoConnector implements Closeable {
    private MongoClient client;
    private MongoClientSettings settings;
    final public static String serverUrl = "mongodb://localhost:27017/";
    final public static String dbName = "hr";

    /**
     * private constructor requires the use of createConnector to instantiate
     * @param settings
     */
    private MongoConnector(MongoClientSettings settings) {
        this.settings = settings;
    }

    /**
     *
     * @param settings connection settings
     * @return
     */
    public static MongoConnector createConnector(MongoClientSettings settings) {
        MongoConnector connector = new MongoConnector(settings);
        connector.createClient();
        return connector;
    }

    /**
     * create the client with settings
     * @return mongoclient
     */
    public MongoClient createClient() {
        client = MongoClients.create(this.settings); // com.mongodb.client.MongoClients*/
        return client;
    }

    /**
     * getter
     * @return
     */
    public MongoClient getClient() {
        return client;
    }

    //url ex:       "mongodb://localhost:27017/bank"
    //pojoPath ex:  "com.revature.model"

  /**
   *
   * @param url
   * @return
   */
    public static MongoClientSettings defaultSettings(String url) {
        // generate codec from pojo declarations
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        // register codec
        CodecRegistry registry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        // create settings
        ConnectionString connectString = new ConnectionString(url);
        // create settings
        return MongoClientSettings.builder()
                .applyConnectionString(connectString)
                .retryWrites(true)
                .codecRegistry(registry)
                .build();
    }

    /**
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        client.close();
    }
}
