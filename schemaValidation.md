db.createCollection("products", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["name", "category", "color", "price", "quantity"],
            properties: {
                name: {
                    bsonType: "string",
                    description: "Le nom est obligatoire et doit être une chaîne de caractères." 
                },
                category: {
                    bsonType: "string",
                    description: "L'objet doit avoir une catégorie." 
                },
                color: {
                    bsonType: "string",
                    description: "L'âge doit être compris entre 18 et 100."
                },
                price: {
                    bsonType: "double",
                    pattern: "^[0-9]{1,2}.[0-9]{0,2}$",
                    maximum: 10,
                    description: "Le nombre est strictement positif, il ne peut pas être 0 et ne dépasse pas 20." 
                },
                quantity: {
                    bsonType: "int",
                    minimum: 1,
                    maximum: 200,
                    description: "le quantité est comprise entre 1 et 200 inclus."
                }
            }
        }
    }
})