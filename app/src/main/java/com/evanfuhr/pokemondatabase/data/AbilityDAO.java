package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.interfaces.AbilityDataInterface;
import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.models.Ability;

public class AbilityDAO extends DataBaseHelper implements AbilityDataInterface {

    public AbilityDAO(Context context) {
        super(context);
    }

    /**
     * Returns an Ability object with its name
     *
     * @param   ability An ability object to be modified with additional data
     * @return          The modified input is returned
     * @see             Ability
     */
    public Ability getAbilityByID(Ability ability) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = SQLiteQueryBuilder
                .select(field(ABILITIES, ID)
                        , field(ABILITY_NAMES, NAME))
                .from(ABILITIES)
                .join(ABILITY_NAMES)
                .on(field(ABILITIES, ID) + "=" + field(ABILITY_NAMES, ABILITY_ID))
                .where(field(ABILITIES, ID) + "=" + ability.getID())
                .and(field(ABILITY_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                ability.setID(Integer.parseInt(cursor.getString(0)));
                ability.setName(cursor.getString(1));
            }
            cursor.close();
        }

        return ability;
    }
}
