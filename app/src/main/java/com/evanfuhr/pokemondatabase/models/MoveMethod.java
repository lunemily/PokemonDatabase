package com.evanfuhr.pokemondatabase.models;

import android.support.annotation.Nullable;

import org.jetbrains.annotations.Contract;

public enum MoveMethod {
    LEVEL_UP,                   //1
    EGG,                        //2
    TUTOR,                      //3
    MACHINE,                    //4
    STADIUM_SURFING_PIKACHU,    //5
    LIGHT_BALL_EGG,             //6
    COLOSSEUM_PURIFICATION,     //7
    XD_SHADOW,                  //8
    XD_PURIFICATION,            //9
    FORM_CHANGE                 //10
    ;

    @Nullable
    @Contract(pure = true)
    public static MoveMethod get(int i) {
        switch(i) {
            case 1:
                return LEVEL_UP;
            case 2:
                return EGG;
            case 3:
                return TUTOR;
            case 4:
                return MACHINE;
            case 5:
                return STADIUM_SURFING_PIKACHU;
            case 6:
                return LIGHT_BALL_EGG;
            case 7:
                return COLOSSEUM_PURIFICATION;
            case 8:
                return XD_SHADOW;
            case 9:
                return XD_PURIFICATION;
            case 10:
                return FORM_CHANGE;
        }
        return null;
    }
}
