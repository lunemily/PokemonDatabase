package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.AbilityListFragment;
import com.evanfuhr.pokemondatabase.models.Ability;

import org.jetbrains.annotations.NonNls;

public class AbilityListActivity extends AppCompatActivity
        implements AbilityListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String ABILITY = "Abilities";
    @NonNls
    public static final String MENU_ITEM_NOT_IMPLEMENTED_YET = "Menu item not implemented yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability_list);

        setTitle(ABILITY);
    }

    @Override
    public void onListFragmentInteraction(Ability ability) {

        // Build the intent to load the ability display
        Intent intent = new Intent(this, AbilityDisplayActivity.class);
        // Load the ability id into the intent
        intent.putExtra(AbilityDisplayActivity.ABILITY_ID, ability.getId());

        startActivity(intent);
    }
}
