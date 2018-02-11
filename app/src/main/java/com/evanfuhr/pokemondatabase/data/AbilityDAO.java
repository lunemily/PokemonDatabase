package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.interfaces.AbilityDataInterface;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Pokemon;

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

        String selectQuery = "SELECT " + TABLE_ABILITIES + "." + KEY_ID +
                ", " + TABLE_ABILITY_NAMES + "." + KEY_NAME +
            " FROM " + TABLE_ABILITIES +
                ", " + TABLE_ABILITY_NAMES +
            " WHERE " + TABLE_ABILITIES + "." + KEY_ID + " = '" + ability.getID() + "'" +
                " AND " + TABLE_ABILITIES + "." + KEY_ID + " = " + TABLE_ABILITY_NAMES + "." + KEY_ABILITY_ID +
                " AND " + TABLE_ABILITY_NAMES + "." + KEY_LOCAL_LANGUAGE_ID + " = '" + _language_id + "'" +
            " ORDER BY " + TABLE_POKEMON_ABILITIES + "." + KEY_SLOT + " ASC"
            ;

        Cursor cursor = db.rawQuery(selectQuery, null);
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
