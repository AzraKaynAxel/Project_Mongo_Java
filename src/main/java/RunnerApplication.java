import fr.diginamic.databases.MongoManager;
import org.bson.Document;
import org.bson.types.ObjectId;

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

            // Création d'un document
            Document documentWatermelon = new Document("name", "watermelon")
                    .append("category", "Fruits")
                    .append("color", "red")
                    .append("price", 3.78)
                    .append("quantity", 69);
            /*Map<String, Object> createdOne = mongoManager.createOneDocument(documentWatermelon);
            System.out.println("create_one_document: " + createdOne);*/

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

            /*Map<String, Object> createdMany = mongoManager.createManyDocuments(documents);
            System.out.println("createdMany: " + createdMany + " à bien été créer avec succès");*/

        // Mise à jour d'une ou plusieurs données
            // Mes à jour un champ du document
            /*Map<String, Object> updateOne = mongoManager.updateOneDocument(
                    new Document("_id", new ObjectId("693c3c330e2a3a3c80ef90a6")),
                    new Document("$set", new Document("price", 1.25))
            );
            System.out.println("update_many_documents: " + updateOne);*/

            // Rajoute un champ au document
            Map<String, Object> updateOne = mongoManager.updateOneDocument(
                    new Document(documentWatermelon),
                    new Document("$set", new Document("description", "This fruits have been import of Hawaii"))
            );
            System.out.println("update_one_document: " + updateOne);

            // Supprimer une propriété d'un produit existant
            Map<String, Object> updatedOne = mongoManager.updateOneDocument(
                    new Document(documentWatermelon),
                    new Document("$unset", new Document("description", ""))
            );
            System.out.println("update_one_document: " + updatedOne);


            // Rajoute un champ au document
            Map<String, Object> updateOne = mongoManager.updateOneDocument(
                    new Document(documentWatermelon),
                    new Document("$set", new Document("description", "This fruits have been import of Hawaii"))
            );
            System.out.println("update_one_document: " + updateOne);

            // Supprimer une propriété d'un produit existant
            Map<String, Object> updatedOne = mongoManager.updateOneDocument(
                    new Document(documentWatermelon),
                    new Document("$unset", new Document("description", ""))
            );
            System.out.println("update_one_document: " + updatedOne);*/

        // Manipulation de tableau
            // Ajouter un élément à un tableau
            Map<String, Object> updateOneTab = mongoManager.updateOneDocument(
                    new Document("name", "Apple"),
                    new Document("$push", new Document("alternative_colors", "Green"))
            );
            System.out.println("update_one_document: " + updateOneTab);

            // Ajouter plusieurs éléments à un tableau
            Map<String, Object> updatedMany = mongoManager.updateManyDocuments(
                    new Document("name", "Grapes"),
                    new Document("$push", new Document("alternative_colors", new Document("$each", List.of("Yellow", "Green", "Red"))))
            );
            System.out.println("update_many_documents: " + updatedMany);

            // Supprimer un élément d'un tableau
            Map<String, Object> deleteOneElementTab = mongoManager.updateOneDocument(
                    new Document("name", "Apple"),
                    new Document("$pull", new Document("alternative_colors", "Green"))
            );
            System.out.println(deleteOneElementTab);

            // Supprimer le dernier élément d'un tableau
            Map<String, Object> deletedLastElementTab = mongoManager.updateOneDocument(
                    new Document("name", "Grapes"),
                    new Document("$pop", new Document("alternative_colors", 1))
            );
            System.out.println(deletedLastElementTab);


            // Fermeture de la connexion
            mongoManager.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
