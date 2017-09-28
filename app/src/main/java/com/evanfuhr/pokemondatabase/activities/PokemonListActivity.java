package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.PokemonListFragment;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import org.jetbrains.annotations.NonNls;

public class PokemonListActivity extends AppCompatActivity
        implements PokemonListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String POKEMON_ID = "pokemon_id";
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
        int pokemon_id = pokemon.getID();

        //Build the intent to load the player sheet
        Intent intent = new Intent(this, PokemonDisplayActivity.class);
        //Load the hero ID to send to the player sheet
        intent.putExtra(POKEMON_ID, pokemon_id);

        startActivity(intent);
    }
}
