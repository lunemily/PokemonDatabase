package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.interfaces.EggGroupDataInterface;
import com.evanfuhr.pokemondatabase.models.EggGroup;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class EggGroupDAO extends DataBaseHelper implements EggGroupDataInterface {

    public EggGroupDAO(Context context) {
        super(context);
    }

    /**
     *
     * @return      An alphabetically sorted list of all egg groups
     * @see         EggGroup
     */
    public List<EggGroup> getAllEggGroups() {
        SQLiteDatabase db = this.getWritableDatabase();

        List<EggGroup> eggGroups = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(EGG_GROUP_PROSE, EGG_GROUP_ID)
                        ,field(EGG_GROUP_PROSE, NAME))
                .from(EGG_GROUP_PROSE)
                .where(field(EGG_GROUP_PROSE, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .orderBy(field(EGG_GROUP_PROSE, NAME))
                .asc()
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                //Move move = new Move();
                EggGroup eggGroup = new EggGroup();
                eggGroup.setId(Integer.parseInt(cursor.getString(0)));
                //add move to list
                eggGroups.add(eggGroup);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return eggGroups;
    }

    /**
     * Returns an Ability object with its name
     *
     * @param   eggGroup    An EggGroup object to be modified with additional data
     * @return              The modified input is returned
     * @see                 EggGroup
     */
    public EggGroup getEggGroup(EggGroup eggGroup) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = SQLiteQueryBuilder
                .select(field(EGG_GROUP_PROSE, EGG_GROUP_ID)
                        , field(EGG_GROUP_PROSE, NAME))
                .from(EGG_GROUP_PROSE)
                .where(field(EGG_GROUP_PROSE, EGG_GROUP_ID) + "=" + eggGroup.getId())
                .and(field(EGG_GROUP_PROSE, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                eggGroup.setId(Integer.parseInt(cursor.getString(0)));
                eggGroup.setName(cursor.getString(1));
            }
            cursor.close();
        }

        return eggGroup;
    }

    /**
     * Adds egg groups to the input pokemon and returns it
     *
     * @param   pokemon A pokemon object to be modified with additional data
     * @return          The modified input is returned
     * @see             Pokemon
     * @see             EggGroup
     */
    public List<EggGroup> getEggGroups(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<EggGroup> eggGroups = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_EGG_GROUPS, EGG_GROUP_ID))
                .from(POKEMON_EGG_GROUPS)
                .where(field(POKEMON_EGG_GROUPS, SPECIES_ID) + "=" + pokemon.getId())
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                //Move move = new Move();
                EggGroup eggGroup = new EggGroup();
                eggGroup.setId(Integer.parseInt(cursor.getString(0)));
                //add move to list
                eggGroups.add(eggGroup);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return eggGroups;
    }
}
