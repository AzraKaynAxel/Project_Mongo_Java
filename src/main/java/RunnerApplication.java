import fr.diginamic.databases.MongoManager;
import org.bson.Document;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class RunnerApplication {
    public static void main(String[] args) {

        try {
            // Chargement de la configuration
            Properties props = new Properties();
            InputStream input = RunnerApplication.class.getClassLoader().getResourceAsStream("application.properties");
            props.load(input);

            String host = props.getProperty("mongodb.host");
            String dbName =  props.getProperty("mongodb.database");
            String collName = props.getProperty("mongodb.collection");

            // Construction de l'URI
            String uri = "mongodb://" + host + "/";

            // Connexion à MongoDB
            MongoManager mongoManager = new MongoManager(uri, dbName, collName);

            // Création de plusieurs documents
            List<Document> documents = List.of(
                    new Document("name", "garlic")
                            .append("category", "Vegetable")
                            .append("color", "white")
                            .append("price", 2.15)
                            .append("quantity", 38),
                    new Document("name", "Turnip")
                            .append("category", "Vegetable")
                            .append("color", "black")
                            .append("price", 4.68)
                            .append("quantity", 7),
                    new Document("name", "Jerusalem artichoke")
                            .append("category", "Vegetable")
                            .append("color", "purple")
                            .append("price", 3.25)
                            .append("quantity", 96)
            );

            Map<String, Object> createdMany = mongoManager.createManyDocuments(documents);
            System.out.println("createdMany: " + createdMany + " à bien été créer avec succès");

            // Fermeture de la connexion
            mongoManager.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
