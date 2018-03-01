package com.evanfuhr.pokemondatabase.models;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.Contract;

public class Flavor {

    public Flavor() {
    }

    private int id;
    private String name;
    private String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // Static methods

    @NonNull
    @Contract(pure = true)
    // TODO: Use an enum for this
    public static String getFlavorColor(int id) {
        switch (id) {
            case 1:
                return "#F5AC78"; // Spicy  | Attack            | Cool
            case 2:
                return "#9DB7F5"; // Dry    | Special Attack    | Beauty
            case 3:
                return "#FA92B2"; // Sweet  | Speed             | Cute
            case 4:
                return "#A7DB8D"; // Bitter | Special Defense   | Smart
            case 5:
                return "#FAE078"; // Sour   | Defense           | Tough
            default:
                return "#000000";
        }
    }
}
