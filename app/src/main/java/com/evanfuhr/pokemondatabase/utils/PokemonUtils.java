package com.evanfuhr.pokemondatabase.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;

import com.evanfuhr.pokemondatabase.models.Flavor;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.List;

public class PokemonUtils {

    @NonNull
    public static GradientDrawable getColorGradientByTypes(List<Type> types) {

        List<String> colors = new ArrayList<>();

        for (Type t : types) {
            colors.add(Type.getTypeColor(t.getId()));
        }

        return getColorGradient(colors);
    }

    @NonNull
    public static GradientDrawable getColorGradientByFlavors(List<Flavor> flavors) {

        List<String> colors = new ArrayList<>();

        for (Flavor f : flavors) {
            colors.add(Flavor.getFlavorColor(f.getId()));
        }

        return getColorGradient(colors);
    }

    @NonNull
    private static GradientDrawable getColorGradient(List<String> colors) {

        int[] color = {0, 0, 0, 0};
        if (colors.size() == 1) {
            color[0] = Color.parseColor(colors.get(0));
            color[1] = Color.parseColor(colors.get(0));
            color[2] = Color.parseColor(colors.get(0));
            color[3] = Color.parseColor(colors.get(0));
        }
        else {
            color[0] = Color.parseColor(colors.get(0));
            color[1] = Color.parseColor(colors.get(0));
            color[2] = Color.parseColor(colors.get(1));
            color[3] = Color.parseColor(colors.get(1));
        }

        return new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, color);
    }
}
