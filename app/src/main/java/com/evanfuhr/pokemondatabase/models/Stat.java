package com.evanfuhr.pokemondatabase.models;

import android.support.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

public class Stat extends BaseNamedObject {

    private int damageClassId;
    private boolean isBattleOnly;
    private int gameIndex;

    public Stat() {
        super();
    }

    public Stat(int id) {
        super(id);
    }

    public int getDamageClassId() {
        return damageClassId;
    }

    public void setDamageClassId(int damageClassId) {
        this.damageClassId = damageClassId;
    }

    public boolean isBattleOnly() {
        return isBattleOnly;
    }

    public void setBattleOnly(boolean battleOnly) {
        isBattleOnly = battleOnly;
    }

    public int getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(int gameIndex) {
        this.gameIndex = gameIndex;
    }

    public enum PrimaryStat {
        HP,     // 1
        Atk,    // 2
        Def,    // 3
        SpA,    // 4
        SpD,    // 5
        Spe     // 6
        ;

        /**
         *
         * @param rawStats   Takes the form of "## HP[ / ## Atk]..."
         */
        public static Map<PrimaryStat, Integer> parseStats(String rawStats) {
            Map<PrimaryStat, Integer> map = new HashMap<>();
            String[] stats = rawStats.split(" / ");

            for (String stat : stats) {
                stat = stat.replace("\u0160", "\u0032");
                if (stat.contains("HP")) {
                    map.put(HP, Integer.valueOf(stat.replace("HP", "").trim()));
                }
                if (stat.contains("Atk")) {
                    map.put(Atk, Integer.valueOf(stat.replace("Atk", "").trim()));
                }
                if (stat.contains("Def")) {
                    map.put(Def, Integer.valueOf(stat.replace("Def", "").trim()));
                }
                if (stat.contains("SpA")) {
                    map.put(SpA, Integer.valueOf(stat.replace("SpA", "").trim()));
                }
                if (stat.contains("SpD")) {
                    map.put(SpD, Integer.valueOf(stat.replace("SpD", "").trim()));
                }
                if (stat.contains("Spe")) {
                    map.put(Spe, Integer.valueOf(stat.replace("Spe", "").trim()));
                }
            }
            return map;
        }

        @Nullable
        @Contract(pure = true)
        public static PrimaryStat get(int i) {
            switch(i) {
                case 1:
                    return HP;
                case 2:
                    return Atk;
                case 3:
                    return Def;
                case 4:
                    return SpA;
                case 5:
                    return SpD;
                case 6:
                    return Spe;
            }
            return null;
        }
    }
}
