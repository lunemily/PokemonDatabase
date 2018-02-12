package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.interfaces.PokemonDataInterface;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.EggGroup;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.MoveMethod;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.List;

public class PokemonDAO extends DataBaseHelper implements PokemonDataInterface {

    public PokemonDAO(Context context) {
        super(context);
    }

    /**
     * Returns a list of all pokemon
     *
     * @return      An unfiltered list of Pokemon objects
     * @see         Pokemon
     */
    public List<Pokemon> getAllPokemon() {
        return getAllPokemon("%");
    }

    /**
     * Returns a list of all pokemon that contain nameSearchParam
     *
     * @param   nameSearchParam A substring to filter Pokemon names with
     * @return                  A filtered list of Pokemon objects
     * @see                     Pokemon
     */
    public List<Pokemon> getAllPokemon(String nameSearchParam) {

        SQLiteDatabase db = this.getWritableDatabase();

        List<Pokemon> pokemonList = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_POKEMON_SPECIES + "." + KEY_ID +
                ", " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_NAME +
                " FROM " + TABLE_POKEMON_SPECIES +
                ", " + TABLE_POKEMON_SPECIES_NAMES +
                " WHERE " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_POKEMON_SPECIES_ID +
                " AND LOWER(" + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_NAME + ") LIKE LOWER('%" + nameSearchParam + "%')" +
                " AND " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_LOCAL_LANGUAGE_ID + " = '" + _language_id + "'"
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);

        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Pokemon pokemon = new Pokemon();
                pokemon.setID(Integer.parseInt(cursor.getString(0)));
                pokemon.setName(cursor.getString(1));
                //add pokemon to list
                pokemonList.add(pokemon);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return pokemonList;
    }

    /**
     * Returns a Pokemon object with most of its non-list data
     *
     * @param   pokemon A Pokemon object to be modified with additional data
     * @return          The modified input is returned
     * @see             Pokemon
     */
    public Pokemon getPokemonByID(Pokemon pokemon) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT " + TABLE_POKEMON_SPECIES + "." + KEY_ID +
                ", " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_NAME +
                ", " + TABLE_POKEMON + "." + KEY_HEIGHT +
                ", " + TABLE_POKEMON + "." + KEY_WEIGHT +
                ", " + TABLE_POKEMON + "." + KEY_BASE_EXPERIENCE +
                ", " + TABLE_POKEMON + ".'" + KEY_ORDER + "'" +
                ", " + TABLE_POKEMON + "." + KEY_IS_DEFAULT +
                ", " + TABLE_POKEMON_SPECIES + "." + KEY_GENDER_RATE +
                ", " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_GENUS +
                " FROM " + TABLE_POKEMON_SPECIES +
                ", " + TABLE_POKEMON_SPECIES_NAMES +
                ", " + TABLE_POKEMON +
                " WHERE " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = '" + pokemon.getID() + "'" +
                " AND " + TABLE_POKEMON + "." + KEY_SPECIES_ID + " = " + TABLE_POKEMON_SPECIES + "." + KEY_ID +
                " AND " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_POKEMON_SPECIES_ID +
                " AND " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_LOCAL_LANGUAGE_ID + " = '" + _language_id + "'"
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                //pokemon.setID(pokemon.getID(0));
                pokemon.setName(cursor.getString(1));
                pokemon.setHeight(Double.parseDouble(cursor.getString(2))/10);
                pokemon.setWeight(Double.parseDouble(cursor.getString(3))/10);
                pokemon.setBaseExperience(Integer.parseInt(cursor.getString(4)));
                //pokemon.setOrder(Integer.parseInt(cursor.getString(5)));
                //pokemon.setIsDefault(Boolean.parseBoolean(cursor.getString(6)));
                //pokemon.setGenderRatio(Integer.parseInt(cursor.getString(7)));
                pokemon.setGenus(cursor.getString(8));
            }
            cursor.close();
        }

        return pokemon;
    }

    /**
     * Adds abilities to the input pokemon and returns it
     *
     * @param   pokemon A pokemon object to be modified with additional data
     * @return          The modified input is returned
     * @see             Pokemon
     * @see             Ability
     */
    public List<Ability> getAbilitiesForPokemon(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Ability> abilitiesForPokemon = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_ABILITIES + "." + KEY_ID +
                ", " + TABLE_POKEMON_ABILITIES + "." + KEY_SLOT +
                ", " + TABLE_POKEMON_ABILITIES + "." + KEY_IS_HIDDEN +
            " FROM " + TABLE_ABILITIES +
                ", " + TABLE_POKEMON_ABILITIES +
                ", " + TABLE_POKEMON_SPECIES +
            " WHERE " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = '" + pokemon.getID() + "'" +
                " AND " + TABLE_ABILITIES + "." + KEY_ID + " = " + TABLE_POKEMON_ABILITIES + "." + KEY_ABILITY_ID +
                " AND " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = " + TABLE_POKEMON_ABILITIES + "." + KEY_POKEMON_ID +
            " ORDER BY " + TABLE_POKEMON_ABILITIES + "." + KEY_SLOT + " ASC"
            ;


        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                //Move move = new Move();
                Ability ability = new Ability();
                ability.setID(Integer.parseInt(cursor.getString(0)));
                ability.setSlot(Integer.parseInt(cursor.getString(1)));
                ability.setIsHidden("1".equals(cursor.getString(2)));
                //add move to list
                abilitiesForPokemon.add(ability);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return abilitiesForPokemon;
    }

    /**
     * Adds egg groups to the input pokemon and returns it
     *
     * @param   pokemon A pokemon object to be modified with additional data
     * @return          The modified input is returned
     * @see             Pokemon
     * @see             EggGroup
     */
    public List<EggGroup> getEggGroupsForPokemon(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<EggGroup> eggGroups = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_POKEMON_EGG_GROUPS + "." + KEY_EGG_GROUP_ID +
            " FROM " + TABLE_POKEMON_EGG_GROUPS +
            " WHERE " + TABLE_POKEMON_EGG_GROUPS + "." + KEY_SPECIES_ID + " = " + pokemon.getID()
            ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                //Move move = new Move();
                EggGroup eggGroup = new EggGroup();
                eggGroup.setID(Integer.parseInt(cursor.getString(0)));
                //add move to list
                eggGroups.add(eggGroup);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return eggGroups;
    }

    /**
     * Adds moves to the input pokemon and returns it. The moves and relevant data for the given
     * pokemon are determined by a deeper reference to the version_group_id maintained elsewhere
     *
     * @param   pokemon A pokemon object to be modified with additional data
     * @return          The modified input is returned
     * @see             Pokemon
     * @see             Move
     */
    public List<Move> getMovesForPokemonByGame(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Move> movesForPokemon = new ArrayList<>();
        int version_group_id = getVersionGroupIDByVersionID();

        String selectQuery = "SELECT " + TABLE_POKEMON_MOVES + "." + KEY_MOVE_ID +
                ", " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_MOVE_METHOD_ID +
                ", " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_MOVE_LEVEL +
                ", " + TABLE_MACHINES + "." + KEY_MACHINE_NUMBER +
                " FROM " + TABLE_POKEMON_MOVES +
                //", " + TABLE_MACHINES +
                " LEFT OUTER JOIN (SELECT * FROM " + TABLE_MACHINES + " WHERE " + TABLE_MACHINES + "." + KEY_VERSION_GROUP_ID + " = " + version_group_id + ") AS " + TABLE_MACHINES +
                " ON " + TABLE_POKEMON_MOVES + "." + KEY_MOVE_ID + " = " + TABLE_MACHINES + "." + KEY_MOVE_ID +

                " WHERE " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_ID + " = " + pokemon.getID() +
                " AND " + TABLE_POKEMON_MOVES + "." + KEY_VERSION_GROUP_ID + " = " + version_group_id +
                " ORDER BY " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_MOVE_METHOD_ID + " ASC" +
                ", " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_MOVE_LEVEL + " ASC" +
                ", " + TABLE_MACHINES + "." + KEY_MACHINE_NUMBER + " ASC"
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Move move = new Move();
                move.setID(Integer.parseInt(cursor.getString(0)));
                // Set method enum
                move.setMethodID(MoveMethod.get(Integer.parseInt(cursor.getString(1))));
                move.setLevel(Integer.parseInt(cursor.getString(2)));
                if (!cursor.isNull(3)) {
                    move.setTM(Integer.parseInt(cursor.getString(3)));
                }
                //add move to list
                movesForPokemon.add(move);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return movesForPokemon;
    }

    /**
     * Adds types to the input pokemon and returns it
     *
     * @param   pokemon A pokemon object to be modified with additional data
     * @return          The modified input is returned
     * @see             Pokemon
     * @see             Type
     */
    public List<Type> getTypesForPokemon(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Type> typesForPokemon = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_POKEMON_TYPES + "." + KEY_SLOT +
                ", " + TABLE_POKEMON_TYPES + "." + KEY_TYPE_ID +
                " FROM " + TABLE_POKEMON_TYPES +
                ", " + TABLE_TYPES +
                " WHERE " + TABLE_POKEMON_TYPES + "." + KEY_TYPE_ID + " = " + TABLE_TYPES + "." + KEY_ID +
                " AND " + TABLE_POKEMON_TYPES + "." + KEY_POKEMON_ID + " = " + pokemon.getID();

        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Type type = new Type();
                type.setSlot(Integer.parseInt(cursor.getString(0)));
                type.setID(Integer.parseInt(cursor.getString(1)));
                type.setColor(Type.getTypeColor(type.getID()));
                //add type to list
                typesForPokemon.add(type);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return typesForPokemon;
    }
}
