package com.mycompany.testfood;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class Favourites extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        String test[] = {"Noodles", "Cereal", "Spaghetti", "Bacon", "Banana Bread",
                "Test", "Food", "Please", "Ignore", "One", "Ring", "Cake", "To",
                "Rule", "Them", "All", ".", ".", ".", ".", ".", "Easter Egg!"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, test);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favourites, menu);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goHome(View view) {

        Intent goHome = new Intent(this, MainActivity.class);

        startActivity(goHome);

        Toast.makeText(this, "You Chose Home", Toast.LENGTH_SHORT).show();

    }
}
