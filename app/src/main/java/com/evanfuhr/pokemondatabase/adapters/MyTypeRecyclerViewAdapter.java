package com.evanfuhr.pokemondatabase.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.TypeListFragment.OnListFragmentInteractionListener;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.List;

public class MyTypeRecyclerViewAdapter extends RecyclerView.Adapter<MyTypeRecyclerViewAdapter.ViewHolder> {

    private final List<Type> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTypeRecyclerViewAdapter(List<Type> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_single_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder._button.setId(mValues.get(position).getID());
        holder._button.setText(mValues.get(position).getName());
        holder._button.setBackgroundColor(Color.parseColor(mValues.get(position).getColor()));

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
        public Type mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            _button = (Button) view.findViewById(R.id.singleButton);
        }
    }
}
