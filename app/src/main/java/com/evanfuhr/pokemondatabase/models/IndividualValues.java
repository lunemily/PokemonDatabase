package com.evanfuhr.pokemondatabase.models;

import java.util.HashMap;
import java.util.Map;

public enum IndividualValues {
    HP,
    Atk,
    Def,
    SpA,
    SpD,
    Spe
    ;

    /**
     *
     * @param rawIVs   Takes the form of "## HP[ / ## Atk]..."
     */
    public static Map<IndividualValues, Integer> parseIVs(String rawIVs) {
        Map<IndividualValues, Integer> map = new HashMap<>();
        String[] ivs = rawIVs.split(" / ");

        for (String iv : ivs) {
            if (iv.contains("HP")) {
                map.put(HP, Integer.valueOf(iv.replace("HP", "").trim()));
            }
            if (iv.contains("Atk")) {
                map.put(Atk, Integer.valueOf(iv.replace("Atk", "").trim()));
            }
            if (iv.contains("Def")) {
                map.put(Def, Integer.valueOf(iv.replace("Def", "").trim()));
            }
            if (iv.contains("SpA")) {
                map.put(SpA, Integer.valueOf(iv.replace("SpA", "").trim()));
            }
            if (iv.contains("SpD")) {
                map.put(SpD, Integer.valueOf(iv.replace("SpD", "").trim()));
            }
            if (iv.contains("Spe")) {
                map.put(Spe, Integer.valueOf(iv.replace("Spe", "").trim()));
            }
        }
        return map;
    }
}
