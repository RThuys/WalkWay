package com.rt.walkway;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.mapbox.mapboxsdk.Mapbox;
import com.rt.walkway.classes.Path;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {
    private static String PATH_INTENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Mapbox.getInstance(this, getString(R.string.access_token));


        if (savedInstanceState == null) {
            PATH_INTENT = getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID);
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
            ItemCityFragment fragment_city = new ItemCityFragment();
            fragment_city.setArguments(arguments);
            ItemDetailFragment fragment_description = new ItemDetailFragment();
            fragment_description.setArguments(arguments);
            ItemMapFragment fragment_map = new ItemMapFragment();
            fragment_map.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_place_container, fragment_city)
                    .add(R.id.item_detail_description_container, fragment_description)
                    .add(R.id.item_detail_map_container, fragment_map)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToMap(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, PATH_INTENT);

        context.startActivity(intent);
    }

}
