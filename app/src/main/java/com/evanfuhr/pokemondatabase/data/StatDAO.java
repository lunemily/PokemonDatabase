package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.interfaces.StatDataInterface;
import com.evanfuhr.pokemondatabase.models.Stat;

import java.util.ArrayList;
import java.util.List;

public class StatDAO extends DataBaseHelper
        implements StatDataInterface {

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public StatDAO(Context context) {
        super(context);
    }

    /**
     * Returns a list of all stats
     *
     * @return      An unfiltered list of Stat objects
     * @see         Stat
     */
    public List<Stat> getAllStats() {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Stat> statList = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(STATS, ID)
                        , field(STAT_NAMES, NAME))
                .from(STATS)
                .join(STAT_NAMES)
                .on(field(STATS, ID) + "=" + field(STAT_NAMES, STAT_ID))
                .where(field(STAT_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);

        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Stat stat = new Stat();
                stat.setId(Integer.parseInt(cursor.getString(0)));
                stat.setName(cursor.getString(1));
                //add stat to list
                statList.add(stat);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return statList;
    }

    /**
     * Returns a Stat object with most of its non-list data
     *
     * @param   stat A Stat object to be modified with additional data
     * @return          The modified input is returned
     * @see             Stat
     */
    public Stat getStatById(Stat stat) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = SQLiteQueryBuilder
                .select(field(STATS, ID)
                        , field(STAT_NAMES, NAME)
                        , field(STATS, DAMAGE_CLASS_ID)
                        , field(STATS, IS_BATTLE_ONLY)
                        , field(STATS, GAME_INDEX))
                .from(STATS)
                .join(STAT_NAMES)
                .on(field(STATS, ID) + "=" + field(STAT_NAMES, STAT_ID))
                .where(field(STATS, ID) + "=" + stat.getId())
                .and(field(STAT_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                stat.setId(Integer.parseInt(cursor.getString(0)));
                stat.setName(cursor.getString(1));
                if (cursor.getString(2) != null) {
                    stat.setDamageClassId(Integer.parseInt(cursor.getString(2)));
                }
                stat.setBattleOnly(Boolean.getBoolean(cursor.getString(3)));
                if (cursor.getString(4) != null) {
                    stat.setGameIndex(Integer.parseInt(cursor.getString(4)));
                }
            }
            cursor.close();
        }

        return stat;
    }
}
