package com.evanfuhr.pokemondatabase.models;

import android.support.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import java.util.Map;

public class Evolution {

    private Pokemon mBeforePokemon;
    private Pokemon mAfterPokemon;
    private Trigger mTrigger;
    private Map<Detail, Integer> mTriggerDetails;

    public Pokemon getBeforePokemon() {
        return mBeforePokemon;
    }

    public void setBeforePokemon(Pokemon beforePokemon) {
        this.mBeforePokemon = beforePokemon;
    }

    public Pokemon getAfterPokemon() {
        return mAfterPokemon;
    }

    public void setAfterPokemon(Pokemon afterPokemon) {
        this.mAfterPokemon = afterPokemon;
    }

    public Trigger getTrigger() {
        return mTrigger;
    }

    public void setTrigger(Trigger trigger) {
        this.mTrigger = trigger;
    }

    public Map<Detail, Integer> getTriggerDetails() {
        return mTriggerDetails;
    }

    public void setTriggerDetails(Map<Detail, Integer> triggerDetails) {
        this.mTriggerDetails = triggerDetails;
    }

    public enum Trigger {
        LEVEL_UP,   //1
        TRADE,      //2
        USE_ITEM,   //3
        SHED        //4
        ;

        @Nullable
        @Contract(pure = true)
        public static Trigger get(int i) {
            switch(i) {
                case 1:
                    return LEVEL_UP;
                case 2:
                    return TRADE;
                case 3:
                    return USE_ITEM;
                case 4:
                    return SHED;
            }
            return null;
        }
    }

    public enum Detail {
        TRIGGER_ITEM_ID,
        MINIMUM_LEVEL,
        LOCATION_ID,
        HELD_ITEM_ID,
        TIME_OF_DAY,
        KNOWN_MOVE_ID,
        KNOWN_MOVE_TYPE,
        MINIMUM_HAPPINESS,
        MINIMUM_BEAUTY,
        MINIMUM_AFFECTION,
        RELATIVE_PHYSICAL_STATS,
        PARTY_SPECIES_ID,
        PARTY_TYPE_ID,
        TRADE_SPECIES_ID,
        NEEDS_OVERWORLD_RAIN,
        TURN_UPSIDE_DOWN,
        GENDER_ID
        ;

        public static Integer getTimeOfDay(String timeOfDay) {
            return timeOfDay.equals("day") ? Integer.valueOf(0) : Integer.valueOf(1);
        }

        @Nullable
        public static String getTimeOfDay(Integer timeOfDay) {
            return timeOfDay == 0 ? "day" : "night";
        }
    }
}
