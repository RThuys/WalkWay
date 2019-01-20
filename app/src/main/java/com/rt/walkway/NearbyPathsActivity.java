package com.rt.walkway;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rt.walkway.dataBase.MyDBHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NearbyPathsActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "MainActivity";
    private EditText mLocationText;
    private String mLocationString;
    private LocationManager locationManager;

    private RecyclerView mRecyclerView;
    private PathAdapter mPathAdapter;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    MyDBHandler mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_track);
        mLocationText = findViewById((R.id.location_input_field));


        mydb = new MyDBHandler(this);

        mRecyclerView = findViewById(R.id.recyclerview_paths);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mPathAdapter = new PathAdapter();
        mRecyclerView.setAdapter(mPathAdapter);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        loadPathData();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 120);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);
            this.onLocationChanged((null));
        }
    }

    private void loadPathData() {
        showPathDataView();

        new FetchPaths().execute();
    }

    private void showPathDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mLocationText.setText(R.string.error_location);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            mLocationText.setText(R.string.location_not_available);
            mLoadingIndicator.setVisibility(View.VISIBLE);
        } else {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            StringBuilder sb = new StringBuilder();
            sb.append("Lat:").append(location.getLatitude()).append((" "));
            sb.append("Long:").append(location.getLongitude()).append(("\n"));

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                mLocationText.setText(addresses.get(0).getLocality() + ", " + addresses.get(0).getSubAdminArea());
                mLocationString = addresses.get(0).getLocality();
                new FetchPaths().execute(mLocationString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private class FetchPaths extends AsyncTask<String, Void, String[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }
                String location = strings[0];

            try {
                String[] simpleJsonPathInformation = GetPathJsonUtils.getSimplePathStringFromJson(location);
                return simpleJsonPathInformation;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] strings) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            strings = new String[30];
            if (strings != null) {
                showPathDataView();
                mPathAdapter.setPathData(strings);
            } else {
                showErrorMessage();
            }
        }
    }
}
