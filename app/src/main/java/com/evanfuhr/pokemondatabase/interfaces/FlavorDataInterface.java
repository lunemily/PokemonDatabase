package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Flavor;

import java.util.List;

public interface FlavorDataInterface {

    List<Flavor> getAllFlavors();

    Flavor getFlavor(Flavor flavor);
}
