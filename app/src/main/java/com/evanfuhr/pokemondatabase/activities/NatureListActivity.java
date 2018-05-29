package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.NatureListFragment;
import com.evanfuhr.pokemondatabase.models.Nature;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import org.jetbrains.annotations.NonNls;

public class NatureListActivity extends AppCompatActivity
        implements NatureListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String NATURE = "Natures";
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
        int nature_id = nature.getId();

        // Build the intent to load the player sheet
        Intent intent = new Intent(this, NatureDisplayActivity.class);
        // Add the id to send to the display activity
        intent.putExtra(NatureDisplayActivity.NATURE_ID, nature_id);

        startActivity(intent);
    }
}
