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
        name = name.toLowerCase();
        if (name.contains("mega-x")) {
            return MEGA_X;
        } else if (name.contains("mega-y")) {
            return MEGA_Y;
        } else if (name.contains("mega")) {
            return MEGA;
        } else if (name.contains("alola")) {
            return ALOLA;
        } else if (name.contains("pirouette")) {
            return PIROUETTE;
        }
        return null;
    }

    public static String getShowdownForme(Forme forme) {
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

    public static String getSpriteForme(Forme forme) {
        if (forme == null) {
            return "";
        }
        switch (forme) {
            case MEGA:
                return "mega";
            case MEGA_X:
                return "megax";
            case MEGA_Y:
                return "megay";
            case ALOLA:
                return "alola";
            case PIROUETTE:
                return "pirouette";
        }
        return "";
    }
}
