package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.interfaces.PokemonDataInterface;
import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
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

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_SPECIES, ID)
                        , field(POKEMON_SPECIES_NAMES, NAME))
                .from(POKEMON_SPECIES)
                .join(POKEMON_SPECIES_NAMES)
                .on(field(POKEMON_SPECIES, ID) + "=" + field(POKEMON_SPECIES_NAMES, POKEMON_SPECIES_ID))
                .where(field(POKEMON_SPECIES_NAMES, NAME) + " LIKE LOWER('%" + nameSearchParam + "%')")
                .and(field(POKEMON_SPECIES_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);

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

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_SPECIES, ID)
                        , field(POKEMON_SPECIES_NAMES, NAME)
                        , field(POKEMON, HEIGHT)
                        , field(POKEMON, WEIGHT)
                        , field(POKEMON, BASE_EXPERIENCE)
                        , field(POKEMON, "'" + ORDER + "'")
                        , field(POKEMON, IS_DEFAULT)
                        , field(POKEMON_SPECIES, GENDER_RATE)
                        , field(POKEMON_SPECIES_NAMES, GENUS))
                .from(POKEMON_SPECIES)
                .join(POKEMON_SPECIES_NAMES)
                .on(field(POKEMON_SPECIES, ID) + "=" + field(POKEMON_SPECIES_NAMES, POKEMON_SPECIES_ID))
                .join(POKEMON)
                .on(field(POKEMON_SPECIES, ID) + "=" + field(POKEMON, SPECIES_ID))
                .where(field(POKEMON_SPECIES, ID) + "=" + pokemon.getID())
                .and(field(POKEMON_SPECIES_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                //pokemon.setId(pokemon.getId(0));
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

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_ABILITIES, ABILITY_ID)
                        , field(POKEMON_ABILITIES, SLOT)
                        , field(POKEMON_ABILITIES, IS_HIDDEN))
                .from(POKEMON_ABILITIES)
                .where(field(POKEMON_ABILITIES, POKEMON_ID) + "=" + pokemon.getID())
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

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_EGG_GROUPS, EGG_GROUP_ID))
                .from(POKEMON_EGG_GROUPS)
                .where(field(POKEMON_EGG_GROUPS, SPECIES_ID) + "=" + pokemon.getID())
                .build();

        Cursor cursor = db.rawQuery(sql, null);
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

        String selectQuery = "SELECT " + POKEMON_MOVES + "." + MOVE_ID +
                ", " + POKEMON_MOVES + "." + POKEMON_MOVE_METHOD_ID +
                ", " + POKEMON_MOVES + "." + POKEMON_MOVE_LEVEL +
                ", " + MACHINES + "." + MACHINE_NUMBER +
                " FROM " + POKEMON_MOVES +
                //", " + MACHINES +
                " LEFT OUTER JOIN (SELECT * FROM " + MACHINES + " WHERE " + MACHINES + "." + VERSION_GROUP_ID + " = " + version_group_id + ") AS " + MACHINES +
                " ON " + POKEMON_MOVES + "." + MOVE_ID + " = " + MACHINES + "." + MOVE_ID +

                " WHERE " + POKEMON_MOVES + "." + POKEMON_ID + " = " + pokemon.getID() +
                " AND " + POKEMON_MOVES + "." + VERSION_GROUP_ID + " = " + version_group_id +
                " ORDER BY " + POKEMON_MOVES + "." + POKEMON_MOVE_METHOD_ID + " ASC" +
                ", " + POKEMON_MOVES + "." + POKEMON_MOVE_LEVEL + " ASC" +
                ", " + MACHINES + "." + MACHINE_NUMBER + " ASC"
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

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_TYPES, SLOT)
                        , field(POKEMON_TYPES, TYPE_ID))
                .from(POKEMON_TYPES)
                .where(field(POKEMON_TYPES, POKEMON_ID) + "=" + pokemon.getID())
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Type type = new Type();
                type.setSlot(Integer.parseInt(cursor.getString(0)));
                type.setId(Integer.parseInt(cursor.getString(1)));
                type.setColor(Type.getTypeColor(type.getId()));
                //add type to list
                typesForPokemon.add(type);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return typesForPokemon;
    }
}
