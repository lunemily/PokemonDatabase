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
}
