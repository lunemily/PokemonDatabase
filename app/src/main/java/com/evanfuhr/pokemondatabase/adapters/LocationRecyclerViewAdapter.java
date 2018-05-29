package com.evanfuhr.pokemondatabase.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.LocationListFragment;
import com.evanfuhr.pokemondatabase.models.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.ViewHolder> {

    private final List<Location> mValues, mFilteredList;
    private final LocationListFragment.OnListFragmentInteractionListener mListener;

    public LocationRecyclerViewAdapter(List<Location> items, LocationListFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

        mFilteredList = new ArrayList<>();
        mFilteredList.addAll(mValues);
    }

    @Override
    public LocationRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_single_button, parent, false);
        return new LocationRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LocationRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mFilteredList.get(position);
        holder.mButton.setId(mFilteredList.get(position).getId());
        String text = mFilteredList.get(position).getRegion().getName() + " - " + mFilteredList.get(position).getName();
        holder.mButton.setText(text);
        holder.mButton.setBackgroundColor(Color.GRAY);

        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final Button mButton;
        Location mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mButton = view.findViewById(R.id.singleButton);
        }
    }

    public void filter(final String filterText) {
        mFilteredList.clear();

        // If there is no search value, then add all original list items to filter list
        if (TextUtils.isEmpty(filterText)) {

            mFilteredList.addAll(mValues);

        } else {
            // Iterate in the original List and add it to filter list...
            for (Location location : mValues) {
                if (location.getName().toLowerCase().contains(filterText.toLowerCase()) || location.getRegion().getName().toLowerCase().contains(filterText.toLowerCase())) {
                    // Adding Matched items
                    mFilteredList.add(location);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void injectLocations(List<Location> locations) {
        mValues.clear();
        mValues.addAll(locations);
        filter("");
    }
}
