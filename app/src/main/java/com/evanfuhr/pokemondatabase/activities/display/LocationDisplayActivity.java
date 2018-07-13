package com.evanfuhr.pokemondatabase.activities.display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.LocationDAO;
import com.evanfuhr.pokemondatabase.fragments.list.PokemonListFragment;
import com.evanfuhr.pokemondatabase.models.Location;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import org.jetbrains.annotations.NonNls;

public class LocationDisplayActivity extends AppCompatActivity
        implements PokemonListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String LOCATION_ID = "location_id";

    Location mLocation = new Location();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Get pokemon id passed to this activity
        Intent intent = getIntent();
        mLocation.setId(intent.getIntExtra(LOCATION_ID, 0));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_display);

        LocationDAO locationDAO = new LocationDAO(this);
        mLocation = locationDAO.getLocation(mLocation);

        setTitle(mLocation.getRegion().getName() + " - " + mLocation.getName());
    }

    @Override
    public void onPokemonListFragmentInteraction(Pokemon pokemon) {
        int pokemon_id = pokemon.getId();

        //Build the intent to load the pokemon display
        Intent intent = new Intent(this, PokemonDisplayActivity.class);
        //Load the pokemon ID to send to the player sheet
        intent.putExtra(PokemonDisplayActivity.POKEMON_ID, pokemon_id);

        startActivity(intent);
    }
}
