package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Stat;

import java.util.List;

public interface StatDataInterface {

    List<Stat> getAllStats();

    List<Stat> getAllStats(String nameSearchParam);

    Stat getStatById(Stat stat);
}
