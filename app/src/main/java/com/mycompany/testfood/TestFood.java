package com.mycompany.testfood;

import android.app.Application;

import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * TestFood.java
 * Extends application class to implement use of
 * Flurry analytics.
 * Created by Jessica on 2015-02-17.
 */
public class TestFood extends Application {

    static final String MY_FLURRY_APIKEY = "F7MTPVYXJMH6DCHMN9S3";

    @Override
    public void onCreate() {
        super.onCreate();
        //configure Flurry
        FlurryAgent.setLogEnabled(false);
        //init Flurry
        FlurryAgent.init(this, MY_FLURRY_APIKEY);

        // Capture author info & user status
        Map<String, String> articleParams = new HashMap<String, String>();

        //up to 10 params can be logged with each event
        FlurryAgent.logEvent("AB_Details", articleParams);
        FlurryAgent.logEvent("AB_Favourites", articleParams);
        FlurryAgent.logEvent("HomeTab", articleParams);
        FlurryAgent.logEvent("DetailsTab", articleParams);
        FlurryAgent.logEvent("FavTab", articleParams);


        //Log the timed event when the user starts reading the article
        //setting the third param to true creates a timed event
        FlurryAgent.logEvent("Home_Read", articleParams, true);
        FlurryAgent.logEvent("Details_Read", articleParams, true);
        FlurryAgent.logEvent("Ingredients_Search_Read", articleParams, true);
        FlurryAgent.logEvent("Favourites_Read", articleParams, true);
    }

}
