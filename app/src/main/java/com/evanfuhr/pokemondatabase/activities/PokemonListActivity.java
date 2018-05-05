package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.PokemonListFragment;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import org.jetbrains.annotations.NonNls;

public class PokemonListActivity extends AppCompatActivity
        implements PokemonListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String POKEMON = "Pokémon";
    @NonNls
    public static final String MENU_ITEM_NOT_IMPLEMENTED_YET = "Menu item not implemented yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        setTitle(POKEMON);
    }

    @Override
    public void onListFragmentInteraction(Pokemon pokemon) {
        PokemonUtils.showLoadingToast(this);

        // Build the intent to load the pokemon display
        Intent intent = new Intent(this, PokemonDisplayActivity.class);
        // Load the pokemon ID to send to the player sheet
        intent.putExtra(PokemonDisplayActivity.POKEMON_ID, pokemon.getID());

        startActivity(intent);
    }
}
