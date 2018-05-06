package com.evanfuhr.pokemondatabase.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.evanfuhr.pokemondatabase.models.Version;

import java.util.List;

public class VersionSpinnerAdapter extends ArrayAdapter<Version> {

    private Context _context;

    private List<Version> _values;

    public VersionSpinnerAdapter(Context context, int textViewResourceID, List<Version> values) {
        super(context, textViewResourceID, values);
        this._context = context;
        this._values = values;
    }

    @Override
    public int getCount(){
        return _values.size();
    }

    @Override
    public Version getItem(int position){
        return _values.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckedTextView view = new CheckedTextView(_context);
        view.setText(_values.get(position).getName());
        view.setPadding(50, 50, 50, 50);

        return view;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        CheckedTextView view = new CheckedTextView(_context);
        view.setText(_values.get(position).getName());
        view.setPadding(50, 50, 50, 50);

        return view;
    }

    public int getPositionByVersion(int version_id) {
        int position = 0;
        for (Version v : _values) {
            if (v.getId() == version_id) {
                position = _values.indexOf(v);
            }
        }
        return position;
    }
}
