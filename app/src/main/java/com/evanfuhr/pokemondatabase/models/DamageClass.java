package com.evanfuhr.pokemondatabase.models;

import android.support.annotation.Nullable;

import org.jetbrains.annotations.Contract;

public enum DamageClass {
    STATUS,     //1
    PHYSICAL,   //2
    SPECIAL     //3
    ;

    @Nullable
    @Contract(pure = true)
    public static DamageClass get(int i) {
        switch(i) {
            case 1:
                return STATUS;
            case 2:
                return PHYSICAL;
            case 3:
                return SPECIAL;
        }
        return null;
    }

    public static String getName(DamageClass dc) {
        switch (dc) {
            case STATUS:
                return "Status";
            case PHYSICAL:
                return "Physical";
            case SPECIAL:
                return "Special";
        }
        return null;
    }
}
