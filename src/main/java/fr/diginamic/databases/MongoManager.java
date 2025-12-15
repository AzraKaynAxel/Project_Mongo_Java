package fr.diginamic.databases;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * @return
     *
     * Rourne la liste des BDD
     */
    public List<String> listDatabases() {
        try {
            List<String> databases = new ArrayList<>();
            MongoIterable<String> dbNames = this.client.listDatabaseNames();
            dbNames.into(databases);
            return databases;
        }  catch (Exception e) {
            throw new RuntimeException("Unable to list databases", e);
        }
    }

    /**
     * @return
     *
     * Rourne la liste des collections
     */
    public List<String> listCollections() {
        try {
            List<String> collections = new ArrayList<>();
            MongoIterable<String> collNames = this.database.listCollectionNames();
            collNames.into(collections);
            return collections;
        } catch (Exception e) {
            throw new RuntimeException("Unable to list collections", e);
        }
    }

    /**
     * @param document
     * @return
     *
     * Viens faire une insertion unique d'un document
     */
    // InsertOne
    public Map<String, Object> createOneDocument(Document document) {
        try {
            InsertOneResult result = this.collection.insertOne(document);
            Map<String, Object> response = new HashMap<>();
            response.put("acknowlegded", result.wasAcknowledged());
            response.put("inserteId", result.getInsertedId());
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Unable to insert document", e);
        }
    }

    /**
     * @param documents
     * @return
     *
     * Viens faire plusieurs insertions de document
     */
    // InsertMany
    public Map<String, Object> createManyDocuments(
            List<Document> documents
    ) {
        try {
            InsertManyResult result =
                    this.collection.insertMany(documents);
            Map<String, Object> response = new HashMap<>();
            response.put("acknowledged", result.wasAcknowledged());
            response.put("insertedIds", result.getInsertedIds());
            return response;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to insert documents", e
            );
        }
    }

    /**
     * @param query
     * @param update
     * @return
     *
     * Pour fair une update sur un document
     */
    // UpdateOne
    public Map<String, Object> updateOneDocument(Document query, Document update) {
        try {
            UpdateResult result = this.collection.updateOne(query, update);
            Map<String, Object> response = new HashMap<>();
            response.put("acknowledged", result.wasAcknowledged());
            response.put("matchedCount", result.getMatchedCount());
            response.put("modifiedCount", result.getModifiedCount());
            response.put("upsertedId", result.getUpsertedId());
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Unable to update document", e);
        }
    }

    /**
     * @param query
     * @param update
     * @return
     *
     * Pour faire plusieur Update sur documents
     */
    // UpdateMany
    public Map<String, Object> updateManyDocuments(Document query, Document update) {
        try {
            UpdateResult result = this.collection.updateMany(query, update);
            Map<String, Object> response = new HashMap<>();
            response.put("acknowledged", result.wasAcknowledged());
            response.put("matchedCount", result.getMatchedCount());
            response.put("modifiedCount", result.getModifiedCount());
            response.put("upsertedId", result.getUpsertedId());
            return response;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to update documents", e
            );
        }
    }

    /**
     * @param query
     * @return
     *
     * Suppression d'un document
     */
    // DeleteOne
    public Map<String, Object> deleteOneDocument(Document query) {
        try {
            DeleteResult result = this.collection.deleteOne(query);
            Map<String, Object> response = new HashMap<>();
            response.put("acknowledged", result.wasAcknowledged());
            response.put("deletedCount", result.getDeletedCount());
            return response;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to delete document", e
            );
        }
    }

    /**
     * @param query
     * @return
     *
     * Suppression de plusieurs documents
     */
    // DeleteMany
    public Map<String, Object> deleteManyDocuments(Document query) {
        try {
            DeleteResult result = this.collection.deleteMany(query);
            Map<String, Object> response = new HashMap<>();
            response.put("acknowledged", result.wasAcknowledged());
            response.put("deletedCount", result.getDeletedCount());
            return response;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to delete documents", e
            );
        }
    }

    /**
     * @param query
     * @param mySort
     * @return
     *
     * Cherche un document selon un tri spécifique
     */
    // FindOne
    public Document readOneDocumentAvecTri(Document query, Document mySort) {
        try {
            return this.collection.find(query).sort(mySort).first();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to read document", e
            );
        }
    }

    /**
     * @param query
     * @param projection
     * @return
     *
     * Cherche plusieurs documents et filtre l'affichage de ce que l'on veut
     */
    // Find
    public List<Document> readManyDocuments(Document query, Document projection) {
        try {
            List<Document> results = new ArrayList<>();
            FindIterable<Document> documents =
                    this.collection.find(query).projection(projection);
            documents.into(results);
            return results;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to read documents", e
            );
        }
    }


    /**
     * Getter for database
     *
     * @return database
     */
    public MongoDatabase getDatabase() {
        return database;
    }

    /**
     * Setter for database
     *
     * @param dbName
     */
    public void setDatabase(String dbName) {
        this.database = this.client.getDatabase(dbName);

        // Réaffectation obligatoire de la collection
        this.collection = this.database.getCollection(this.collection.getNamespace().getCollectionName());
    }

    /**
     * Getter for collection
     *
     * @return collection
     */
    public MongoCollection<Document> getCollection() {
        return collection;
    }

    /**
     * Setter for collection
     *
     * @param collName
     */
    public void setCollection(String collName) {
        this.collection = this.database.getCollection(collName);
    }
}
