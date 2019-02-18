package com.rt.walkway;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rt.walkway.classes.Path;
import com.rt.walkway.utils.FetchData;
import com.rt.walkway.utils.PrintListner;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ItemListActivity extends AppCompatActivity implements LocationListener, PrintListner {
    private boolean mTwoPane;
    private EditText mLocationText;
    private ImageView mTreeInfo, mInfoButton;
    private TextView mColorInfoBackground, mTreeTextInfo;

    private LocationManager locationManager;

    private static Resources mResources;

    private static ProgressBar mLoadingIndicator;
    private List<Address> addresses;
    private SwipeRefreshLayout swipeContainer;
    private static RecyclerView mRecyclerView;

    private int count = 0;


    private static String JSON = "https://api.myjson.com/bins/soa5u";


    private static Path[] mPathData;

    public static Path[] getmPathData() {
        return mPathData;
    }

    /* JSON FETCHER
        http://myjson.com/8fu5o
        https://api.myjson.com/bins/q8hfg

        2
        http://myjson.com/8rz4i
        https://api.myjson.com/bins/8rz4i


        3 copied
        http://myjson.com/soa5u
        https://api.myjson.com/bins/soa5u
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        swipeContainer = findViewById(R.id.refresh_list);
        mLocationText = findViewById((R.id.location_input_field));
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mRecyclerView = findViewById(R.id.item_list);
        mColorInfoBackground = findViewById(R.id.color_info_background);
        mTreeInfo = findViewById(R.id.easy_tree_image);
        mInfoButton = findViewById(R.id.info_button_colors);
        mTreeTextInfo = findViewById(R.id.tree_text_info);

        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        count = 0;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 120);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);
            this.onLocationChanged((null));
        }
        new FetchData(this).execute(JSON);

        swipeContainer.setOnRefreshListener(() -> {
            new FetchData(this).execute(JSON);
        });


        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }


        mResources = getResources();


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
                //mLocationString = addresses.get(0).getLocality();

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
        swipeContainer.setRefreshing(false);
        mPathData = receiptItem;
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private static Path pathTemp = null;


    public void showColorInfo(View view) {
        mRecyclerView.setClickable(false);
        mRecyclerView.setEnabled(false);
        mInfoButton.setVisibility(View.INVISIBLE);
        mTreeInfo.setImageResource(R.drawable.treeeasy);
        mTreeInfo.setVisibility(View.VISIBLE);
        mTreeTextInfo.setText(getString(R.string.track_indication_easy));
        mTreeTextInfo.setVisibility(View.VISIBLE);
        mColorInfoBackground.setVisibility(View.VISIBLE);
    }

    public void nextStep(View view) {
        if (count == 0) {
            mTreeInfo.setImageResource(R.drawable.treemedium);
            mTreeTextInfo.setText(getString(R.string.track_indication_medium));
            count++;
        } else if (count == 1) {
            mTreeInfo.setImageResource(R.drawable.treehard);
            mTreeTextInfo.setText(getString(R.string.track_indication_hard));
            count++;
        } else {
            mRecyclerView.setClickable(true);
            mRecyclerView.setEnabled(true);
            mInfoButton.setVisibility(View.VISIBLE);
            mTreeInfo.setVisibility(View.INVISIBLE);
            mTreeTextInfo.setVisibility(View.INVISIBLE);
            mColorInfoBackground.setVisibility(View.INVISIBLE);
            count = 0;
        }
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
                pathTemp = path;

                if (mTwoPane) {

                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, String.valueOf(path.getId()));
                    ItemCityFragment fragment_city = new ItemCityFragment();
                    fragment_city.setArguments(arguments);
                    ItemDetailFragment fragment_description = new ItemDetailFragment();
                    fragment_description.setArguments(arguments);
                    ItemMapFragment fragment_map = new ItemMapFragment();
                    fragment_map.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_place_container, fragment_city)
                            .replace(R.id.item_detail_description_container, fragment_description)
                            .replace(R.id.item_detail_map_container, fragment_map)
                            .commit();

                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    Log.i("intent", String.valueOf(path.getId()));
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
            mRecyclerView.setVisibility(View.VISIBLE);
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            holder.mIdView.setText(String.valueOf(position + 1));
            holder.mContentView.setText(mPathData[position].getCityName());
            holder.mImageView.setVisibility((mResources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? View.GONE : View.VISIBLE);
            if (mPathData[position].getDifficulty().equals("easy")) {
                holder.mImageView.setImageResource(R.drawable.treeeasy);
            } else if (mPathData[position].getDifficulty().equals("medium")) {
                holder.mImageView.setImageResource(R.drawable.treemedium);
            } else {
                holder.mImageView.setImageResource(R.drawable.treehard);
            }
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
            final ImageView mImageView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
                mImageView = (ImageView) view.findViewById(R.id.difficulty_image);

            }
        }
    }

    public void goToMap(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, String.valueOf(pathTemp.getId()));

        context.startActivity(intent);
    }

}
