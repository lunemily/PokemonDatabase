package com.evanfuhr.pokemondatabase.models;

import java.util.HashMap;
import java.util.Map;

public enum EffortValue {
    HP,
    Atk,
    Def,
    SpA,
    SpD,
    Spe
    ;

    /**
     *
     * @param rawEVs   Takes the form of "## HP[ / ## Atk]..."
     */
    public static Map<EffortValue, Integer> parseEVs(String rawEVs) {
        Map<EffortValue, Integer> map = new HashMap<>();
        String[] evs = rawEVs.split(" / ");

        for (String ev : evs) {
            if (ev.contains("HP")) {
                map.put(HP, Integer.valueOf(ev.replace("HP", "").trim()));
            }
            if (ev.contains("Atk")) {
                map.put(Atk, Integer.valueOf(ev.replace("Atk", "").trim()));
            }
            if (ev.contains("Def")) {
                map.put(Def, Integer.valueOf(ev.replace("Def", "").trim()));
            }
            if (ev.contains("SpA")) {
                map.put(SpA, Integer.valueOf(ev.replace("SpA", "").trim()));
            }
            if (ev.contains("SpD")) {
                map.put(SpD, Integer.valueOf(ev.replace("SpD", "").trim()));
            }
            if (ev.contains("Spe")) {
                map.put(Spe, Integer.valueOf(ev.replace("Spe", "").trim()));
            }
        }
        return map;
    }
}
