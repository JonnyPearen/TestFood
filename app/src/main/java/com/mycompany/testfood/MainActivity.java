package com.mycompany.testfood;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create tabhost object, add buttons and labels
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpecHome = tabHost.newTabSpec("home");
        tabSpecHome.setContent(R.id.tabHome);
        tabSpecHome.setIndicator("Home");
        tabHost.addTab(tabSpecHome);

        TabHost.TabSpec tabSpecFavourites = tabHost.newTabSpec("favourites");
        tabSpecFavourites.setContent(R.id.tabFavourites);
        tabSpecFavourites.setIndicator("Favourites");
        tabHost.addTab(tabSpecFavourites);

        TabHost.TabSpec tabSpecDetails = tabHost.newTabSpec("details");
        tabSpecDetails.setContent(R.id.tabDetails);
        tabSpecDetails.setIndicator("Details");
        tabHost.addTab(tabSpecDetails);
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

        switch (item.getItemId()) {
            case R.id.action_bad:
                Toast.makeText(this, ":(", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_good:
                Toast.makeText(this, "Yay", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_favourite:
                Toast.makeText(this, ":D", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onGoodButtonClick(View view) {

        Intent getSearchResultIntent = new Intent(this, IngredientsSearch.class);

        /* put data to be sent to recipe details
        * callingActivity : key for the button that was pressed
        * "something good" string to return as result on Details page.*/
        getSearchResultIntent.putExtra("callingActivity", "Something Good");

        startActivity(getSearchResultIntent);

    }

    public void onEasyButtonClick(View view) {

        Intent getSearchResultIntent = new Intent(this, IngredientsSearch.class);

        /* put data to be sent to recipe details
        * callingActivity : key for the button that was pressed
        * "something good" string to return as result on Details page.*/
        getSearchResultIntent.putExtra("callingActivity", "Something Easy");

        startActivity(getSearchResultIntent);

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

    public void onRecipeDetailsClick(View view) {
        Intent goToRecipeDetails = new Intent(this, recipeDetails.class);
        startActivity(goToRecipeDetails);
    }
}
