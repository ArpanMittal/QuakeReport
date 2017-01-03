package com.example.android.earthquake;


import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    private static final String EARTHQUAKE_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query";
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final int LOADER_NUMBER = 1;
    private ListView earthquakeListView;
    private EarthquakeAdapter madapter;
    private TextView emptyView;
    private ProgressBar mProgressBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        List<Earthquake> earthquakes = new LinkedList<>();
        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        mProgressBarView = (ProgressBar) findViewById(R.id.loading_spinner);
        // Create a new {@link ArrayAdapter} of earthquakes
        madapter = new EarthquakeAdapter(EarthquakeActivity.this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(madapter);
        earthquakeListView.setEmptyView(emptyView);
        if(QueryUtils.isConnectedToInternet(this))
            getLoaderManager().initLoader(LOADER_NUMBER, null, EarthquakeActivity.this);
        else{
            showNoInternetState();
        }
    }

    private void showNoInternetState() {
        mProgressBarView.setVisibility(View.GONE);
        emptyView.setText("No Internet Connection");

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));
        Uri baseUri = Uri.parse(EARTHQUAKE_URL);
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default)
        );
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby",orderBy);

        //return new EarthquakeLoader(this, uriBuilder.toString());
        return new EarhquakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        mProgressBarView.setVisibility(View.GONE);
        madapter.clear();
        if (data != null && !data.isEmpty()) {
            madapter.addAll(data);
            madapter.notifyDataSetChanged();
        } else {
            emptyView.setText("No Earthquake Data Found");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        madapter.clear();
        madapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
