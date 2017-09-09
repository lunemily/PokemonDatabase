package com.evanfuhr.pokemondatabase.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.TypeListFragment;

import org.jetbrains.annotations.NonNls;

public class TypeListActivity extends AppCompatActivity
        implements TypeListFragment.OnTypeListFragmentInteractionListener {

    @NonNls
    public static final String TYPE_ID = "type_id";
    @NonNls
    public static final String TYPE = "Type";
    @NonNls
    public static final String MENU_ITEM_NOT_IMPLEMENTED_YET = "Menu item not implemented yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_list);

        setTitle(TYPE);
    }

    @Override
    public void onTypeListFragmentInteraction(Uri uri) {

    }
}
