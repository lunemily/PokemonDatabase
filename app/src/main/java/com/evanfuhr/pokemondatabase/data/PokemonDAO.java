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
                pokemon.setId(Integer.parseInt(cursor.getString(0)));
                pokemon.setName(cursor.getString(1));
                //add pokemon to list
                pokemonList.add(pokemon);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return pokemonList;
    }

    @Override
    public List<Pokemon> getPokemon(Location location) {
        return null;
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

        return pokemon;
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
}
