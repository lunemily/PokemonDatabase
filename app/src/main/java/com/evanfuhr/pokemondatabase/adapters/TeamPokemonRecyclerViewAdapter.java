package com.evanfuhr.pokemondatabase.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.list.TeamPokemonListFragment.OnListFragmentInteractionListener;
import com.evanfuhr.pokemondatabase.models.Stat;
import com.evanfuhr.pokemondatabase.models.TeamPokemon;

import java.util.ArrayList;
import java.util.List;

public class TeamPokemonRecyclerViewAdapter extends RecyclerView.Adapter<TeamPokemonRecyclerViewAdapter.ViewHolder> {

    private final List<TeamPokemon> mValues, mFilteredList;
    private final OnListFragmentInteractionListener mListener;

    public TeamPokemonRecyclerViewAdapter(List<TeamPokemon> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

        mFilteredList = new ArrayList<>();
        mFilteredList.addAll(mValues);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_team_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mFilteredList.get(position);
        holder.mName.setText(holder.mItem.getName());
        if (holder.mItem.getName().equals(holder.mItem.getNickname())) {
            holder.mNickname.setVisibility(View.INVISIBLE);
        } else {
            holder.mNickname.setText("'" + holder.mItem.getNickname() + "'");
        }
        holder.mAbility.setText(holder.mItem.getAbility().getName());

        holder.mHeldItem.setText(holder.mItem.getItem().getName());

        holder.mNature.setText(holder.mItem.getNature().getName());

        holder.mMove1.setText(holder.mItem.getMoves().get(0).getName());
        holder.mMove2.setText(holder.mItem.getMoves().get(1).getName());
        holder.mMove3.setText(holder.mItem.getMoves().get(2).getName());
        holder.mMove4.setText(holder.mItem.getMoves().get(3).getName());

        holder.mType1.setText(holder.mItem.getTypes().get(0).getName());
        if (holder.mItem.getTypes().size() == 2)
            holder.mType2.setText(holder.mItem.getTypes().get(1).getName());
        else holder.mType2.setVisibility(View.INVISIBLE);

        String mEVs = "";
        if (holder.mItem.getEVs().containsKey(Stat.PrimaryStat.HP))
            mEVs += (holder.mItem.getEVs().get(Stat.PrimaryStat.HP).toString() + " HP ");
        if (holder.mItem.getEVs().containsKey(Stat.PrimaryStat.Atk))
            mEVs += (holder.mItem.getEVs().get(Stat.PrimaryStat.Atk).toString() + " Atk ");
        if (holder.mItem.getEVs().containsKey(Stat.PrimaryStat.Def))
            mEVs += (holder.mItem.getEVs().get(Stat.PrimaryStat.Def).toString() + " Def ");
        if (holder.mItem.getEVs().containsKey(Stat.PrimaryStat.SpA))
            mEVs += (holder.mItem.getEVs().get(Stat.PrimaryStat.SpA).toString() + " SpA ");
        if (holder.mItem.getEVs().containsKey(Stat.PrimaryStat.SpD))
            mEVs += (holder.mItem.getEVs().get(Stat.PrimaryStat.SpD).toString() + " SpD ");
        if (holder.mItem.getEVs().containsKey(Stat.PrimaryStat.Spe))
            mEVs += (holder.mItem.getEVs().get(Stat.PrimaryStat.Spe).toString() + " Spe");
        holder.mEVs.setText(mEVs.trim());
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mNickname;
        final TextView mName;
        final TextView mAbility;
        final TextView mHeldItem;
        final TextView mNature;
        final TextView mMove1;
        final TextView mMove2;
        final TextView mMove3;
        final TextView mMove4;
        final TextView mType1;
        final TextView mType2;
        final TextView mEVs;

        TeamPokemon mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;

            mNickname = view.findViewById(R.id.text_view_team_pokemon_nickname);
            mName = view.findViewById(R.id.text_view_team_pokemon_name);
            mAbility = view.findViewById(R.id.text_view_ability);
            mHeldItem = view.findViewById(R.id.text_view_item);
            mNature = view.findViewById(R.id.text_view_nature);
            mMove1 = view.findViewById(R.id.text_view_team_pokemon_move_1);
            mMove2 = view.findViewById(R.id.text_view_team_pokemon_move_2);
            mMove3 = view.findViewById(R.id.text_view_team_pokemon_move_3);
            mMove4 = view.findViewById(R.id.text_view_team_pokemon_move_4);
            mType1 = view.findViewById(R.id.text_view_type_1);
            mType2 = view.findViewById(R.id.text_view_type_2);
            mEVs = view.findViewById(R.id.text_view_evs);
        }
    }

    public void filter(final String filterText) {
        mFilteredList.clear();

        // If there is no search value, then add all original list items to filter list
        if (TextUtils.isEmpty(filterText)) {

            mFilteredList.addAll(mValues);

        } else {
            // Iterate in the original List and add it to filter list...
            for (TeamPokemon teamPokemon : mValues) {
                if (teamPokemon.getName().toLowerCase().contains(filterText.toLowerCase())) {
                    // Adding Matched items
                    mFilteredList.add(teamPokemon);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void injectTeamPokemons(List<TeamPokemon> teamPokemons) {
        mValues.clear();
        mValues.addAll(teamPokemons);
        filter("");
    }
}
