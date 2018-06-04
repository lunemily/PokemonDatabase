package com.evanfuhr.pokemondatabase.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.LocationDAO;
import com.evanfuhr.pokemondatabase.data.MoveDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Evolution;
import com.evanfuhr.pokemondatabase.models.Location;
import com.evanfuhr.pokemondatabase.models.Move;
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

    private String getEvolutionProse() {
        String prose = "By";

        switch (mEvolution.getTrigger()) {
            case LEVEL_UP:
                prose += " leveling up";
                break;
            case TRADE:
                prose += " trading";
                break;
            case USE_ITEM:
                prose += " using";
                break;
            case SHED:
                prose += " shedding";
                break;
            default:
                prose += " an unknown method";
                break;
        }

        for (Map.Entry<Evolution.Detail, Integer> entry : mEvolution.getTriggerDetails().entrySet()) {
            switch (entry.getKey()) {
                case MINIMUM_LEVEL:
                    prose += ", starting at Lv. " + entry.getValue();
                    break;
                case TIME_OF_DAY:
                    prose += ", only during the " + Evolution.Detail.getTimeOfDay(entry.getValue());
                    break;
                case LOCATION_ID:
                    LocationDAO locationDAO = new LocationDAO(mContext);
                    prose += ", only at " + locationDAO.getLocation(new Location(entry.getValue())).getName();
                    locationDAO.close();
                    break;
                case KNOWN_MOVE_ID:
                    MoveDAO moveDAO = new MoveDAO(mContext);
                    prose += ", if it knows " + moveDAO.getMove(new Move(entry.getValue())).getName();
                    moveDAO.close();
                    break;
                case KNOWN_MOVE_TYPE:
                    TypeDAO typeDAO = new TypeDAO(mContext);
                    prose += ", if it knows a " + typeDAO.getType(new Type(entry.getValue())).getName() + " type move";
                    typeDAO.close();
                    break;
                default:
                    break;
            }
        }

        return prose;
    }
}
