package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import java.util.List;

public interface AbilityDataInterface {

    List<Ability> getAllAbilities();

    Ability getAbility(Ability ability);

    Ability getAbility(String identifier);

    List<Ability> getAbilities(Pokemon pokemon);
}
