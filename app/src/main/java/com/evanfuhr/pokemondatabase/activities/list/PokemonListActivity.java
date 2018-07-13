package com.evanfuhr.pokemondatabase.activities.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.display.PokemonDisplayActivity;
import com.evanfuhr.pokemondatabase.fragments.list.PokemonListFragment;
import com.evanfuhr.pokemondatabase.models.Pokemon;

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
    public void onPokemonListFragmentInteraction(Pokemon pokemon) {
        // Build the intent to load the pokemon display
        Intent intent = new Intent(this, PokemonDisplayActivity.class);
        // Load the pokemon id into the intent
        intent.putExtra(PokemonDisplayActivity.POKEMON_ID, pokemon.getId());

        startActivity(intent);
    }
}
