package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.interfaces.NatureDataInterface;
import com.evanfuhr.pokemondatabase.models.Flavor;
import com.evanfuhr.pokemondatabase.models.Nature;

import java.util.ArrayList;
import java.util.List;

public class NatureDAO extends DataBaseHelper implements NatureDataInterface {

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public NatureDAO(Context context) {
        super(context);
    }

    /**
     * Returns a list of all natures
     *
     * @return      An unfiltered list of Nature objects
     * @see         Nature
     */
    public List<Nature> getAllNatures() {
        return getAllNatures("%");
    }

    /**
     * Returns a list of all natures that contain nameSearchParam
     *
     * @param   nameSearchParam A substring to filter Nature names with
     * @return                  A filtered list of Nature objects
     * @see                     Nature
     */
    public List<Nature> getAllNatures(String nameSearchParam) {

        SQLiteDatabase db = this.getWritableDatabase();

        List<Nature> natureList = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(NATURES, ID)
                        , field(NATURE_NAMES, NAME))
                .from(NATURES)
                .join(NATURE_NAMES)
                .on(field(NATURES, ID) + "=" + field(NATURE_NAMES, NATURE_ID))
                .where(field(NATURE_NAMES, NAME) + " LIKE LOWER('%" + nameSearchParam + "%')")
                .and(field(NATURE_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);

        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Nature nature = new Nature();
                nature.setId(Integer.parseInt(cursor.getString(0)));
                nature.setName(cursor.getString(1));
                //add pokemon to list
                natureList.add(nature);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return natureList;
    }

    /**
     * Returns a Nature object with most of its non-list data
     *
     * @param   nature A Nature object to be modified with additional data
     * @return          The modified input is returned
     * @see             Nature
     */
    public Nature getNatureById(Nature nature) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = SQLiteQueryBuilder
                .select(field(NATURES, ID)
                        , field(NATURE_NAMES, NAME)
                        , field(NATURES, DECREASED_STAT_ID)
                        , field(NATURES, INCREASED_STAT_ID)
                        , field(NATURES, HATES_FLAVOR_ID)
                        , field(NATURES, LIKES_FLAVOR_ID))
                .from(ABILITIES)
                .join(ABILITY_NAMES)
                .on(field(ABILITIES, ID) + "=" + field(ABILITY_NAMES, ABILITY_ID))
                .where(field(ABILITIES, ID) + "=" + nature.getId())
                .and(field(ABILITY_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                nature.setId(Integer.parseInt(cursor.getString(0)));
                nature.setName(cursor.getString(1));
                nature.setDecreasedStatID(Integer.parseInt(cursor.getString(2)));
                nature.setIncreasedStatID(Integer.parseInt(cursor.getString(3)));

                Flavor hates = new Flavor();
                hates.setId(Integer.parseInt(cursor.getString(4)));
                nature.setHatesFlavor(hates);

                Flavor likes = new Flavor();
                likes.setId(Integer.parseInt(cursor.getString(5)));
                nature.setLikesFlavor(likes);
            }
            cursor.close();
        }

        return nature;
    }
}
