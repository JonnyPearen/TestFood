package com.mycompany.testfood.MongoStuff;

public class QueryBuilder {


    /*The URL of the mongoDb database collection to access */
    public String buildContactsSaveURL() {
        return "https://api.mongolab.com/api/1/databases/testfooddb/collections/ingredientstest?apiKey=a5Eqs4CeKR0S2cTdOULWMjoxG1kiyoBe";
    }


    //Creates a new entry in the database
    public String createContact(Ingredient ingredient) {
        return String
                .format("{\"document\"  : {\"ingredientName\": \"%s\", "
                                + "\"color\": \"%s\"}, \"safe\" : true}",
                        ingredient.ingredientName, ingredient.color);
    }
}
