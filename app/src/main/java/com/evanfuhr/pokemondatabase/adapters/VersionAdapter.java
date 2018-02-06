package com.evanfuhr.pokemondatabase.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.models.Version;

import java.util.List;

public class VersionAdapter extends ArrayAdapter<Version> {

    private Context _context;

    private List<Version> _values;

    public VersionAdapter(Context context, int textViewResourceID, List<Version> values) {
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
        TextView label = new TextView(_context);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(_values.get(position).getName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(_context);
        label.setTextColor(Color.BLACK);
        label.setText(_values.get(position).getName());

        return label;
    }

    public int getPositionByVersion(int version_id) {
        int position = 0;
        for (Version v : _values) {
            if (v.getID() == version_id) {
                position = _values.indexOf(v);
            }
        }
        return position;
    }
}
