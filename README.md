# Project_Mongo_Java 🍃

## Description 📋

**Résumé** 📝: Ce dépôt contient un projet Maven Java pour gérer une base de données MongoDB avec le driver MongoDB Java Driver Synchrone.

**Prérequis** ✅:

- **Code**: structure de projet Java/Maven prête.

- **Configuration du pom.xml**: dépendance **MongoDB Java Driver (sync)** version **5.6.2** configurée ⚙️.

- **Configuration MongoDB**: `application.properties` présent dans `src/main/resources` avec les paramètres de connexion (host, database, collection) ⚙️.

## Détails des fonctionnalités implémentées 🚧 :

### Implémentation de la classe `MongoManager` 🗄️

- ### Gestion de la connexion 🔌:

  - `MongoManager(String uri, String dbName, String collName)` - Constructeur pour établir la connexion à MongoDB avec vérification via ping, support de l'API Server v1.

  - `closeConnection()` - Fermeture propre de la connexion à la base de données.

  - `listDatabases()` - Retourne la liste de toutes les bases de données disponibles.

  - `listCollections()` - Retourne la liste de toutes les collections de la base de données courante.

- ### Opérations CRUD:
   
   - **Insertion ➕** :
      - `createOneDocument(Document document)` - Insertion d'un document unique dans la collection, retourne un Map contenant l'acknowledgment et l'ID du document inséré.

      - `createManyDocuments(List<Document> documents)` - Insertion de plusieurs documents dans la collection, retourne un Map contenant l'acknowledgment et la liste des IDs des documents insérés.

  - **Mise à jour** ✏️ :

      - `updateOneDocument(Document query, Document update)` - Mise à jour d'un seul document correspondant à la requête (support des opérateurs MongoDB comme `$set`, `$unset`, `$push`, `$pull`, `$pop`), retourne un Map avec matchedCount, modifiedCount, etc.

      - `updateManyDocuments(Document query, Document update)` - Mise à jour de plusieurs documents correspondant à la requête, retourne un Map avec les statistiques de mise à jour.

  - **Suppression** 🗑️ :

      - `deleteOneDocument(Document query)` - Suppression d'un seul document correspondant à la requête, retourne un Map contenant l'acknowledgment et le nombre de documents supprimés.

      - `deleteManyDocuments(Document query)` - Suppression de plusieurs documents correspondant à la requête, retourne un Map contenant l'acknowledgment et le nombre de documents supprimés.

  - **lecture** 🔍 :

      - `readOneDocumentAvecTri(Document query, Document mySort)` - Recherche d'un document avec critères de recherche et tri personnalisé (ex: tri par quantité décroissante).

      - `readManyDocuments(Document query, Document projection)` - Recherche de plusieurs documents avec critères de recherche et projection pour filtrer les champs retournés (ex: afficher uniquement name et price).

- **Getters/Setters** 🔧:

  - `getDatabase()` / `setDatabase(String dbName)` - Accès et modification de la base de données courante.

  - `getCollection()` / `setCollection(String collName)` - Accès et modification de la collection courante.

*voir `src/main/java/fr/diginamic/databases/MongoManager.java`* 📝.

### Implémentation de l'application de démonstration 🎯

- **RunnerApplication.java** - Application principale de démonstration des fonctionnalités MongoDB :

  - Chargement de la configuration depuis `application.properties`.

  - Exemples d'utilisation des opérations CRUD (insertion, mise à jour, suppression).

  - Exemples de manipulation de tableaux dans les documents (`$push`, `$pull`, `$pop`, `$each`).

  - Exemples de requêtes de recherche avec filtres et projections.

  - Exemples d'utilisation des opérateurs MongoDB (`$set`, `$unset`, `$or`, `$lt`, etc.).

*voir `src/main/java/RunnerApplication.java`* 🏃.

## Configuration ⚙️

Le fichier `application.properties` contient la configuration MongoDB :

```properties
mongodb.host=localhost:monLocalHost
mongodb.database=nameDB
mongodb.collection=nameCollection
```

Vous pouvez modifier ces valeurs selon votre environnement MongoDB.

*voir `src/main/resources/application.properties`* 📄.

## Technologies utilisées 🛠️

- **Java** 21
- **Maven** pour la gestion des dépendances
- **MongoDB Java Driver (sync)** 5.6.2

## Structure du projet 📁

```
Project_TpMongoJava/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   │   ├── fr/
    │   │   │   └── diginamic/
    │   │   │       └── databases/
    │   │   │           └── MongoManager.java
    │   │   └── RunnerApplication.java
    │   └── resources/
    │       └── application.properties
    └── test/
        └── java/
```
```