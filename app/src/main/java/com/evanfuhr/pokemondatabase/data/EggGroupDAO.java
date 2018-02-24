package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.interfaces.EggGroupDataInterface;
import com.evanfuhr.pokemondatabase.models.EggGroup;

public class EggGroupDAO extends DataBaseHelper implements EggGroupDataInterface {

    public EggGroupDAO(Context context) {
        super(context);
    }

    /**
     * Returns an Ability object with its name
     *
     * @param   eggGroup    An EggGroup object to be modified with additional data
     * @return              The modified input is returned
     * @see                 EggGroup
     */
    public EggGroup getEggGroupByID(EggGroup eggGroup) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = SQLiteQueryBuilder
                .select(field(EGG_GROUP_PROSE, EGG_GROUP_ID)
                        , field(EGG_GROUP_PROSE, NAME))
                .from(EGG_GROUP_PROSE)
                .where(field(EGG_GROUP_PROSE, EGG_GROUP_ID) + "=" + eggGroup.getID())
                .and(field(EGG_GROUP_PROSE, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                eggGroup.setID(Integer.parseInt(cursor.getString(0)));
                eggGroup.setName(cursor.getString(1));
            }
            cursor.close();
        }

        return eggGroup;
    }
}
