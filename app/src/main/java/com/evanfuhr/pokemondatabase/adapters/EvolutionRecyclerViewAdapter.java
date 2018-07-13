package com.evanfuhr.pokemondatabase.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.list.EvolutionListFragment;
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
                    mListener.onPokemonSelected(holder.mItem.getBeforePokemon());
                }
            }
        });

        holder.mEvolutionDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onEvolutionSelected(holder.mItem);
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
                    mListener.onPokemonSelected(holder.mItem.getAfterPokemon());
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
        final Button mEvolutionDetail;
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

    public void injectEvolutions(List<Evolution> evolutions) {
        mValues.clear();
        mValues.addAll(evolutions);
        mFilteredList.addAll(mValues);
        notifyDataSetChanged();
    }
}
