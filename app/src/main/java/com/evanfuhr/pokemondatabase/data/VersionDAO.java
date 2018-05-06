package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.interfaces.VersionDataInterface;
import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.models.Version;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

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
                version.setId(Integer.parseInt(cursor.getString(0)));
                version.setName(cursor.getString(1));
                version.setGroupId(Integer.parseInt(cursor.getString(2)));
                version.setGenerationId(Integer.parseInt(cursor.getString(3)));
                //add version to list
                versionList.add(version);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return versionList;
    }

    /**
     *
     * @return A loaded Version object
     */
    public Version getVersion() {
        SQLiteDatabase db = this.getWritableDatabase();
        Version version = new Version();

        SharedPreferences settings = getMyContext().getSharedPreferences(String.valueOf(R.string.gameVersionID), MODE_PRIVATE);
        int version_id = settings.getInt(String.valueOf(R.string.gameVersionID), DataBaseHelper.defaultVersionId);

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
                .and(field(VERSIONS, ID) + "=" + version_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                version.setId(Integer.parseInt(cursor.getString(0)));
                version.setName(cursor.getString(1));
                version.setGroupId(Integer.parseInt(cursor.getString(2)));
                version.setGenerationId(Integer.parseInt(cursor.getString(3)));
            }
            cursor.close();
        }
        db.close();

        return version;
    }
}
