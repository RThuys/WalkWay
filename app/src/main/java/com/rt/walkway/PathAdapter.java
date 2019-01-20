package com.rt.walkway;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PathAdapter extends RecyclerView.Adapter<PathAdapter.PathAdapterViewHolder> {

    private String[] mPathData;

    public PathAdapter() {
    }

    public class PathAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView mPathTextView;

        public PathAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mPathTextView = itemView.findViewById(R.id.tv_path_data);
        }
    }

    @NonNull
    @Override
    public PathAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIForListItem = R.layout.path_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIForListItem, viewGroup, shouldAttachToParentImmediately);
        return new PathAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PathAdapterViewHolder pathAdapterViewHolder, int i) {
        String pathsAvailable = mPathData[i];
        pathAdapterViewHolder.mPathTextView.setText(pathsAvailable);
    }

    @Override
    public int getItemCount() {
        if (null == mPathData) return 0;
        return mPathData.length;
    }

    public void setPathData(String[] pathData) {
        mPathData = pathData;
        notifyDataSetChanged();
    }
}
