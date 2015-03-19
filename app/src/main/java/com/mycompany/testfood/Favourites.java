package com.mycompany.testfood;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;

public class Favourites extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        makeDB();
    }

    @Override
    public void onStart() {
        super.onStart();
        FlurryAgent.logEvent("Favourites_Read");

    }

    public class FeedReaderDbHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;

        public FeedReaderDbHelper(Context context) {
            super(context, "DB1", null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE NEW (_id INTEGER PRIMARY KEY, Food TEXT);");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL("DROP TABLE IF EXISTS TEST");
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

    public void makeDB() throws NullPointerException {
        FeedReaderDbHelper helper = new FeedReaderDbHelper(getApplicationContext());

        SQLiteDatabase testDB = helper.getWritableDatabase();

        ContentValues values = new ContentValues();


            testDB = this.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);

   /* Create a Table in the Database, if it doesn't already exist */
            testDB.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "TEST"
                    + " (_id VARCHAR, Food TEXT);");

        values.put("_id", 1);
        values.put("Food", "Pizza");

        testDB.insert("TEST", null, values);

        ListView listView = (ListView) findViewById(R.id.listView);



        String columns[] = {
                "_id",
                "Food"
        };

        int to[] = new int[] {
            android.R.id.text1,
            android.R.id.text2
        };

        Cursor testCursor = testDB.rawQuery("SELECT _id, Food FROM TEST", null);
        SimpleCursorAdapter testAdapter = new SimpleCursorAdapter(getApplicationContext(),
            android.R.layout.simple_list_item_2, testCursor, columns, to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(testAdapter);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goHome(View view) {

        Intent goHome = new Intent(this, IngredientsSearch.class);

        startActivity(goHome);

        Toast.makeText(this, "You Chose Home", Toast.LENGTH_SHORT).show();

    }
}
