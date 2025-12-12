package fr.diginamic.databases;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoManager {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;


    /**
     * Constructor for: MongoManager
     *
     * @param uri
     * @param dbName
     * @param collName
     *
     * Création de la connexion
     */
    public MongoManager(String uri, String dbName, String collName) {
        ConnectionString connectionString = new ConnectionString(uri);

        // les paramètres de la base de données Mongo
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();

        client = MongoClients.create(settings);

        try {
            Document dbPing = this.client.getDatabase("admin")
                    .runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment: " + dbPing + ". You successfully connected to MongoDB!");
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect to MongoDB", e);
        }
        this.database = client.getDatabase(dbName);
        this.collection = this.database.getCollection(collName);
    }

    /**
     *  Permet de fermer la connection à la BDD
     */
    public void closeConnection() {
        if (this.client != null) {
            this.client.close();
            System.out.println("Connection closed");
        }
    }
}
