package com.rt.walkway;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rt.walkway.classes.Path;
import com.rt.walkway.dummy.DummyContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
//TODO implement image in the master detail
public class ItemDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ITEM_ID = "id";


    private DummyContent.DummyItem mItem;
    private Path[] pathsData;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            pathsData = ItemListActivity.getmPathData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        if (pathsData != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(pathsData[Integer.parseInt(getArguments().get("item_id").toString()) - 1].getDescription());


            /*
            ((TextView) rootView.findViewById(R.id.textView)).setText(this.getString(R.string.description_label) + pathsData[Integer.parseInt(getArguments().get("item_id").toString()) - 1].toString() + " " + pathsData[Integer.parseInt(getArguments().get("item_id").toString()) - 1].getCityName());

            if (pathsData[Integer.parseInt(getArguments().get("item_id").toString()) - 1].getDifficulty() == "easy") {
                    ((ImageView) rootView.findViewById(R.id.item_difficulty_image)).setImageResource(R.drawable.easy);
            } else if (pathsData[Integer.parseInt(getArguments().get("item_id").toString()) - 1].getDifficulty() == "medium") {
                ((ImageView) rootView.findViewById(R.id.item_difficulty_image)).setImageResource(R.drawable.medium);
            } else {
                ((ImageView) rootView.findViewById(R.id.item_difficulty_image)).setImageResource(R.drawable.hard);
            }
            */
        }

        return rootView;
    }
}
