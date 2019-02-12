package com.rt.walkway;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rt.walkway.classes.Path;
import com.rt.walkway.dummy.DummyContent;
import com.rt.walkway.utils.FetchData;
import com.rt.walkway.utils.PrintListner;
import com.rt.walkway.utils.PrintListnerImp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ItemListActivity extends AppCompatActivity implements LocationListener, PrintListner {
    private boolean mTwoPane;
    private EditText mLocationText;
    private String mLocationString;
    private LocationManager locationManager;

    private ProgressBar mLoadingIndicator;
    private List<Address> addresses;

    private static Path[] mPathData;

    public static Path[] getmPathData() {
        return mPathData;
    }

/* JSON FETCHER
        http://myjson.com/8fu5o
        https://api.myjson.com/bins/q8hfg
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        mLocationText = findViewById((R.id.location_input_field));
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }

        new FetchData(this).execute("https://api.myjson.com/bins/8fu5o");


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 120);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);
            this.onLocationChanged((null));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, mTwoPane, mPathData));
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
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                mLocationText.setText(addresses.get(0).getLocality() + ", " + addresses.get(0).getSubAdminArea());
                mLocationString = addresses.get(0).getLocality();

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
    public void getResult(Path[] receiptItem) {
        Log.d("Test", receiptItem[2].toString());
        mPathData = receiptItem;
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {


        private final ItemListActivity mParentActivity;
        private final boolean mTwoPane;
        private final Path[] mPathData;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Path path = (Path) view.getTag();

                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, String.valueOf(path.getId()));
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, String.valueOf(path.getId()));

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      boolean twoPane, Path[] pathData) {
            mParentActivity = parent;
            mTwoPane = twoPane;
            mPathData = pathData;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(String.valueOf(position + 1));
            holder.mContentView.setText(mPathData[position].getCityName());
            holder.itemView.setTag(mPathData[position]);
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            // return mValues.size();
            return mPathData.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
