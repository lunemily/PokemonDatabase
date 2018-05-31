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
import com.evanfuhr.pokemondatabase.fragments.EvolutionListFragment;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Evolution;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import java.util.ArrayList;
import java.util.List;

public class EvolutionRecyclerViewAdapter extends RecyclerView.Adapter<EvolutionRecyclerViewAdapter.ViewHolder> {

    private final List<Evolution> mValues, mFilteredList;
    private final EvolutionListFragment.OnListFragmentInteractionListener mListener;

    public EvolutionRecyclerViewAdapter(List<Evolution> items, EvolutionListFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

        mFilteredList = new ArrayList<>();
        mFilteredList.addAll(mValues);
    }

    @Override
    public EvolutionRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_evolution_list_item, parent, false);
        return new EvolutionRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EvolutionRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mFilteredList.get(position);
        holder.mBeforePokemonButton.setId(mFilteredList.get(position).getBeforePokemon().getId());
        holder.mBeforePokemonButton.setText(mFilteredList.get(position).getBeforePokemon().getName());
        holder.mBeforePokemonButton.setBackground(PokemonUtils.getColorGradientByTypes(mFilteredList.get(position).getBeforePokemon().getTypes()));
        holder.mBeforePokemonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem.getBeforePokemon());
                }
            }
        });

        holder.mAfterPokemonButton.setId(mFilteredList.get(position).getAfterPokemon().getId());
        holder.mAfterPokemonButton.setText(mFilteredList.get(position).getAfterPokemon().getName());
        holder.mAfterPokemonButton.setBackground(PokemonUtils.getColorGradientByTypes(mFilteredList.get(position).getAfterPokemon().getTypes()));
        holder.mAfterPokemonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem.getAfterPokemon());
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
        final Button mBeforePokemonButton;
        final TextView mEvolutionDetail;
        final Button mAfterPokemonButton;
        Evolution mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mBeforePokemonButton = view.findViewById(R.id.beforePokemon);
            mEvolutionDetail = view.findViewById(R.id.evolutionDetail);
            mAfterPokemonButton = view.findViewById(R.id.afterPokemon);
        }
    }

    public void filter(final String filterText) {
        mFilteredList.clear();

        // If there is no search value, then add all original list items to filter list
        if (TextUtils.isEmpty(filterText)) {

            mFilteredList.addAll(mValues);

        } else {
            // Iterate in the original List and add it to filter list...
            for (Evolution evolution : mValues) {
                mFilteredList.add(evolution);
            }
        }
        notifyDataSetChanged();
    }

    public void injectEvolutions(List<Evolution> evolutions) {
        mValues.clear();
        mValues.addAll(evolutions);
        filter("");
    }
}
