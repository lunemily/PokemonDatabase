package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.interfaces.EvolutionDataInterface;
import com.evanfuhr.pokemondatabase.models.Evolution;
import com.evanfuhr.pokemondatabase.models.EvolutionChain;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class EvolutionDAO extends DataBaseHelper implements EvolutionDataInterface {

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public EvolutionDAO(Context context) {
        super(context);
    }

    public List<Evolution> getAllEvolutions(Pokemon pokemon) {
        return getAllEvolutions(getEvolutionChain(pokemon));
    }

    public List<Evolution> getAllEvolutions(EvolutionChain evolutionChain) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Evolution> evolutions = new ArrayList<>();

        String sql = SQLiteQueryBuilder.select(field(POKEMON_SPECIES, EVOLVES_FROM_SPECIES_ID)
                , field(POKEMON_SPECIES, ID))
                .from(POKEMON_SPECIES)
                .where(field(POKEMON_SPECIES, EVOLUTION_CHAIN_ID) + "=(" +
                        evolutionChain.getId()
                        + ")")
                .and(field(POKEMON_SPECIES, EVOLVES_FROM_SPECIES_ID) + " IS NOT NULL")
                .build();


        Cursor cursor = db.rawQuery(sql, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Evolution evolution = new Evolution();
                Pokemon preEvolution = new Pokemon();
                Pokemon postEvolution = new Pokemon();

                preEvolution.setId(Integer.parseInt(cursor.getString(0)));
                postEvolution.setId(Integer.parseInt(cursor.getString(1)));

                evolution.setBeforePokemon(preEvolution);
                evolution.setAfterPokemon(postEvolution);

                //add to list
                evolutions.add(evolution);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return evolutions;
    }

    @Override
    public EvolutionChain getEvolutionChain(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        EvolutionChain evolutionChain = new EvolutionChain();

        String sql = SQLiteQueryBuilder.select(field(POKEMON_SPECIES, EVOLUTION_CHAIN_ID))
                .from(POKEMON_SPECIES)
                .where(field(POKEMON_SPECIES, ID) + "=" + pokemon.getId())
                .build();


        Cursor cursor = db.rawQuery(sql, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                evolutionChain.setId(Integer.parseInt(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return evolutionChain;
    }
}
