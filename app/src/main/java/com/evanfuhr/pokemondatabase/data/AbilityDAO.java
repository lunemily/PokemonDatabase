package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.interfaces.AbilityDataInterface;
import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.models.Ability;
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
        return getAllAbilities("%");
    }

    /**
     * Returns a list of all abilities that contain nameSearchParam
     *
     * @param   nameSearchParam A substring to filter Ability names with
     * @return                  A filtered list of Ability objects
     * @see                     Ability
     */
    public List<Ability> getAllAbilities(String nameSearchParam) {

        SQLiteDatabase db = this.getWritableDatabase();

        List<Ability> abilityList = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(ABILITIES, ID)
                        , field(ABILITY_NAMES, NAME))
                .from(ABILITIES)
                .join(ABILITY_NAMES)
                .on(field(ABILITIES, ID) + "=" + field(ABILITY_NAMES, ABILITY_ID))
                .where(field(ABILITY_NAMES, NAME) + " LIKE LOWER('%" + nameSearchParam + "%')")
                .and(field(ABILITY_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
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

        return abilityList;
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

        return ability;
    }

    public List<Pokemon> getPokemonByAbility(Ability ability) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Pokemon> pokemonList = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_ABILITIES, POKEMON_ID)
                        , field(POKEMON_SPECIES_NAMES, NAME)
                        , field(POKEMON_ABILITIES, ABILITY_ID)
                        , field(POKEMON_ABILITIES, IS_HIDDEN))
                .from(POKEMON_ABILITIES)
                .join(POKEMON_SPECIES_NAMES)
                .on(field(POKEMON_ABILITIES, POKEMON_ID) + "=" + field(POKEMON_SPECIES_NAMES, POKEMON_SPECIES_ID))
                .where(field(POKEMON_ABILITIES, ABILITY_ID) + "=" + ability.getId())
                .and(field(POKEMON_SPECIES_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);

        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Pokemon pokemon = new Pokemon();
                pokemon.setID(Integer.parseInt(cursor.getString(0)));
                pokemon.setName(cursor.getString(1));

                List<Ability> pokemonAbilities = new ArrayList<>();
                Ability pokemonAbility = new Ability();
                pokemonAbility.setId(Integer.parseInt(cursor.getString(2)));
                pokemonAbility.setIsHidden("1".equals(cursor.getString(3)));

                pokemonAbilities.add(pokemonAbility);
                pokemon.setAbilities(pokemonAbilities);
                pokemonList.add(pokemon);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return pokemonList;
    }

}
