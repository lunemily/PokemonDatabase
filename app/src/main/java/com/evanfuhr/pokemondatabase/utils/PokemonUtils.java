package com.evanfuhr.pokemondatabase.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evanfuhr.pokemondatabase.data.AbilityDAO;
import com.evanfuhr.pokemondatabase.data.MoveDAO;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Flavor;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

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

    public static List<String> getProseLinks(String prose) {

        List<String> links = new ArrayList<>();

        // Yay Regex!!!
        Pattern pattern = Pattern.compile("\\[(|[A-Za-z\\s]+)\\]\\{([a-z:\\-\\s]+)\\}");
        Matcher matcher = pattern.matcher(prose);

        while (matcher.find()) {
            links.add(matcher.group());
        }

        return links;
    }

    public static String replaceProseLinks(Context context, String prose) {

        List<String> proseLinks = getProseLinks(prose);
        String replacement;

        for (String link : proseLinks) {
            if (link.indexOf("]") > 1) {
                // Means [_%] so we already have the replacement text
                replacement = link.substring(1, link.indexOf("]"));
            } else {
                // Means we have to figure out the replacement text
                replacement = getProseLinkReplacement(context, link);
            }
            prose = prose.replace(link, replacement);
        }

        return prose;
    }

    public static String getProseLinkReplacement(Context context, String link) {
        // TODO: This is fragile :(
        String object = link.substring(link.indexOf("{") + 1, link.indexOf(":"));
        String identifier = link.substring(link.indexOf(":") + 1, link.indexOf("}"));
        String replacement;

        switch (object) {
            case "ability":
                AbilityDAO abilityDAO = new AbilityDAO(context);
                replacement = abilityDAO.getAbilityByIdentifier(identifier).getName();
                abilityDAO.close();
                break;
            case "move":
                MoveDAO moveDAO = new MoveDAO(context);
                replacement = moveDAO.getMoveByIdentifier(identifier).getName();
                moveDAO.close();
                break;
            default:
                Log.w("PROSE", "Prose reference object, not recognized: " + object);
                replacement = link;
                break;
        }
        return replacement;
    }
}
