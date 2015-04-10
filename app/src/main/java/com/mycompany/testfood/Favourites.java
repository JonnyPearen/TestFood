package com.mycompany.testfood;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Favourites extends ActionBarActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        makeDB();
    }

    @Override
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Toast.makeText(this, position, Toast.LENGTH_SHORT).show();
        ListView list = (ListView) findViewById(R.id.favlistView);
        String clickedItems=list.getItemAtPosition(position).toString();
        Toast.makeText(this, clickedItems, Toast.LENGTH_SHORT).show();

        /*
        Intent goToDetails = new Intent();
        goToDetails.setClass(this, recipeDetails.class);
        goToDetails.putExtra("recipeName", (String) resultRecipes.get(position));
        startActivity(goToDetails);
        */
    }

    public void makeDB() throws NullPointerException {
        SQLiteDatabase testDB = this.openOrCreateDatabase("database4", MODE_PRIVATE, null);

        //Create a table in the database, if it doesn't already exist
        testDB.execSQL("CREATE TABLE IF NOT EXISTS "
                + "TEST"
                + " (_id VARCHAR, Food TEXT);");

        //Get the listView to put the favourites data into
        ListView listView = (ListView) findViewById(R.id.favlistView);

        //The column(s) from the database to display
        String columns[] = {
                "Food"
        };

        //The corresponding part of template to fill with the data
        int to[] = new int[]{
                android.R.id.text1
        };

        //The cursor that queries the database
        Cursor testCursor = testDB.rawQuery("SELECT _id, Food FROM TEST", null);

        //The cursor adapter that allows the cursor's results to be display
        SimpleCursorAdapter testAdapter = new SimpleCursorAdapter(getApplicationContext(),
                android.R.layout.simple_list_item_1, testCursor, columns, to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        //Setting the listView to use the cursor adapter
        listView.setAdapter(testAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
