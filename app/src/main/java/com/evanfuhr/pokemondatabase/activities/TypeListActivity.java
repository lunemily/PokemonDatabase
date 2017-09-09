package com.evanfuhr.pokemondatabase.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.TypeListFragment;

public class TypeListActivity extends AppCompatActivity
        implements TypeListFragment.OnTypeListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_list);
    }

    @Override
    public void onTypeListFragmentInteraction(Uri uri) {

    }
}
