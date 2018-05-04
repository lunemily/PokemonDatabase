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
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class PokemonUtils {

    @NonNull
    public static GradientDrawable getColorGradientByTypes(List<Type> types) {

        //TODO: Null check and return grey

        List<String> colors = new ArrayList<>();

        for (Type t : types) {
            colors.add(Type.getTypeColor(t.getId()));
        }

        return getColorGradient(colors);
    }

    @NonNull
    public static GradientDrawable getColorGradientByFlavors(List<Flavor> flavors) {

        //TODO: Null check and return grey

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
        Pattern pattern = Pattern.compile("(\\[(|[A-Za-z\\s]+)\\]\\{([a-z:\\-\\s]+)\\}|\\$effect_chance)");
        Matcher matcher = pattern.matcher(prose);

        while (matcher.find()) {
            links.add(matcher.group());
        }

        return links;
    }

    public static String replaceProseLinks(Context context, String prose) {
        return replaceProseLinks(context, prose, 0);
    }

    public static String replaceProseLinks(Context context, String prose, int objectId) {

        List<String> proseLinks = getProseLinks(prose);
        String replacement;

        for (String link : proseLinks) {
            if (link.indexOf("]") > 1) {
                // Means [_%] so we already have the replacement text
                replacement = link.substring(1, link.indexOf("]"));
            } else {
                // Means we have to figure out the replacement text
                replacement = getProseLinkReplacement(context, link, objectId);
            }
            prose = prose.replace(link, replacement);
        }

        return prose;
    }

    private static String getProseLinkReplacement(Context context, String link, int objectId) {
        // TODO: This is fragile :(
        String object = "";
        String identifier = "";
        if (objectId > 0) {
            object = "effectChance";
        } else {
            object = link.substring(link.indexOf("{") + 1, link.indexOf(":"));
            identifier = link.substring(link.indexOf(":") + 1, link.indexOf("}"));
        }
        String replacement;
        AbilityDAO abilityDAO = new AbilityDAO(context);
        MoveDAO moveDAO = new MoveDAO(context);

        switch (object) {
            case "ability":
                replacement = abilityDAO.getAbilityByIdentifier(identifier).getName();
                break;
            case "move":
                replacement = moveDAO.getMoveByIdentifier(identifier).getName();
                break;
            case "effectChance":
                Move move = new Move();
                move.setId(objectId);
                replacement = Integer.toString(moveDAO.getMoveMetaById(move).getEffectChance());
                break;
            default:
                Log.w("PROSE", "Prose reference object, not recognized: " + object);
                replacement = link;
                break;
        }
        abilityDAO.close();
        moveDAO.close();

        return replacement;
    }

    public static String convertEfficacyToMultiplier(float efficacy) {
        String multiplier;
        switch (Math.round(efficacy * 100)) {
            case 0:
                multiplier = "x0";
                break;
            case 25:
                multiplier = "x1/4";
                break;
            case 50:
                multiplier = "x1/2";
                break;
            case 200:
                multiplier = "x2";
                break;
            case 400:
                multiplier = "x4";
                break;
            default:
                multiplier = "xERROR";
                break;
        }

        return multiplier;
    }
}
