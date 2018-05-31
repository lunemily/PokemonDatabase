package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Evolution;
import com.evanfuhr.pokemondatabase.models.EvolutionChain;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import java.util.List;

public interface EvolutionDataInterface {

    List<Evolution> getAllEvolutions(Pokemon pokemon);

    List<Evolution> getAllEvolutions(EvolutionChain evolutionChain);

    EvolutionChain getEvolutionChain(Pokemon pokemon);
}
