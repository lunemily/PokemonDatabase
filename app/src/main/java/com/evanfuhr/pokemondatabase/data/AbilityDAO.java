package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.interfaces.AbilityDataInterface;
import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class AbilityDAO extends DataBaseHelper implements AbilityDataInterface {

    public AbilityDAO(Context context) {
        super(context);
    }

    /**
     * Returns a list of all abilities
     *
     * @return      An unfiltered list of Ability objects
     * @see         Ability
     */
    public List<Ability> getAllAbilities() {

        SQLiteDatabase db = this.getWritableDatabase();

        List<Ability> abilityList = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(ABILITIES, ID)
                        , field(ABILITY_NAMES, NAME)
                        , field(ABILITIES, IS_MAIN_SERIES))
                .from(ABILITIES)
                .join(ABILITY_NAMES)
                .on(field(ABILITIES, ID) + "=" + field(ABILITY_NAMES, ABILITY_ID))
                .where(field(ABILITY_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .and(field(ABILITIES, IS_MAIN_SERIES) + "=" + 1)
                .orderBy(field(ABILITY_NAMES, NAME))
                .asc()
                .build();

        Cursor cursor = db.rawQuery(sql, null);

        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Ability ability = new Ability();
                ability.setId(Integer.parseInt(cursor.getString(0)));
                ability.setName(cursor.getString(1));
                //add pokemon to list
                abilityList.add(ability);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return abilityList;
    }

    /**
     * Returns an Ability object with its name
     *
     * @param   ability An ability object to be modified with additional data
     * @return          The modified input is returned
     * @see             Ability
     */
    public Ability getAbility(Ability ability) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = SQLiteQueryBuilder
                .select(field(ABILITIES, ID)
                        , field(ABILITY_NAMES, NAME)
                        , field(ABILITY_PROSE, EFFECT))
                .from(ABILITIES)
                .join(ABILITY_NAMES)
                .on(field(ABILITIES, ID) + "=" + field(ABILITY_NAMES, ABILITY_ID))
                .join(ABILITY_PROSE)
                .on(field(ABILITIES, ID) + "=" + field(ABILITY_PROSE, ABILITY_ID))
                .where(field(ABILITIES, ID) + "=" + ability.getId())
                .and(field(ABILITY_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .and(field(ABILITY_PROSE, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                ability.setId(Integer.parseInt(cursor.getString(0)));
                ability.setName(cursor.getString(1));
                ability.setProse(cursor.getString(2));
            }
            cursor.close();
        }

        db.close();
        return ability;
    }

    /**
     * Returns an Ability object with its name
     *
     * @param   identifier  An ability object to be modified with additional data
     * @return              The modified input is returned
     * @see                 Ability
     */
    public Ability getAbility(String identifier) {
        SQLiteDatabase db = this.getWritableDatabase();

        Ability ability = new Ability();

        String sql = SQLiteQueryBuilder
                .select(field(ABILITIES, ID)
                        , field(ABILITY_NAMES, NAME)
                        , field(ABILITY_PROSE, EFFECT))
                .from(ABILITIES)
                .join(ABILITY_NAMES)
                .on(field(ABILITIES, ID) + "=" + field(ABILITY_NAMES, ABILITY_ID))
                .join(ABILITY_PROSE)
                .on(field(ABILITIES, ID) + "=" + field(ABILITY_PROSE, ABILITY_ID))
                .where(field(ABILITIES, IDENTIFIER) + "=\"" + identifier + "\"")
                .and(field(ABILITY_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .and(field(ABILITY_PROSE, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                ability.setId(Integer.parseInt(cursor.getString(0)));
                ability.setName(cursor.getString(1));
                ability.setProse(cursor.getString(2));
            }
            cursor.close();
        }

        db.close();
        return ability;
    }

    /**
     * Adds abilities to the input pokemon and returns it
     *
     * @param   pokemon A pokemon object to be modified with additional data
     * @return          The modified input is returned
     * @see             Pokemon
     * @see             Ability
     */
    public List<Ability> getAbilities(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Ability> abilitiesForPokemon = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_ABILITIES, ABILITY_ID)
                        , field(POKEMON_ABILITIES, SLOT)
                        , field(POKEMON_ABILITIES, IS_HIDDEN))
                .from(POKEMON_ABILITIES)
                .where(field(POKEMON_ABILITIES, POKEMON_ID) + "=" + pokemon.getId())
                .orderBy(field(POKEMON_ABILITIES, SLOT))
                .asc()
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                //Move move = new Move();
                Ability ability = new Ability();
                ability.setId(Integer.parseInt(cursor.getString(0)));
                ability.setSlot(Integer.parseInt(cursor.getString(1)));
                ability.setIsHidden("1".equals(cursor.getString(2)));
                //add move to list
                abilitiesForPokemon.add(ability);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return abilitiesForPokemon;
    }
}
