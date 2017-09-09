package com.evanfuhr.pokemondatabase.activities;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.PokemonListFragment;

import org.jetbrains.annotations.NonNls;

public class PokemonListActivity extends AppCompatActivity
        implements PokemonListFragment.OnPokemonListFragmentInteractionListener,
        SearchView.OnQueryTextListener{

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search_pokemon_list);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        if (!searchMenuItem.isActionViewExpanded()) {
            searchMenuItem.expandActionView();
        }
        else {
            searchMenuItem.collapseActionView();
        }

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_search_pokemon_list:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        FragmentManager fm = getFragmentManager();
        PokemonListFragment pokemonListFragment = (PokemonListFragment) fm.findFragmentById(R.id.pokemonListFragment);
        pokemonListFragment.generatePokemonList(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        FragmentManager fm = getFragmentManager();
        PokemonListFragment pokemonListFragment = (PokemonListFragment) fm.findFragmentById(R.id.pokemonListFragment);
        //Very taxing on resources
        pokemonListFragment.regeneratePokemonList(newText);
        return true;
    }

    @Override
    public void onPokemonListFragmentInteraction(Uri uri) {

    }
}
