package com.mycompany.testfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.flurry.android.FlurryAgent;
import com.mycompany.testfood.MongoStuff.AsyncResponse;
import com.mycompany.testfood.MongoStuff.RequestTask;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/*calls AsyncTask to search for recipes in the mongodb based on the search criteria passed in and displays the results.*/
public class SearchResults extends ActionBarActivity implements AsyncResponse, AdapterView.OnItemClickListener {
    //Stores the chosen ingredients. Passed in form the Ingredient search page
    private ArrayList<String> chosen_Ingredients;
    //holds the names of the recipes retrieved
    private ArrayList resultRecipes = new ArrayList<String>();
    RequestTask getResultsTask = new RequestTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //gets ingredients picked from Ingredients search page via getlistt
        chosen_Ingredients = getIntent().getStringArrayListExtra("ingredients_array_list");

        //runs the search query
        getResultsTask.execute(getSearchQuery(chosen_Ingredients));
        getResultsTask.delegate = this;
        ListView listview = (ListView) findViewById(R.id.resultsListView);
        listview.setOnItemClickListener(this);
    }
    @Override
    /* listens to the reicipe optionss list view, and redirects to the appropriate recipe details page*/
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        //Toast.makeText(this, (String)resultRecipes.get(position), Toast.LENGTH_SHORT).show();
        Intent goToDetails = new Intent();
        goToDetails.setClass(this, recipeDetails.class);
        goToDetails.putExtra("recipeName", (String) resultRecipes.get(position));
        startActivity(goToDetails);
    }

    /*builds URL to retrieve recipes based on the arraylist of ingredients passed in*/
    private String getSearchQuery(ArrayList<String> ingredientsList) {
        String SearchURL = "https://api.mongolab.com/api/1/databases/testfooddb/collections/recipes?q=";
        String searchQuery = "{\"ingredients\":{\"$in\":[";

        try {
            for (String ingredient : ingredientsList) {
                searchQuery = searchQuery + "\"" + ingredient + "\"";
                if (!ingredient.equals(ingredientsList.get(ingredientsList.size() - 1))) {
                    searchQuery = searchQuery + ",";
                }
            }
            searchQuery = searchQuery + "]}}";
            //URLEncoder replaces special characters with their code useful for geting rid of brackets ands stuff
            SearchURL = SearchURL + URLEncoder.encode(searchQuery, "UTF-8") + "&apiKey=a5Eqs4CeKR0S2cTdOULWMjoxG1kiyoBe";
        } catch (Exception e) {
            Toast.makeText(this, "sending request failed", Toast.LENGTH_SHORT).show();
        }
        return SearchURL;
    }

    /*processes the retrieved recipes. adds all recipe names to the resultRecipes arraylist. */
    public void processFinish(String output) {
        try {
            JSONArray json = new JSONArray(output);
            //loops through all the recipes returned.
            // If you need to get more details from the returned recipes this is your loop :)
            for (int i = 0; i < json.length(); i++) {
                JSONObject e = json.getJSONObject(i);
                resultRecipes.add(e.getString("name"));
            }
        } catch (Exception e) {
            Toast.makeText(this, "No Entries Found", Toast.LENGTH_SHORT).show();
        }

        populateIngredientsList(resultRecipes);
    }
    /*inflates the menu bar*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
        return true;
    }

    /*listens to menubar button clicks*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent actionBarBtnIntent;

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

    private void populateIngredientsList(List<String> a) {
        ListView lv = (ListView) findViewById(R.id.resultsListView);

        /*array adaptor for the list of recipes*/
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                a);
        lv.setAdapter(arrayAdapter);
    }
}
