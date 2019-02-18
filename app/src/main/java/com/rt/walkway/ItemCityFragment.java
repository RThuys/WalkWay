package com.rt.walkway;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rt.walkway.classes.Path;

public class ItemCityFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";

    private Path[] pathsData;

    public ItemCityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            pathsData = ItemListActivity.getmPathData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        if (pathsData != null) {
            //TODO implement layout ID #
            ((TextView) rootView.findViewById(R.id.item_detail)).setText("Current location " + pathsData[Integer.parseInt(getArguments().get("item_id").toString()) - 1].getCityName())
            ;
        }

        return rootView;
    }
}
