package com.evanfuhr.pokemondatabase.models;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.Contract;

public enum Forme {
    STANDARD,
    MEGA,
    MEGA_X,
    MEGA_Y,
    PRIMAL,
    SPRING,
    SUMMER,
    AUTUMN,
    WINTER,
    NORMAL,
    FIGHTING,
    GRASS,
    FIRE,
    WATER,
    ELECTRIC,
    FLYING,
    PSYCHIC,
    POISON,
    GHOST,
    DARK,
    ROCK,
    GROUND,
    STEEL,
    ICE,
    DRAGON,
    FAIRY,
    BUG,
    PIROUETTE,
    RADIANT_SUN,
    FULL_MOON,
    DAWN_WINGS,
    DUSK_MANE,
    ULTRA,
    POM_POM,
    BAILE,
    PU_A,
    SENSU,
    RED,
    ORANGE,
    YELLOW,
    GREEN,
    BLUE,
    INDIGO,
    VIOLET,
    DISGUISED,
    BUSTED,
    ORIGINAL,
    ZENITH,
    ALOLA
    ;

    public static Forme parseShowdownForme(String name) {
        if (name.contains("Mega-X")) {
            return MEGA_X;
        } else if (name.contains("Mega-Y")) {
            return MEGA_Y;
        } else if (name.contains("Mega")) {
            return MEGA;
        } else if (name.contains("Alola")) {
            return ALOLA;
        } else if (name.contains("Pirouette")) {
            return PIROUETTE;
        }
        return null;
    }

    public static String getShowndownForme(Forme forme) {
        switch (forme) {
            case MEGA:
                return "Mega";
            case MEGA_X:
                return "Mega-X";
            case MEGA_Y:
                return "Mega-Y";
            case ALOLA:
                return "Alola";
            case PIROUETTE:
                return "Pirouette";
        }
        return "";
    }
}
