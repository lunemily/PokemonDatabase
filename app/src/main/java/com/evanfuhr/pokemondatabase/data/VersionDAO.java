package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.models.Version;

import java.util.ArrayList;
import java.util.List;

public class VersionDAO extends DataBaseHelper {

    public VersionDAO(Context context) {
        super(context);
    }

    public List<Version> getAllVersions() {

        SQLiteDatabase db = this.getWritableDatabase();

        List<Version> versionList = new ArrayList<>();

        String selectQuery = select + tableField(TABLE_VERSIONS, KEY_ID) +
                comma + tableField(TABLE_VERSION_NAMES, KEY_NAME) +
                comma + tableField(TABLE_VERSIONS, KEY_VERSION_GROUP_ID) +
                comma + tableField(TABLE_VERSION_GROUPS, KEY_GENERATION_ID) +
                from + TABLE_VERSIONS +
                comma + TABLE_VERSION_NAMES +
                comma + TABLE_VERSION_GROUPS +
                where + tableField(TABLE_VERSIONS, KEY_ID) + equals + tableField(TABLE_VERSION_NAMES, KEY_VERSION_ID) +
                and + tableField(TABLE_VERSIONS, KEY_VERSION_GROUP_ID) + equals + tableField(TABLE_VERSION_GROUPS, KEY_ID) +
                and + tableField(TABLE_VERSION_NAMES, KEY_LOCAL_LANGUAGE_ID) + equals + _language_id;


        Cursor cursor = db.rawQuery(selectQuery, null);

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
