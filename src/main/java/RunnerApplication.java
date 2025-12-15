import fr.diginamic.databases.MongoManager;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.io.InputStream;
import java.util.Arrays;
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

            Map<String, Object> createdMany = mongoManager.createManyDocuments(documents);
            System.out.println("createdMany: " + createdMany + " à bien été créer avec succès");*/

        // Mise à jour d'une ou plusieurs données
            // Mes à jour un champ du document
            Map<String, Object> updateOne = mongoManager.updateOneDocument(
                    new Document("_id", new ObjectId("693c3c330e2a3a3c80ef90a6")),
                    new Document("$set", new Document("price", 1.25))
            );
            System.out.println("update_many_documents: " + updateOne);

            // Rajoute un champ au document
            Map<String, Object> updateOneField = mongoManager.updateOneDocument(
                    new Document(documentWatermelon),
                    new Document("$set", new Document("description", "This fruits have been import of Hawaii"))
            );
            System.out.println("update_one_document: " + updateOneField);

            // Supprimer une propriété d'un produit existant
            Map<String, Object> updatedOneProperties = mongoManager.updateOneDocument(
                    new Document(documentWatermelon),
                    new Document("$unset", new Document("description", ""))
            );
            System.out.println("update_one_document: " + updatedOneProperties);

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

        // Suppression de document
            // Supprimer un fruit grâce à son id
            Map<String, Object> deleteWitheId = mongoManager.deleteOneDocument(new Document("_id", new ObjectId("693c3c330e2a3a3c80ef9091")));
            System.out.println("delete_one_document: " + deleteWitheId);

            // Supprimer plusieurs fruits et légume avec la couleur qui sont verts
            Map<String, Object > deleteWitheColor = mongoManager.deleteManyDocuments(
                    new Document("$or", Arrays.asList(
                            new Document("color", "Green"),
                            new Document("alternative_colors", "Green")
                    ))
            );
            System.out.println("delete_many_documents: " + deleteWitheColor);

        // Requête de recherche
            // Rechercher tous les éléments rouges
            List<Document> searchWitheColorRed = mongoManager.readManyDocuments(
                    new Document("$or", Arrays.asList(
                            new Document("color", "Red"),
                            new Document("alternative_colors", "Red")
                    )),
                    new Document("name", 1).append("color", 1).append("alternative_colors", 1).append("_id", 0)
            );
            System.out.println("read_many_documents: " + searchWitheColorRed);

            // Rechercher tous les éléments dont le prix est inférieur à 2.00 et affiche uniquement le nom et le prix
            List<Document> searchWithePriceInf2 = mongoManager.readManyDocuments(
                    new Document("price", new Document("$lt", 2.00)),
                    new Document("name", 1).append("price", 1).append("_id", 0)
            );
            System.out.println("read_many_documents: " + searchWithePriceInf2);

            // Rechercher le fruit qui a la plus grande quantité.
            Document searchWitheMoreQuantity = mongoManager.readOneDocumentAvecTri(new Document(), new Document("quantity", -1));
            System.out.println("read_one_documents: " + searchWitheMoreQuantity);


            // Fermeture de la connexion
            mongoManager.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
