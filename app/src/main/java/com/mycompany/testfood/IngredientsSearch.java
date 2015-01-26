package com.mycompany.testfood;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class IngredientsSearch extends ActionBarActivity {
    //arraylist that stores chosen ingredients
    private List<String> ingredients_array_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_search);

/*
        //declare intent
        Intent activityThatCalled = getIntent();


        String searchButtonSelected =
                activityThatCalled.getExtras().getString("callingActivity");

        //get the actual string
        TextView searchButtonSelectedMessage =
                (TextView) findViewById(R.id.searchButtonSelected);

        //append selected string message to the textview on details page.
        searchButtonSelectedMessage.append(" " + searchButtonSelected);
        */
        ingredients_array_list.add("rice");
        ingredients_array_list.add("cheese");
        ingredients_array_list.add("tomatoes");
        populateIngredientsList(ingredients_array_list);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goHome(View view) {

        Intent goHome = new Intent(this, MainActivity.class);
        startActivity(goHome);

        Toast.makeText(this, "You Chose Home", Toast.LENGTH_SHORT).show();

    }

    public void goToFavs(View view) {

        Intent goToFavourites = new Intent(this, Favourites.class);
        startActivity(goToFavourites);
        Toast.makeText(this, "You Chose Favourites", Toast.LENGTH_SHORT).show();
    }
}
