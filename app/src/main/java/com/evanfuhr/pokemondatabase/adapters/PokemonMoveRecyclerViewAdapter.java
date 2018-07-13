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
import com.evanfuhr.pokemondatabase.fragments.list.MoveListFragment.OnListFragmentInteractionListener;
import com.evanfuhr.pokemondatabase.models.Move;

import java.util.ArrayList;
import java.util.List;

public class PokemonMoveRecyclerViewAdapter extends RecyclerView.Adapter<PokemonMoveRecyclerViewAdapter.ViewHolder> {

    private final List<Move> mValues, mFilteredList;
    private final OnListFragmentInteractionListener mListener;

    public PokemonMoveRecyclerViewAdapter(List<Move> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

        mFilteredList = new ArrayList<>();
        mFilteredList.addAll(mValues);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_pokemon_move_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mFilteredList.get(position);
        holder.mButton.setId(mFilteredList.get(position).getId());
        holder.mButton.setText(mFilteredList.get(position).getName());
        holder.mButton.setBackgroundColor(Color.parseColor(mFilteredList.get(position).getType().getColor()));


        switch (holder.mItem.getMethodID()) {
            case LEVEL_UP:
                holder.mDetail.setText("Lv" + Integer.toString(holder.mItem.getLevel()));
                break;
            case MACHINE:
                //move = moveDAO.getTMForMove(move);
                holder.mDetail.setText("TM" + Integer.toString(holder.mItem.getTM()));
                break;
            case EGG:
                holder.mDetail.setText("EGG");
                break;
            case TUTOR:
                // TODO: show actual BP cost
                holder.mDetail.setText("TUTOR");
                break;
            default:
                holder.mDetail.setText("");
                break;
        }
        holder.mPower.setText(Integer.toString(holder.mItem.getPower()));
        holder.mAccuracy.setText(Integer.toString(holder.mItem.getAccuracy()));
        holder.mPP.setText(Integer.toString(holder.mItem.getPP()));

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
        final TextView mDetail;
        final Button mButton;
        final TextView mPower;
        final TextView mPP;
        final TextView mAccuracy;
        Move mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mButton = view.findViewById(R.id.moveButton);
            mDetail = view.findViewById(R.id.moveMethodDetail);
            mPower = view.findViewById(R.id.movePower);
            mPP = view.findViewById(R.id.movePP);
            mAccuracy = view.findViewById(R.id.moveAccuracy);
        }
    }

    public void filter(final String filterText) {
        mFilteredList.clear();

        // If there is no search value, then add all original list items to filter list
        if (TextUtils.isEmpty(filterText)) {

            mFilteredList.addAll(mValues);

        } else {
            // Iterate in the original List and add it to filter list...
            for (Move move : mValues) {
                if (move.getName().toLowerCase().contains(filterText.toLowerCase())) {
                    // Adding Matched items
                    mFilteredList.add(move);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void injectMoves(List<Move> moves) {
        mValues.clear();
        mValues.addAll(moves);
        filter("");
    }
}
