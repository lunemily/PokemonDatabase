package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Stat;

import java.util.List;

public interface StatDataInterface {

    List<Stat> getAllStats();

    Stat getStatById(Stat stat);
}
