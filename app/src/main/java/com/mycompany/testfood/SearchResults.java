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
import android.widget.EditText;
import com.flurry.android.FlurryAgent;
import com.mycompany.testfood.MongoStuff.Ingredient;
import com.mycompany.testfood.MongoStuff.SaveAsyncTask;

import java.net.UnknownHostException;


public class SearchResults extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.btn_searchPage:
                actionBarBtnIntent = new Intent(this, IngredientsSearch.class);
                startActivity(actionBarBtnIntent);
                return true;
            case R.id.action_settings:
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

    public void saveIngredient(View v) throws UnknownHostException {

        EditText ingredientNameBox = (EditText)findViewById(R.id.ingredientNameEditText);
        EditText colorNameBox = (EditText)findViewById(R.id.colorEditText);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        Ingredient ingredient = new Ingredient();

        ingredient.ingredientName = ingredientNameBox.getText().toString();
        ingredient.color = colorNameBox.getText().toString();

        SaveAsyncTask tsk = new SaveAsyncTask();
        tsk.execute(ingredient);

        //displays the contents of the mongodb doc in a webview
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("https://api.mongolab.com/api/1/databases/testfooddb/collections/ingredientstest?apiKey=a5Eqs4CeKR0S2cTdOULWMjoxG1kiyoBe");

    }
}
