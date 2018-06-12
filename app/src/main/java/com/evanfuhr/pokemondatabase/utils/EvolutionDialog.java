package com.evanfuhr.pokemondatabase.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.ItemDAO;
import com.evanfuhr.pokemondatabase.data.LocationDAO;
import com.evanfuhr.pokemondatabase.data.MoveDAO;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Evolution;
import com.evanfuhr.pokemondatabase.models.Item;
import com.evanfuhr.pokemondatabase.models.Location;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.Map;

public class EvolutionDialog {

    private Context mContext;
    private Evolution mEvolution;

    public EvolutionDialog(Context context) {
        this.mContext = context;
    }

    public void show(Evolution evolution) {

        // Get the pretty evolution detail
        mEvolution = evolution;

        // Get view
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View evolutionDialogView = layoutInflater.inflate(R.layout.dialog_evolution_details, null);
        TextView evolutionDetails = evolutionDialogView.findViewById(R.id.evolution_prose);
        evolutionDetails.setText(getEvolutionProse());

        // Setup a dialog window
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(mEvolution.getBeforePokemon().getName() + " evolves into " +
                mEvolution.getAfterPokemon().getName());
        alertDialogBuilder.setView(evolutionDialogView);
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });


        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    /**
     * The evolution object identifies the base trigger for how a Pokemon evolves. It also contains a variable set
     * of flags and data "modifying" how that Pokemon evolves.
     *
     * @return      A string of how a single Pokemon evolves into another Pokemon.
     * @see         Evolution
     */
    private String getEvolutionProse() {
        // The underlying data is id based and so we lazy-load the human readable names
        PokemonDAO pokemonDAO = new PokemonDAO(mContext);
        TypeDAO typeDAO = new TypeDAO(mContext);
        ItemDAO itemDAO = new ItemDAO(mContext);
        MoveDAO moveDAO = new MoveDAO(mContext);
        LocationDAO locationDAO = new LocationDAO(mContext);

        // Get the single trigger and "stringify it"
        String prose = "By";
        switch (mEvolution.getTrigger()) {
            case LEVEL_UP:
                prose += " leveling up";
                break;
            case TRADE:
                prose += " trading";
                break;
            case USE_ITEM:
                prose += " exposing it to the " + itemDAO.getItem(
                        new Item(mEvolution.getTriggerDetails().get(Evolution.Detail.TRIGGER_ITEM_ID))).getName();
                break;
            case SHED:
                prose += " shedding";
                break;
            default:
                prose += " an unknown method";
                break;
        }

        // Loop through each entry in the evolution object's trigger details, and append the string form to the 
        // prose in no particular order
        for (Map.Entry<Evolution.Detail, Integer> entry : mEvolution.getTriggerDetails().entrySet()) {
            switch (entry.getKey()) {
                case MINIMUM_LEVEL:
                    prose += ", starting at Lv. " + entry.getValue();
                    break;
                case TIME_OF_DAY:
                    prose += ", only during the " + Evolution.Detail.getTimeOfDay(entry.getValue());
                    break;
                case GENDER_ID:
                    prose += ", if a " + Evolution.Detail.getGender(entry.getValue());
                case MINIMUM_HAPPINESS:
                    // If this field is present, it means high happiness, e.g. 220
                    prose += ", with high friendship";
                    break;
                case MINIMUM_AFFECTION:
                    prose += ", with at least " + entry.getValue() + " affection hearts";
                    break;
                case LOCATION_ID:
                    prose += ", only at " + locationDAO.getLocation(new Location(entry.getValue())).getName();
                    break;
                case KNOWN_MOVE_ID:
                    prose += ", if it knows " + moveDAO.getMove(new Move(entry.getValue())).getName();
                    break;
                case KNOWN_MOVE_TYPE:
                    prose += ", if it knows a " + typeDAO.getType(new Type(entry.getValue())).getName() + " type move";
                    break;
                case HELD_ITEM_ID:
                    prose += ", while holding the " + itemDAO.getItem(new Item(entry.getValue())).getName();
                    break;
                case TRIGGER_ITEM_ID:
                    // This is handled above. It makes the most sense to preserve readability for the end user
                    break;
                // Note: the rest of these really are just one-offs but still necessary
                case PARTY_TYPE_ID:
                    prose += ", if there is a " + typeDAO.getType(new Type(entry.getValue())).getName() +
                            " type Pokémon also in the party";
                    break;
                case PARTY_SPECIES_ID:
                    prose += ", if there is a " + pokemonDAO.getPokemon(new Pokemon(entry.getValue())).getName() +
                            " also in the party";
                    break;
                case TRADE_SPECIES_ID:
                    prose += ", if traded for a " + pokemonDAO.getPokemon(new Pokemon(entry.getValue())).getName();
                    break;
                case NEEDS_OVERWORLD_RAIN:
                    prose += ", if it's raining outside of battle";
                    break;
                case TURN_UPSIDE_DOWN:
                    prose += ", while holding the device upside down";
                    break;
                case MINIMUM_BEAUTY:
                    prose += ", with a maxed out beauty stat";
                    break;
                case RELATIVE_PHYSICAL_STATS:
                    // This trigger detail represents the relationship between a Pokemon's Attack and Defense stats
                    switch (entry.getValue()) {
                        case 1:
                            // Attack > Defense
                            prose += ", if its Attack is higher than its Defense";
                            break;
                        case -1:
                            // Attack < Defense
                            prose += ", if its Defense is higher than its Attack";
                            break;
                        case 0:
                            //Attack = Defense
                            prose += ", if its Attack and Defense are equal";
                            break;
                        default:
                            break;
                    }
                default:
                    break;
            }
        }

        pokemonDAO.close();
        itemDAO.close();
        typeDAO.close();
        moveDAO.close();
        locationDAO.close();

        return prose;
    }
}
