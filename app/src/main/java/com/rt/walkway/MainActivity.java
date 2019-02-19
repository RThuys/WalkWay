package com.rt.walkway;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements LocationListener {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String IMAGE_ID = "id";

    private Button mNearbyTracks, mLoginButton, mProfileButton;
    private ImageView mInfoButton;
    private RelativeLayout mLayout;

    public static boolean loggedIn = false;


    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.main_activity_layout);
        setBackground();
        mProfileButton = findViewById(R.id.profile_view_button);

        onClickNearbyTracks();
        onClickLogin();
        onClickInfo();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 120);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            this.onLocationChanged((null));
        }

        loggedIn();

    }

    private void onClickLogin() {
        mLoginButton = findViewById(R.id.login_view_button);
        mLoginButton.setOnClickListener(v -> setContext(LoginActivity.class));
    }

    private void onClickNearbyTracks() {
        mNearbyTracks = findViewById(R.id.find_nearby_routes_button);
        mNearbyTracks.setOnClickListener(v -> setContext(ItemListActivity.class));
    }

    private void onClickInfo() {
        mInfoButton = findViewById(R.id.info_button);
        mInfoButton.setOnClickListener(v -> onCreateDialog());
    }

    public void onClickProfile(View view) {
        setContext(ProfileActivity.class);
    }

    private void setContext(Class classContext) {
        Context context = MainActivity.this;
        Class destinationActivity = classContext;
        Intent startLoginActivityIntent = new Intent(context, destinationActivity);
        startActivity(startLoginActivityIntent);
    }

    protected void onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.info_label)
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loggedIn();
        setBackground();
    }

    private void loggedIn() {
        if (loggedIn == true) {
            mLoginButton.setVisibility(View.GONE);
            mProfileButton.setVisibility(View.VISIBLE);

        } else {
            mLoginButton.setVisibility(View.VISIBLE);
            mProfileButton.setVisibility(View.GONE);
        }
    }


    private void setBackground() {
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(IMAGE_ID)) {
            Log.i("print", sharedpreferences.getString(IMAGE_ID, ""));
            switch (sharedpreferences.getString(IMAGE_ID, "")) {
                case "1":
                    mLayout.setBackgroundResource(R.drawable.image);
                    break;
                case "2":
                    mLayout.setBackgroundResource(R.drawable.image2);
                    break;
                case "3":
                    mLayout.setBackgroundResource(R.drawable.image3);
                    break;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

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
}
