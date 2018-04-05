package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.EggGroup;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.List;

public interface PokemonDataInterface {

    List<Pokemon> getAllPokemon();

    List<Pokemon> getAllPokemon(String nameSearchParam);

    Pokemon getPokemonByID(Pokemon pokemon);

    List<Ability> getAbilitiesForPokemon(Pokemon pokemon);

    List<EggGroup> getEggGroupsForPokemon(Pokemon pokemon);

    List<Move> getMovesForPokemon(Pokemon pokemon);

    List<Type> getTypesForPokemon(Pokemon pokemon);
}
