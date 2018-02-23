package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Pokemon;

public interface AbilityDataInterface {

    Ability getAbilityByID(Ability ability);
}
