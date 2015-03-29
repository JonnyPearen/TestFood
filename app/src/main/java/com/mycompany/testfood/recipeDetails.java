package com.mycompany.testfood;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.mycompany.testfood.MongoStuff.AsyncResponse;
import com.mycompany.testfood.MongoStuff.RequestTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;


public class recipeDetails extends ActionBarActivity implements AsyncResponse{
    RequestTask getResultsTask = new RequestTask();
    String recipeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        FlurryAgent.logEvent("Details_Read");
        recipeName = getIntent().getStringExtra("recipeName");
        String recipeName = getIntent().getStringExtra("recipeName");
        //runs the search query
        getResultsTask.execute(getRecipeURL(recipeName));
        getResultsTask.delegate = this;

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
            String description = "nothing";
            description = e.getString("description");
            Toast.makeText(this, description, Toast.LENGTH_SHORT).show();
            /*
            for (int i = 0; i < json.length(); i++) {
                JSONObject e = json.getJSONObject(i);
                resultRecipes.add(e.getString("name"));
            }
            */
        } catch (Exception e) {
            Toast.makeText(this, "No Entries Found", Toast.LENGTH_SHORT).show();
        }

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
