package com.evanfuhr.pokemondatabase.activities.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.display.LocationDisplayActivity;
import com.evanfuhr.pokemondatabase.fragments.list.LocationListFragment;
import com.evanfuhr.pokemondatabase.models.Location;

import org.jetbrains.annotations.NonNls;

public class LocationListActivity extends AppCompatActivity
        implements LocationListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String LOCATIONS = "Locations";
    @NonNls
    public static final String MENU_ITEM_NOT_IMPLEMENTED_YET = "Menu item not implemented yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        setTitle(LOCATIONS);
    }

    public void onListFragmentInteraction(Location location) {

        // Build the intent to load the ability display
        Intent intent = new Intent(this, LocationDisplayActivity.class);
        // Load the ability id into the intent
        intent.putExtra(LocationDisplayActivity.LOCATION_ID, location.getId());

        startActivity(intent);
    }
}
