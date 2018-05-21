package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Location;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import java.util.List;

public interface LocationDataInterface {

    List<Location> getAllLocations();

    List<Location> getLocations(Pokemon pokemon);

    Location getLocation(Location location);
}
