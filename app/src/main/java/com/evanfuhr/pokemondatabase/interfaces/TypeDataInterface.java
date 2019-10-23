package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.List;

public interface TypeDataInterface {

    List<Type> getAllTypes();

    Type getType(Type type);

    Type getType(String identifier);

    List<Type> getTypes(Pokemon pokemon);

    List<Type> getSingleTypeEfficacy(Type type);

    List<Type> getSingleTypeEfficacy(Type type, boolean forPokemon);

    List<Type> getDualTypeEfficacy(Type type1, Type type2);
}
