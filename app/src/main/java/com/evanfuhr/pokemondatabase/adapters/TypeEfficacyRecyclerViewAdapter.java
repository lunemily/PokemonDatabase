package com.evanfuhr.pokemondatabase.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.TypeMatchUpFragment.OnTypeMatchUpListFragmentInteractionListener;
import com.evanfuhr.pokemondatabase.models.Type;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import java.util.ArrayList;
import java.util.List;

public class TypeEfficacyRecyclerViewAdapter extends RecyclerView.Adapter<TypeEfficacyRecyclerViewAdapter.ViewHolder> {

    private final List<Type> mValues, mFilteredList;
    private final OnTypeMatchUpListFragmentInteractionListener mListener;

    public TypeEfficacyRecyclerViewAdapter(List<Type> items, OnTypeMatchUpListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

        mFilteredList = new ArrayList<>();
        mFilteredList.addAll(mValues);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_type_efficacy_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mFilteredList.get(position);
        holder.mButton.setId(mFilteredList.get(position).getId());
        holder.mButton.setText(mFilteredList.get(position).getName());
        holder.mButton.setBackgroundColor(Color.parseColor(mFilteredList.get(position).getColor()));

        if (holder.mItem.isTarget()) {
            // In the context of the TypeMatchUpFragment, the Activity's type is the originating type
            holder.mDealsReceives.setText(R.string.dealsDamage);
        } else {
            holder.mDealsReceives.setText(R.string.takesDamage);
        }
        holder.mMultiplier.setText(PokemonUtils.convertEfficacyToMultiplier(holder.mItem.getEfficacy()));

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
        final TextView mDealsReceives;
        final TextView mMultiplier;
        Type mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mButton = view.findViewById(R.id.typeButton);
            mDealsReceives = view.findViewById(R.id.dealsReceives);
            mMultiplier = view.findViewById(R.id.multiplier);
        }
    }

    public void filter(final String filterText) {
        mFilteredList.clear();

        // If there is no search value, then add all original list items to filter list
        if (TextUtils.isEmpty(filterText)) {

            mFilteredList.addAll(mValues);

        } else {
            // Iterate in the original List and add it to filter list...
            for (Type type : mValues) {
                if (type.getName().toLowerCase().contains(filterText.toLowerCase())) {
                    // Adding Matched items
                    mFilteredList.add(type);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void injectTypes(List<Type> types) {
        mValues.clear();
        mValues.addAll(types);
        filter("");
    }
}
