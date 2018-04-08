package com.evanfuhr.pokemondatabase.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.TypeMatchUpFragment.OnListFragmentInteractionListener;
import com.evanfuhr.pokemondatabase.models.Type;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import java.util.List;

public class TypeEfficacyRecyclerViewAdapter extends RecyclerView.Adapter<TypeEfficacyRecyclerViewAdapter.ViewHolder> {

    private final List<Type> mValues;
    private final OnListFragmentInteractionListener mListener;

    public TypeEfficacyRecyclerViewAdapter(List<Type> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_type_efficacy_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder._button.setId(mValues.get(position).getId());
        holder._button.setText(mValues.get(position).getName());
        holder._button.setBackgroundColor(Color.parseColor(mValues.get(position).getColor()));

        if (holder.mItem.isAttacker()) {
            holder.mDealsReceives.setText(R.string.dealsDamage);
        } else {
            holder.mDealsReceives.setText(R.string.takesDamage);
        }
        holder.mMultiplier.setText(PokemonUtils.convertEfficacyToMultiplier(holder.mItem.getEfficacy()));

        holder._button.setOnClickListener(new View.OnClickListener() {
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
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final Button _button;
        final TextView mDealsReceives;
        final TextView mMultiplier;
        Type mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            _button = view.findViewById(R.id.typeButton);
            mDealsReceives = view.findViewById(R.id.dealsReceives);
            mMultiplier = view.findViewById(R.id.multiplier);
        }
    }
}
