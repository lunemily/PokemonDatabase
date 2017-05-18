package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.EggGroup;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.List;

public class PokemonDAO extends DataBaseHelper {

    public PokemonDAO(Context context) {
        super(context);
    }

    //Get Pokemon
    public List<Pokemon> getAllPokemon() {
        return getAllPokemon("%");
    }

    public List<Pokemon> getAllPokemon(String nameSearchParam) {

        SQLiteDatabase db = this.getWritableDatabase();

        List<Pokemon> pokemonList = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_POKEMON_SPECIES + "." + KEY_ID +
                ", " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_NAME +
                " FROM " + TABLE_POKEMON_SPECIES +
                ", " + TABLE_POKEMON_SPECIES_NAMES +
                " WHERE " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_POKEMON_SPECIES_ID +
                " AND LOWER(" + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_NAME + ") LIKE '%" + nameSearchParam + "%'" +
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

    public Pokemon getSinglePokemonByID(Pokemon pokemon) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT " + TABLE_POKEMON_SPECIES + "." + KEY_ID +
                ", " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_NAME +
                ", " + TABLE_POKEMON + "." + KEY_HEIGHT +
                ", " + TABLE_POKEMON + "." + KEY_WEIGHT +
                ", " + TABLE_POKEMON + "." + KEY_BASE_EXPERIENCE +
                ", " + TABLE_POKEMON + ".'" + KEY_ORDER + "'" +
                ", " + TABLE_POKEMON + "." + KEY_IS_DEFAULT +
                ", " + TABLE_POKEMON_SPECIES + "." + KEY_GENDER_RATE +
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
            }
            cursor.close();
        }

        return pokemon;
    }

    //Get Pokemon attributes
    public List<Ability> getAbilitiesForPokemon(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Ability> abilitiesForPokemon = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_ABILITIES + "." + KEY_ID +
                ", " + TABLE_ABILITY_NAMES + "." + KEY_NAME +
                ", " + TABLE_POKEMON_ABILITIES + "." + KEY_SLOT +
                ", " + TABLE_POKEMON_ABILITIES + "." + KEY_IS_HIDDEN +
            " FROM " + TABLE_ABILITIES +
                ", " + TABLE_ABILITY_NAMES +
                ", " + TABLE_POKEMON_ABILITIES +
                ", " + TABLE_POKEMON_SPECIES +
            " WHERE " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = '" + pokemon.getID() + "'" +
                " AND " + TABLE_ABILITIES + "." + KEY_ID + " = " + TABLE_POKEMON_ABILITIES + "." + KEY_ABILITY_ID +
                " AND " + TABLE_ABILITIES + "." + KEY_ID + " = " + TABLE_ABILITY_NAMES + "." + KEY_ABILITY_ID +
                " AND " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = " + TABLE_POKEMON_ABILITIES + "." + KEY_POKEMON_ID +
                " AND " + TABLE_ABILITY_NAMES + "." + KEY_LOCAL_LANGUAGE_ID + " = '" + _language_id + "'" +
            " ORDER BY " + TABLE_POKEMON_ABILITIES + "." + KEY_SLOT + " ASC"
            ;


        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                //Move move = new Move();
                Ability ability = new Ability();
                ability.setID(Integer.parseInt(cursor.getString(0)));
                ability.setName(cursor.getString(1));
                ability.setSlot(Integer.parseInt(cursor.getString(2)));
                ability.setIsHidden("1".equals(cursor.getString(3)));
                //add move to list
                abilitiesForPokemon.add(ability);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return abilitiesForPokemon;
    }

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

    public List<Move> getMovesForPokemonByGame(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Move> movesForPokemon = new ArrayList<>();
        int version_group_id = getVersionGroupIDByVersionID(_version_id);

        String selectQuery = "SELECT " + TABLE_POKEMON_MOVES + "." + KEY_MOVE_ID +
                ", " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_MOVE_METHOD_ID +
                ", " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_MOVE_LEVEL +
                " FROM " + TABLE_POKEMON_MOVES +
                " WHERE " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_ID + " = '" + pokemon.getID() + "'" +
                " AND " + TABLE_POKEMON_MOVES + "." + KEY_VERSION_GROUP_ID + " = '" + version_group_id + "'" +
                " ORDER BY " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_MOVE_LEVEL + " ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Move move = new Move();
                move.setID(Integer.parseInt(cursor.getString(0)));
                move.setMethodID(Integer.parseInt(cursor.getString(1)));
                move.setLevel(Integer.parseInt(cursor.getString(2)));
                //add move to list
                movesForPokemon.add(move);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return movesForPokemon;
    }

    public List<Type> getTypesForPokemon(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Type> typesForPokemon = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_POKEMON_TYPES + "." + KEY_SLOT +
                ", " + TABLE_POKEMON_TYPES + "." + KEY_TYPE_ID +
                ", " + TABLE_TYPES + "." + KEY_COLOR +
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
                type.setColor(cursor.getString(2));
                //add type to list
                typesForPokemon.add(type);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return typesForPokemon;
    }
}
