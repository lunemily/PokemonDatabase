package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.EggGroup;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import java.util.List;

public interface EggGroupDataInterface {

    List<EggGroup> getAllEggGroups();

    EggGroup getEggGroup(EggGroup eggGroup);

    List<EggGroup> getEggGroups(Pokemon pokemon);
}
