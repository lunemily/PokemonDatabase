package com.evanfuhr.pokemondatabase.utils;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;

import com.evanfuhr.pokemondatabase.data.VersionDAO;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

public class ExternalLink {

    /**
     * Returns a fully-formed <a> element to embed in the page
     *
     * It dynamically determines the type of entity and version
     */
    public static Spanned getSmogonLink(Object object, Context context) {
        String link;
        String version;
        String entity = "undefined";
        String name = "undefined";

        // Determine version string
        VersionDAO versionDAO = new VersionDAO(context);
        version = versionDAO.getVersion().getSmogonVersion();

        // Determine entity type and pull name
        if (object instanceof Pokemon) {
            name = ((Pokemon) object).getName();
            entity = "pokemon";
        } else if (object instanceof Type) {
            name = ((Type) object).getName();
            entity = "type";
        }

        link = "http://www.smogon.com/dex/" + version + "/" + entity + "/" + name;

        return Html.fromHtml("<a href='" + link + "'>Smogon</a>");
    }

    /**
     * Returns a fully-formed <a> element to embed in the page
     *
     * It dynamically determines the type of entity and version
     */
    public static Spanned getBulbapediaLink(Object object, Context context) {
        String link;
        String entity = "undefined";
        String name = "undefined";

        // Determine version string
        VersionDAO versionDAO = new VersionDAO(context);

        // Determine entity type and pull name
        if (object instanceof Pokemon) {
            name = ((Pokemon) object).getName();
            entity = "Pok%C3%A9mon";
        } else if (object instanceof Type) {
            name = ((Type) object).getName();
            entity = "type";
        }

        link = "https://bulbapedia.bulbagarden.net/wiki/" + name + "_(" + entity + ")";

        return Html.fromHtml("<a href='" + link + "'>Bulbapedia</a>");
    }

    /**
     * Returns a fully-formed <a> element to embed in the page
     *
     * It dynamically determines the type of entity and version
     */
    public static Spanned getSerebiiLink(Object object, Context context) {
        String entity = "undefined";
        String link;
        String name = "undefined";
        String version;

        // Determine version string
        VersionDAO versionDAO = new VersionDAO(context);
        version = versionDAO.getVersion().getSerebiiVersion(object);

        // Determine entity type and pull name
        if (object instanceof Pokemon) {
            name = ((Pokemon) object).getThreeDigitStringId();
            entity = "pokedex";
        } else if (object instanceof Type) {
            name = ((Type) object).getName().toLowerCase();
            entity = "attackdex";
        }

        // When version is not empty it is preceded by "-"
        link = "https://www.serebii.net/" + entity + version + "/" + name + ".shtml";

        return Html.fromHtml("<a href='" + link + "'>Serebii</a>");
    }
}
