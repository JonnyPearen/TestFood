package com.mycompany.testfood;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.mycompany.testfood.MongoStuff.AsyncResponse;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.testfood.MongoStuff.RequestTask;
import org.json.JSONArray;
import org.json.JSONObject;


public class IngredientsSearch extends ActionBarActivity implements AsyncResponse {
    static final String MY_FLURRY_APIKEY = "F7MTPVYXJMH6DCHMN9S3";
    AutoCompleteTextView mEdit;
    //arraylist that stores chosen ingredients
    private List<String> ingredients_array_list = new ArrayList<>();
   // public String[] ingredients = new String[3];
    public ArrayList<String> ingredients = new ArrayList();
    RequestTask r1 = new RequestTask();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_search);

        //configure Flurry
        FlurryAgent.setLogEnabled(false);
        //init Flurry
        FlurryAgent.init(this, MY_FLURRY_APIKEY);

        mEdit   = (AutoCompleteTextView)findViewById(R.id.autocomplete_ingredient);


        //String[] ingredients = getResources().getStringArray(R.array.ingredients_array);

        //Get the available ingredients from mongoDB
        r1.execute("https://api.mongolab.com/api/1/databases/testfooddb/collections/ingredientstest?apiKey=a5Eqs4CeKR0S2cTdOULWMjoxG1kiyoBe");
        r1.delegate = this;

        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredients);
        mEdit.setAdapter(adapter);



    }

    //parse the JSON ingredients from MongoDB into the ingredients array
    public void processFinish(String output){
        try{
            JSONArray json = new JSONArray(output);
            //ingredients = new String[55];
            for(int i=0;i<json.length();i++) {

                JSONObject e = json.getJSONObject(i);
                ingredients.add(e.getJSONObject("document").getString("ingredientName"));
            }
        } catch(Exception e){
            Toast.makeText(this, "Ingredient parsing failed", Toast.LENGTH_SHORT).show();
        }
    }
    //fills the ingredients listview with with strings from the passed in array list
    private void populateIngredientsList(List<String> a){

       ListView lv = (ListView) findViewById(R.id.ingredientsListView);

        // This is the array adapter, takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and the
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                a );
        lv.setAdapter(arrayAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        FlurryAgent.logEvent("Ingredients_Search_Read");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent actionBarBtnIntent;

        switch (item.getItemId()) {
            case R.id.btn_searchPage:
                return true;
            case R.id.btn_detailsPage:
                actionBarBtnIntent = new Intent(this, recipeDetails.class);
                FlurryAgent.logEvent("AB_Details");
                startActivity(actionBarBtnIntent);
                return true;
            case R.id.btn_favouritesPage:
                actionBarBtnIntent = new Intent(this, Favourites.class);
                FlurryAgent.logEvent("AB_Favourites");
                startActivity(actionBarBtnIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToFavs(View view) {

        Intent goToFavourites = new Intent(this, Favourites.class);
        startActivity(goToFavourites);
        Toast.makeText(this, "You Chose Favourites", Toast.LENGTH_SHORT).show();
    }

    public void searchRecipies(View view) {
        Intent searchRecipies = new Intent(this, SearchResults.class);
        startActivity(searchRecipies);
    }

    public void addIngredient(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEdit.getWindowToken(), 0);
        String inputText = mEdit.getText().toString();
        if(!inputText.equals("")){
            Toast.makeText(this, inputText, Toast.LENGTH_SHORT).show();
            ingredients_array_list.add(inputText);
            populateIngredientsList(ingredients_array_list);
            mEdit.setText("");
        }
    }

}