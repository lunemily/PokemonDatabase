package com.evanfuhr.pokemondatabase.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.list.PokemonListFragment.OnListFragmentInteractionListener;
import com.evanfuhr.pokemondatabase.models.Forme;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import java.util.ArrayList;
import java.util.List;

public class PokemonRecyclerViewAdapter extends RecyclerView.Adapter<PokemonRecyclerViewAdapter.ViewHolder> {

    private final List<Pokemon> mValues, mFilteredList;
    private final OnListFragmentInteractionListener mListener;

    public PokemonRecyclerViewAdapter(List<Pokemon> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

        mFilteredList = new ArrayList<>();
        mFilteredList.addAll(mValues);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_single_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mFilteredList.get(position);
        holder.mButton.setId(mFilteredList.get(position).getId());
        String name = holder.mItem.getName();
        if (holder.mItem.getForme() != null) {
            name = name + " - " + Forme.getShowdownForme(holder.mItem.getForme());
        }
        holder.mButton.setText(name);
        holder.mButton.setBackground(PokemonUtils.getColorGradientByTypes(mFilteredList.get(position).getTypes()));

        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onPokemonListFragmentInteraction(holder.mItem);
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
        Pokemon mItem;

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
            for (Pokemon pokemon : mValues) {
                if (pokemon.getName().toLowerCase().contains(filterText.toLowerCase())) {
                    // Adding Matched items
                    mFilteredList.add(pokemon);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void injectPokemon(List<Pokemon> pokemons) {
        mValues.clear();
        mValues.addAll(pokemons);
        filter("");
    }
}
