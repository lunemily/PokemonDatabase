package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.interfaces.PokemonDataInterface;
import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.EggGroup;
import com.evanfuhr.pokemondatabase.models.Location;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.MoveMethod;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Stat;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        SQLiteDatabase db = this.getWritableDatabase();

        List<Pokemon> pokemonList = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_SPECIES, ID)
                        , field(POKEMON_SPECIES_NAMES, NAME))
                .from(POKEMON_SPECIES)
                .join(POKEMON_SPECIES_NAMES)
                .on(field(POKEMON_SPECIES, ID) + "=" + field(POKEMON_SPECIES_NAMES, POKEMON_SPECIES_ID))
                .where(field(POKEMON_SPECIES_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);

        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Pokemon pokemon = new Pokemon();
                pokemon.setId(Integer.parseInt(cursor.getString(0)));
                pokemon.setName(cursor.getString(1));
                //add pokemon to list
                pokemonList.add(pokemon);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return pokemonList;
    }

    /**
     *
     * @param ability   A filter criterion for all Pokemon
     * @return          A list of Pokemon that all have the input ability
     * @see             Ability
     */
    public List<Pokemon>getPokemon(Ability ability) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Pokemon> pokemons = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select( "distinct " + field(POKEMON, SPECIES_ID))
                .from(POKEMON_ABILITIES)
                .join(POKEMON)
                .on(field(POKEMON_ABILITIES, POKEMON_ID) + "=" + field(POKEMON, ID))
                .where(field(POKEMON_ABILITIES, ABILITY_ID) + "=" + ability.getId())
                .orderBy(field(POKEMON, SPECIES_ID))
                .asc()
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Pokemon pokemon = new Pokemon();
                pokemon.setId(Integer.parseInt(cursor.getString(0)));
                pokemons.add(pokemon);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return pokemons;
    }

    /**
     *
     * @param eggGroup  A filter criterion for all Pokemon
     * @return          A list of Pokemon that all have the input egg group
     * @see             EggGroup
     */
    public List<Pokemon>getPokemon(EggGroup eggGroup) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Pokemon> pokemons = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_EGG_GROUPS, SPECIES_ID))
                .from(POKEMON_EGG_GROUPS)
                .where(field(POKEMON_EGG_GROUPS, EGG_GROUP_ID) + "=" + eggGroup.getId())
                .orderBy(field(POKEMON_EGG_GROUPS, SPECIES_ID))
                .asc()
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Pokemon pokemon = new Pokemon();
                pokemon.setId(Integer.parseInt(cursor.getString(0)));
                pokemons.add(pokemon);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return pokemons;
    }

    /**
     *
     * @param location  A filter criterion for all Pokemon
     * @return          A list of Pokemon that can be found at the input location
     * @see             Location
     */
    public List<Pokemon> getPokemon(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Pokemon> pokemons = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select("DISTINCT " + field(ENCOUNTERS, POKEMON_ID))
                .from(ENCOUNTERS)
                .join(LOCATION_AREAS)
                .on(field(LOCATION_AREAS, ID) + "=" + field(ENCOUNTERS, LOCATION_AREA_ID))
                .where(field(LOCATION_AREAS, LOCATION_ID) + "=" + location.getId())
                //.and(field(ENCOUNTERS, VERSION_ID) + "=" + mVersion.getId())
                .orderBy(field(ENCOUNTERS, POKEMON_ID))
                .asc()
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                //Move move = new Move();
                Pokemon pokemon = new Pokemon();
                pokemon.setId(Integer.parseInt(cursor.getString(0)));
                //add move to list
                pokemons.add(pokemon);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return pokemons;
    }

    /**
     * Returns a list of all pokemon that can learn the given move. References to the version_group_id maintained elsewhere
     *
     * @param   move A pokemon object to be modified with additional data
     * @return          The modified input is returned
     * @see             Pokemon
     * @see             Move
     */
    public List<Pokemon> getPokemon(Move move) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Pokemon> pokemons = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select("DISTINCT " + field(POKEMON, SPECIES_ID))
                .from(POKEMON_MOVES)
                .join(POKEMON)
                .on(field(POKEMON_MOVES, POKEMON_ID) + "=" + field(POKEMON, ID))
                .where(field(POKEMON_MOVES, MOVE_ID) + "=" + move.getId())
                .and(field(POKEMON_MOVES, VERSION_GROUP_ID) + "=" + getVersionGroupIDByVersionID())
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                //Move move = new Move();
                Pokemon pokemon = new Pokemon();
                pokemon.setId(Integer.parseInt(cursor.getString(0)));
                //add move to list
                pokemons.add(pokemon);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return pokemons;
    }

    /**
     *
     * @param type      A filter criterion for all Pokemon
     * @return          A list of Pokemon that all have the input type
     * @see             Type
     */
    public List<Pokemon>getPokemon(Type type) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Pokemon> pokemons = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select("distinct " + field(POKEMON, SPECIES_ID)
                        , field(POKEMON, ID))
                .from(POKEMON_TYPES)
                .join(POKEMON)
                .on(field(POKEMON_TYPES, POKEMON_ID) + "=" + field(POKEMON, ID))
                .where(field(POKEMON_TYPES, TYPE_ID) + "=" + type.getId())
                .orderBy(field(POKEMON, SPECIES_ID))
                .asc()
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Pokemon pokemon = new Pokemon();
                pokemon.setId(Integer.parseInt(cursor.getString(1)));
                pokemons.add(pokemon);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return pokemons;
    }

    /**
     * Returns a Pokemon object with most of its non-list data
     *
     * @param   pokemon A Pokemon object to be modified with additional data
     * @return          The modified input is returned
     * @see             Pokemon
     */
    public Pokemon getPokemon(Pokemon pokemon) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_SPECIES, ID)
                        , field(POKEMON_SPECIES_NAMES, NAME))
                .from(POKEMON_SPECIES)
                .join(POKEMON_SPECIES_NAMES)
                .on(field(POKEMON_SPECIES, ID) + "=" + field(POKEMON_SPECIES_NAMES, POKEMON_SPECIES_ID))
                .join(POKEMON)
                .on(field(POKEMON_SPECIES, ID) + "=" + field(POKEMON, SPECIES_ID))
                .where(field(POKEMON, ID) + "=" + pokemon.getId())
                .and(field(POKEMON_SPECIES_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                //pokemon.setId(pokemon.getId(0));
                pokemon.setName(cursor.getString(1));
            }
            cursor.close();
        }
        db.close();
        return pokemon;
    }

    /**
     * Returns a Pokemon object with most of its non-list data
     *
     * @param   pokemon A Pokemon object to be modified with additional data
     * @return          The modified input is returned
     * @see             Pokemon
     */
    public Pokemon loadPokemonDetails(Pokemon pokemon) {
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
                .where(field(POKEMON_SPECIES, ID) + "=" + pokemon.getId())
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

        // Get base stats
        HashMap<Stat.PrimaryStat, Integer> map = new HashMap<>();
        String baseStatSql = SQLiteQueryBuilder
                .select(field(POKEMON_STATS, POKEMON_ID)
                        , field(POKEMON_STATS, STAT_ID)
                        , field(POKEMON_STATS, BASE_STAT))
                .from(POKEMON_STATS)
                .where(field(POKEMON_STATS, POKEMON_ID) + "=" + pokemon.getId())
                .build();
        Cursor baseStatCursor = db.rawQuery(baseStatSql, null);
        if (baseStatCursor.moveToFirst()) {
            do {
                map.put(Stat.PrimaryStat.get(Integer.parseInt(baseStatCursor.getString(1))), Integer.valueOf(baseStatCursor.getString(2)));
            } while (baseStatCursor.moveToNext());
        }
        pokemon.setBaseStats(map);

        baseStatCursor.close();
        db.close();
        return pokemon;
    }

    /**
     * Returns a Pokemon object with most of its non-list data
     *
     * @param   name A Pokemon object to be modified with additional data
     * @return          The modified input is returned
     * @see             Pokemon
     */
    public Pokemon getPokemon(String name) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(name);
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
                .where(field(POKEMON_SPECIES_NAMES, NAME) + "='" + pokemon.getName() + "'")
                .and(field(POKEMON_SPECIES_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                pokemon.setId(Integer.parseInt(cursor.getString(0)));
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
}
