package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.NatureListFragment;
import com.evanfuhr.pokemondatabase.models.Nature;

import org.jetbrains.annotations.NonNls;

public class NatureListActivity extends AppCompatActivity
        implements NatureListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String NATURE_ID = "move_id";
    @NonNls
    public static final String NATURE = "Type";
    @NonNls
    public static final String MENU_ITEM_NOT_IMPLEMENTED_YET = "Menu item not implemented yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature_list);

        setTitle(NATURE);
    }

    @Override
    public void onListFragmentInteraction(Nature nature) {
        int type_id = nature.getId();

        //Build the intent to load the player sheet
        Intent intent = new Intent(this, MoveDisplayActivity.class);
        //Load the hero ID to send to the player sheet
        intent.putExtra(NATURE_ID, type_id);

        //startActivity(intent);
    }
}
