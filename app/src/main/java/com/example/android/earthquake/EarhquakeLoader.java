package com.example.android.earthquake;


import android.content.AsyncTaskLoader;
import android.content.Context;


import java.util.List;

import static com.example.android.earthquake.QueryUtils.extractEarthquakes;

/**
 * Created by arpan on 12/29/2016.
 */

public class EarhquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private static  String EARTHQUAKE_URL ;
    public EarhquakeLoader(Context context, String url) {
        super(context);
        EARTHQUAKE_URL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (EARTHQUAKE_URL == null)
            return null;
        else {
            String jsonResponse = QueryUtils.openHttpConnection(EARTHQUAKE_URL);
            List<Earthquake> earthquakes = QueryUtils.extractEarthquakes(jsonResponse);
            return earthquakes;
        }
    }


}
