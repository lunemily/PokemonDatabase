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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_SPECIES, EVOLVES_FROM_SPECIES_ID)
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

    public EvolutionChain getEvolutionChain(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        EvolutionChain evolutionChain = new EvolutionChain();

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_SPECIES, EVOLUTION_CHAIN_ID))
                .from(POKEMON_SPECIES)
                .join(POKEMON)
                .on(field(POKEMON, SPECIES_ID) + "=" + field(POKEMON_SPECIES, ID))
                .where("(" + field(POKEMON_SPECIES, ID) + "=" + pokemon.getId()
                        + " OR " + field(POKEMON, ID) + "=" + pokemon.getId() + ")")
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

    public Evolution getEvolution(Evolution evolution) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_EVOLUTION, EVOLUTION_TRIGGER_ID)
                        ,field(POKEMON_EVOLUTION, TRIGGER_ITEM_ID)
                        ,field(POKEMON_EVOLUTION, MINIMUM_LEVEL)
                        ,field(POKEMON_EVOLUTION, LOCATION_ID)
                        ,field(POKEMON_EVOLUTION, HELD_ITEM_ID)
                        ,field(POKEMON_EVOLUTION, TIME_OF_DAY)
                        ,field(POKEMON_EVOLUTION, KNOWN_MOVE_ID)
                        ,field(POKEMON_EVOLUTION, KNOWN_MOVE_TYPE_ID)
                        ,field(POKEMON_EVOLUTION, MINIMUM_HAPPINESS)
                        ,field(POKEMON_EVOLUTION, MINIMUM_BEAUTY)
                        ,field(POKEMON_EVOLUTION, MINIMUM_AFFECTION)
                        ,field(POKEMON_EVOLUTION, RELATIVE_PHYSICAL_STATS)
                        ,field(POKEMON_EVOLUTION, PARTY_SPECIES_ID)
                        ,field(POKEMON_EVOLUTION, PARTY_TYPE_ID)
                        ,field(POKEMON_EVOLUTION, TRADE_SPECIES_ID)
                        ,field(POKEMON_EVOLUTION, NEEDS_OVERWORLD_RAIN)
                        ,field(POKEMON_EVOLUTION, TURN_UPSIDE_DOWN)
                        ,field(POKEMON_EVOLUTION, GENDER_ID))
                .from(POKEMON_EVOLUTION)
                .where(field(POKEMON_EVOLUTION, EVOLVED_SPECIES_ID) + "=" +
                        evolution.getAfterPokemon().getId())
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                evolution.setTrigger(Evolution.Trigger.get(Integer.parseInt(cursor.getString(0))));
                Map<Evolution.Detail, Integer> details = new HashMap<>();
                if (!cursor.isNull(1)) {
                    details.put(Evolution.Detail.TRIGGER_ITEM_ID, Integer.valueOf(cursor.getString(1)));
                }
                if (!cursor.isNull(2)) {
                    details.put(Evolution.Detail.MINIMUM_LEVEL, Integer.valueOf(cursor.getString(2)));
                }
                if (!cursor.isNull(3)) {
                    details.put(Evolution.Detail.LOCATION_ID, Integer.valueOf(cursor.getString(3)));
                }
                if (!cursor.isNull(4)) {
                    details.put(Evolution.Detail.HELD_ITEM_ID, Integer.valueOf(cursor.getString(4)));
                }
                if (!cursor.isNull(5)) {
                    details.put(Evolution.Detail.TIME_OF_DAY, Evolution.Detail.getTimeOfDay(cursor.getString(5)));
                }
                if (!cursor.isNull(6)) {
                    details.put(Evolution.Detail.KNOWN_MOVE_ID, Integer.valueOf(cursor.getString(6)));
                }
                if (!cursor.isNull(7)) {
                    details.put(Evolution.Detail.KNOWN_MOVE_TYPE, Integer.valueOf(cursor.getString(7)));
                }
                if (!cursor.isNull(8)) {
                    details.put(Evolution.Detail.MINIMUM_HAPPINESS, Integer.valueOf(cursor.getString(8)));
                }
                if (!cursor.isNull(9)) {
                    details.put(Evolution.Detail.MINIMUM_BEAUTY, Integer.valueOf(cursor.getString(9)));
                }
                if (!cursor.isNull(10)) {
                    details.put(Evolution.Detail.MINIMUM_AFFECTION, Integer.valueOf(cursor.getString(10)));
                }
                if (!cursor.isNull(11)) {
                    details.put(Evolution.Detail.RELATIVE_PHYSICAL_STATS, Integer.valueOf(cursor.getString(11)));
                }
                if (!cursor.isNull(12)) {
                    details.put(Evolution.Detail.PARTY_SPECIES_ID, Integer.valueOf(cursor.getString(12)));
                }
                if (!cursor.isNull(13)) {
                    details.put(Evolution.Detail.PARTY_TYPE_ID, Integer.valueOf(cursor.getString(13)));
                }
                if (!cursor.isNull(14)) {
                    details.put(Evolution.Detail.TRADE_SPECIES_ID, Integer.valueOf(cursor.getString(14)));
                }
                if (!cursor.getString(15).equals("0")) {
                    details.put(Evolution.Detail.NEEDS_OVERWORLD_RAIN, Integer.valueOf(cursor.getString(15)));
                }
                if (!cursor.getString(16).equals("0")) {
                    details.put(Evolution.Detail.TURN_UPSIDE_DOWN, Integer.valueOf(cursor.getString(16)));
                }
                if (!cursor.isNull(17)) {
                    details.put(Evolution.Detail.GENDER_ID, Integer.valueOf(cursor.getString(17)));
                }
                evolution.setTriggerDetails(details);
            }
            cursor.close();
        }

        return evolution;
    }
}
