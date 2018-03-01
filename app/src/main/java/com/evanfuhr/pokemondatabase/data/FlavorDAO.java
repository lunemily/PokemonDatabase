package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.interfaces.FlavorDataInterface;
import com.evanfuhr.pokemondatabase.models.Flavor;

import java.util.ArrayList;
import java.util.List;

public class FlavorDAO extends DataBaseHelper
        implements FlavorDataInterface {

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public FlavorDAO(Context context) {
        super(context);
    }

    /**
     * Returns a list of all flavors
     *
     * @return      An unfiltered list of Flavor objects
     * @see         Flavor
     */
    public List<Flavor> getAllFlavors() {
        return getAllFlavors("%");
    }

    /**
     * Returns a list of all flavors that contain nameSearchParam
     *
     * @param   nameSearchParam A substring to filter Flavor names with
     * @return                  A filtered list of Flavor objects
     * @see                     Flavor
     */
    public List<Flavor> getAllFlavors(String nameSearchParam) {

        SQLiteDatabase db = this.getWritableDatabase();

        List<Flavor> flavorList = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(CONTEST_TYPES, ID)
                        , field(CONTEST_TYPE_NAMES, NAME))
                .from(CONTEST_TYPES)
                .join(CONTEST_TYPE_NAMES)
                .on(field(CONTEST_TYPES, ID) + "=" + field(CONTEST_TYPE_NAMES, CONTEST_TYPE_ID))
                .where(field(CONTEST_TYPE_NAMES, NAME) + " LIKE LOWER('%" + nameSearchParam + "%')")
                .and(field(CONTEST_TYPE_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);

        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Flavor flavor = new Flavor();
                flavor.setId(Integer.parseInt(cursor.getString(0)));
                flavor.setName(cursor.getString(1));
                //add stat to list
                flavorList.add(flavor);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return flavorList;
    }

    /**
     * Returns a Flavor object with most of its non-list data
     *
     * @param   flavor A Flavor object to be modified with additional data
     * @return          The modified input is returned
     * @see             Flavor
     */
    public Flavor getFlavorById(Flavor flavor) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = SQLiteQueryBuilder
                .select(field(CONTEST_TYPES, ID)
                        , field(CONTEST_TYPE_NAMES, NAME)
                        , field(CONTEST_TYPE_NAMES, FLAVOR)
                        , field(CONTEST_TYPE_NAMES, COLOR))
                .from(CONTEST_TYPES)
                .join(CONTEST_TYPE_NAMES)
                .on(field(CONTEST_TYPES, ID) + "=" + field(CONTEST_TYPE_NAMES, CONTEST_TYPE_ID))
                .where(field(CONTEST_TYPES, ID) + "=" + flavor.getId())
                .and(field(CONTEST_TYPE_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                flavor.setId(Integer.parseInt(cursor.getString(0)));
                //Contest type flavor.setContestName(Integer.parseInt(cursor.getString(1)));
                flavor.setName(cursor.getString(2));
                flavor.setColor(Flavor.getFlavorColor(flavor.getId()));
            }
            cursor.close();
        }

        return flavor;
    }
}
