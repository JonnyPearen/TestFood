package com.mycompany.testfood;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class RecipeDetails extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        //declare intent
        Intent activityThatCalled = getIntent();

        /* String field to hold data sent from Main (extras)
        * callingActivity : key from Main. */
        String searchButtonSelected =
                activityThatCalled.getExtras().getString("callingActivity");

        //get the actual string
        TextView searchButtonSelectedMessage =
                (TextView) findViewById(R.id.searchButtonSelected);

        //append selected string message to the textview on details page.
        searchButtonSelectedMessage.append(" " + searchButtonSelected);
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
