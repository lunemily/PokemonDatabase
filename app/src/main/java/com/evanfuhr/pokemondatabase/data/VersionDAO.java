package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.interfaces.VersionDataInterface;
import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.models.Version;

import java.util.ArrayList;
import java.util.List;

public class VersionDAO extends DataBaseHelper implements VersionDataInterface {

    public VersionDAO(Context context) {
        super(context);
    }

    /**
     * Returns a list of all versions
     *
     * @return      An unfiltered list of Version objects
     * @see         Version
     */
    public List<Version> getAllVersions() {

        SQLiteDatabase db = this.getWritableDatabase();

        List<Version> versionList = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(VERSIONS, ID)
                        , field(VERSION_NAMES, NAME)
                        , field(VERSIONS, VERSION_GROUP_ID)
                        , field(VERSION_GROUPS, GENERATION_ID))
                .from(VERSIONS)
                .join(VERSION_NAMES)
                .on(field(VERSIONS, ID) + "=" + field(VERSION_NAMES, VERSION_ID))
                .join(VERSION_GROUPS)
                .on(field(VERSIONS, VERSION_GROUP_ID) + "=" + field(VERSION_GROUPS, ID))
                .where(field(VERSION_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();


        Cursor cursor = db.rawQuery(sql, null);

        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Version version = new Version();
                version.setID(Integer.parseInt(cursor.getString(0)));
                version.setName(cursor.getString(1));
                version.setGroupID(Integer.parseInt(cursor.getString(2)));
                version.setGenerationID(Integer.parseInt(cursor.getString(3)));
                //add version to list
                versionList.add(version);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return versionList;
    }
}
