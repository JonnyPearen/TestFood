package com.mycompany.testfood;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.net.URLEncoder;

import com.flurry.android.FlurryAgent;
import com.mycompany.testfood.MongoStuff.AsyncResponse;
import com.mycompany.testfood.MongoStuff.Ingredient;
import com.mycompany.testfood.MongoStuff.RequestTask;
import com.mycompany.testfood.MongoStuff.SaveAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class SearchResults extends ActionBarActivity implements AsyncResponse{
    //Stores the chosen ingredients. Passed in form the Ingredient search page
    private ArrayList<String> chosen_Ingredients;
    //holds the names of the recipes retrieved
    private ArrayList resultRecipes = new ArrayList<String>();
    RequestTask getResultsTask = new RequestTask();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //gets ingredients picked from Ingredients search page via intent
        chosen_Ingredients = getIntent().getStringArrayListExtra("ingredients_array_list");

        //runs the search query
        getResultsTask.execute(getSearchQuery(chosen_Ingredients));
        getResultsTask.delegate = this;

    }
    /*builds URL to retrieve recipes based on the arraylist of ingredients passed in*/
    private String getSearchQuery(ArrayList<String> ingredientsList){
        String SearchURL = "https://api.mongolab.com/api/1/databases/testfooddb/collections/recipes?q=";
        String searchQuery = "{\"ingredients\":{\"$in\":[";
        try{
            for(String ingredient : ingredientsList){
                searchQuery = searchQuery + "\"" + ingredient + "\"";
                if(ingredient != ingredientsList.get(ingredientsList.size() - 1)){
                    searchQuery = searchQuery + ",";
                }
            }
            searchQuery = searchQuery + "]}}";
            SearchURL = SearchURL + URLEncoder.encode(searchQuery, "UTF-8") + "&apiKey=a5Eqs4CeKR0S2cTdOULWMjoxG1kiyoBe";
            //Toast.makeText(this, SearchURL, Toast.LENGTH_SHORT).show();
            } catch(Exception e){
            Toast.makeText(this, "sending request failed", Toast.LENGTH_SHORT).show();
        }
        return SearchURL;
    }
/*processes the retrieved recipes. adds all recipe names to the resultRecipes arraylist. */
    public void processFinish(String output){
        try{
            JSONArray json = new JSONArray(output);
            for(int i=0;i<json.length();i++) {

                JSONObject e = json.getJSONObject(i);
                resultRecipes.add(e.getString("name"));
            }
        } catch(Exception e){
            Toast.makeText(this, "No Entries Found", Toast.LENGTH_SHORT).show();
        }
        populateIngredientsList(resultRecipes);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
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
                actionBarBtnIntent = new Intent(this, recipeDetails.class);
                startActivity(actionBarBtnIntent);
                return true;
            case R.id.btn_favouritesPage:
                actionBarBtnIntent = new Intent(this, Favourites.class);
                startActivity(actionBarBtnIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FlurryAgent.logEvent("Database_pls");

    }

    private void populateIngredientsList(List<String> a){

        ListView lv = (ListView) findViewById(R.id.resultsListView);

        // This is the array adapter, takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and the
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                a );
        lv.setAdapter(arrayAdapter);
    }

}
