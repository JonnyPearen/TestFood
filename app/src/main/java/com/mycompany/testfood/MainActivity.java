package com.mycompany.testfood;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void onGoodButtonClick(View view) {

        Intent getSearchResultIntent = new Intent(this, RecipeDetails.class);

        /* put data to be sent to recipe details
        * callingActivity : key for the button that was pressed
        * "something good" string to return as result on Details page.*/
        getSearchResultIntent.putExtra("callingActivity", "Something Good");

        startActivity(getSearchResultIntent);

    }

    public void onEasyButtonClick(View view) {

        Intent getSearchResultIntent = new Intent(this, RecipeDetails.class);

        /* put data to be sent to recipe details
        * callingActivity : key for the button that was pressed
        * "something good" string to return as result on Details page.*/
        getSearchResultIntent.putExtra("callingActivity", "Something Easy");

        startActivity(getSearchResultIntent);

    }

    public void goToFavs(View view) {

        Intent goToFavourites = new Intent(this, Favourites.class);

        startActivity(goToFavourites);

        Toast.makeText(this, "You Chose Favourites", Toast.LENGTH_SHORT).show();
    }
}
