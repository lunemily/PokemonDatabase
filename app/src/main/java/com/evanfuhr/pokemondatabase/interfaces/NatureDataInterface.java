package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Nature;

import java.util.List;

public interface NatureDataInterface {

    List<Nature> getAllNatures();

    List<Nature> getAllNatures(String nameSearchParam);

    Nature getNatureById(Nature nature);
}
