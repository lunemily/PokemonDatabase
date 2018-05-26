package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.AbilityDAO;
import com.evanfuhr.pokemondatabase.fragments.PokemonListFragment;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import org.jetbrains.annotations.NonNls;

public class AbilityDisplayActivity extends AppCompatActivity
        implements PokemonListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String ABILITY_ID = "ability_id";

    Ability mAbility = new Ability();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability_display);

        AbilityDAO abilityDAO = new AbilityDAO(this);

        // Get ability_id passed to this activity
        Intent intent = getIntent();
        mAbility.setId(intent.getIntExtra(ABILITY_ID, 0));
        mAbility = abilityDAO.getAbility(mAbility);
        setTitle(mAbility.getName());

        abilityDAO.close();
    }

    @Override
    public void onListFragmentInteraction(Pokemon pokemon) {

        //Build the intent to load the pokemon display
        Intent intent = new Intent(this, PokemonDisplayActivity.class);
        //Load the pokemon ID to send to the player sheet
        intent.putExtra(PokemonDisplayActivity.POKEMON_ID, pokemon.getId());

        startActivity(intent);
    }
}
