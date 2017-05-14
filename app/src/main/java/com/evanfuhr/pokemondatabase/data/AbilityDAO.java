package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Pokemon;

public class AbilityDAO extends DataBaseHelper {

    public AbilityDAO(Context context) {
        super(context);
    }

    //Get Pokemon attributes
    public Ability getAbilityByIDForPokemon(Ability ability, Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT " + TABLE_ABILITIES + "." + KEY_ID +
                ", " + TABLE_ABILITY_NAMES + "." + KEY_NAME +
                ", " + TABLE_POKEMON_ABILITIES + "." + KEY_SLOT +
                ", " + TABLE_POKEMON_ABILITIES + "." + KEY_IS_HIDDEN +
            " FROM " + TABLE_ABILITIES +
                ", " + TABLE_ABILITY_NAMES +
                ", " + TABLE_POKEMON_ABILITIES +
                ", " + TABLE_POKEMON_SPECIES +
            " WHERE " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = '" + pokemon.getID() + "'" +
                " AND " + TABLE_ABILITIES + "." + KEY_ID + " = '" + ability.getID() + "'" +
                " AND " + TABLE_ABILITIES + "." + KEY_ID + " = " + TABLE_POKEMON_ABILITIES + "." + KEY_ABILITY_ID +
                " AND " + TABLE_ABILITIES + "." + KEY_ID + " = " + TABLE_ABILITY_NAMES + "." + KEY_ABILITY_ID +
                " AND " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = " + TABLE_POKEMON_ABILITIES + "." + KEY_POKEMON_ID +
                " AND " + TABLE_ABILITY_NAMES + "." + KEY_LOCAL_LANGUAGE_ID + " = '" + _language_id + "'" +
            " ORDER BY " + TABLE_POKEMON_ABILITIES + "." + KEY_SLOT + " ASC"
            ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                ability.setID(Integer.parseInt(cursor.getString(0)));
                ability.setName(cursor.getString(1));
                ability.setSlot(Integer.parseInt(cursor.getString(2)));
                ability.setIsHidden("1".equals(cursor.getString(3)));
            }
            cursor.close();
        }

        return ability;
    }
}
