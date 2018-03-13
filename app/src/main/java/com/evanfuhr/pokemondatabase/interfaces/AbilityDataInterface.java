package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import java.util.List;

public interface AbilityDataInterface {

    List<Ability> getAllAbilities();

    List<Ability> getAllAbilities(String nameSearchParam);

    Ability getAbilityByID(Ability ability);

    List<Pokemon> getPokemonByAbility(Ability ability);
}
