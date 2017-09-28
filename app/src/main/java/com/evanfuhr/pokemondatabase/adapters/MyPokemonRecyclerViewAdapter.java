package com.evanfuhr.pokemondatabase.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.PokemonListFragment.OnListFragmentInteractionListener;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.List;

public class MyPokemonRecyclerViewAdapter extends RecyclerView.Adapter<MyPokemonRecyclerViewAdapter.ViewHolder> {

    private final List<Pokemon> mValues, _filteredList;
    private final OnListFragmentInteractionListener mListener;

    public MyPokemonRecyclerViewAdapter(List<Pokemon> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

        _filteredList = new ArrayList<>();
        _filteredList.addAll(mValues);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = _filteredList.get(position);
        holder._button.setId(_filteredList.get(position).getID());
        holder._button.setText(_filteredList.get(position).getName());
        holder._button.setBackground(getPokemonButtonBackgroundColor(_filteredList.get(position)));

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final Button _button;
        public Pokemon mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            _button = (Button) view.findViewById(R.id.pokemonButton);
        }
    }

    public void filter(final String filterText) {
        _filteredList.clear();

        // If there is no search value, then add all original list items to filter list
        if (TextUtils.isEmpty(filterText)) {

            _filteredList.addAll(mValues);

        } else {
            // Iterate in the original List and add it to filter list...
            for (Pokemon pokemon : mValues) {
                if (pokemon.getName().toLowerCase().contains(filterText.toLowerCase())) {
                    // Adding Matched items
                    _filteredList.add(pokemon);
                }
            }
        }
        notifyDataSetChanged();
    }

    GradientDrawable getPokemonButtonBackgroundColor(Pokemon pokemon) {
        List<Type> types = pokemon.getTypes();

        int[] colors = {0, 0, 0, 0};
        if (types.size() == 1) {
            colors[0] = Color.parseColor(types.get(0).getColor());
            colors[1] = Color.parseColor(types.get(0).getColor());
            colors[2] = Color.parseColor(types.get(0).getColor());
            colors[3] = Color.parseColor(types.get(0).getColor());
        }
        else {
            colors[0] = Color.parseColor(types.get(0).getColor());
            colors[1] = Color.parseColor(types.get(0).getColor());
            colors[2] = Color.parseColor(types.get(1).getColor());
            colors[3] = Color.parseColor(types.get(1).getColor());
        }
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, colors);

        return gd;
    }
}
