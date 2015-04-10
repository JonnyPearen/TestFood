package com.mycompany.testfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.flurry.android.FlurryAgent;
import com.mycompany.testfood.MongoStuff.RequestTask;
import com.mycompany.testfood.MongoStuff.AsyncResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class recipeDetails extends ActionBarActivity implements AsyncResponse{
    SQLiteDatabase testDB;
    RequestTask getResultsTask = new RequestTask();
    TextView recipeTitle;
    TextView recipeDescription;
    TextView ingredientHeading;
    TextView instructionHeading;
    String recipeName;

    ArrayList<String> recipe_steps;
    ArrayList<String> ingredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        FlurryAgent.logEvent("Details_Read");

        ingredientsList = new ArrayList<>();
        recipe_steps = new ArrayList<>();

        ingredientHeading = (TextView) findViewById(R.id.textview_ingredientHeader);
        instructionHeading = (TextView) findViewById(R.id.textview_instructionsHeader);
        recipeTitle = (TextView) findViewById(R.id.textview_recipeTitle);
        recipeDescription = (TextView) findViewById(R.id.textview_recipeDescription);

        recipeName = getIntent().getStringExtra("recipeName");
        //runs the search query
        getResultsTask.execute(getRecipeURL(recipeName));
        getResultsTask.delegate = this;
        makeDB();

    }

    public class FeedReaderDbHelper extends SQLiteOpenHelper {

        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;

        public FeedReaderDbHelper(Context context) {
            super(context, "DB3", null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE NEW (_id INTEGER PRIMARY KEY, Food TEXT);");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL("DROP TABLE IF EXISTS TEST");
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

    public void makeDB() throws NullPointerException {
        FeedReaderDbHelper helper = new FeedReaderDbHelper(getApplicationContext());

        //SQLiteDatabase testDB = helper.getWritableDatabase();

        testDB = this.openOrCreateDatabase("database4", MODE_PRIVATE, null);

   /* Create a Table in the Database, if it doesn't already exist */

        testDB.execSQL("CREATE TABLE IF NOT EXISTS "
                + "TEST"
                + " (_id VARCHAR, Food TEXT);");

    }

    public void addToDb(){
        ContentValues values = new ContentValues();
        values.put("_id", 1);
        values.put("Food", recipeName);

        testDB.insert("TEST", null, values);

        Toast.makeText(this, recipeName + " added to favourites!", Toast.LENGTH_LONG).show();

    }

    public void removeFromDb(){
        testDB.delete("TEST", "Food='" + recipeName + "'", null);
        Toast.makeText(this, recipeName + " removed from favourites!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_details, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent actionBarBtnIntent;

        /*noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        switch (item.getItemId()) {
            case R.id.btn_searchPage:
                actionBarBtnIntent = new Intent(this, IngredientsSearch.class);
                startActivity(actionBarBtnIntent);
                return true;
            case R.id.btn_detailsPage:
                return true;
            case R.id.btn_favouritesPage:
                actionBarBtnIntent = new Intent(this, Favourites.class);
                startActivity(actionBarBtnIntent);
                return true;
            case R.id.action_addFavourite:
                addToDb();
                return true;
            case R.id.action_removeFavourite:
                removeFromDb();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateIngredientsList(ArrayList<String> a) {
        ListView lv_ingredients = (ListView) findViewById(R.id.list_ingredients);

        // This is the array adapter, takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and the
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                a);
        lv_ingredients.setAdapter(arrayAdapter);
    }

    private void populateInstructionsList(ArrayList<String> a) {
        ListView lv_instructions = (ListView) findViewById(R.id.list_instructions);

        // This is the array adapter, takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and the
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                a);
        lv_instructions.setAdapter(arrayAdapter);
    }


    private String getRecipeURL(String recipeName) {
        //add to oncreate to get recipe name from intent:
        //String recipeName = getIntent().getStringExtra("recipeName");

        String SearchURL = "https://api.mongolab.com/api/1/databases/testfooddb/collections/recipes?q=";
        try{
            SearchURL = SearchURL + URLEncoder.encode("{\"name\":\"" + recipeName + "\"}", "UTF-8") + "&apiKey=a5Eqs4CeKR0S2cTdOULWMjoxG1kiyoBe";
        } catch (Exception e) {
            Toast.makeText(this, "making request string failed", Toast.LENGTH_SHORT).show();
        }
        return SearchURL;
    }


    public void processFinish(String output) {
        try {
            JSONArray json = new JSONArray(output);
            //loops through all the recipes returned.
            JSONObject e = json.getJSONObject(0);
            String name = "";
            JSONArray json_ingredients;
            JSONArray json_instructions;
            String description = "";
            name = e.getString("name");
            description = e.getString("description");
            String recipeTitleString = name;
            String ingredientsHeaderString = "Ingredients";
            String instructionsHeadingString = "Instructions";

            recipeTitle.setText(recipeTitleString);
            recipeDescription.setText(description);
            ingredientHeading.setText(ingredientsHeaderString);
            instructionHeading.setText(instructionsHeadingString);

            json_ingredients = e.getJSONArray("ingredients");
            json_instructions = e.getJSONArray("instructions");

            for (int i = 0; i < json_ingredients.length(); i++) {
                String f = json_ingredients.getString(i);
                ingredientsList.add(f);

            }
            populateIngredientsList(ingredientsList);

            for (int i = 0; i < json_instructions.length(); i++) {
                String pee = json_instructions.getString(i);
                recipe_steps.add(pee);

            }
            populateInstructionsList(recipe_steps);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "No Entries Found", Toast.LENGTH_SHORT).show();
        }

    }

}
