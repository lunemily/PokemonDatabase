package com.evanfuhr.pokemondatabase.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.display.PokemonDisplayActivity;
import com.evanfuhr.pokemondatabase.adapters.VersionSpinnerAdapter;
import com.evanfuhr.pokemondatabase.data.DataBaseHelper;
import com.evanfuhr.pokemondatabase.data.VersionDAO;
import com.evanfuhr.pokemondatabase.models.Version;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class VersionManager {

    private Context mContext;
    private Version mVersion;

    public VersionManager(Context context) {
        mContext = context;
        mVersion = new Version();
    }

    private void saveVersion(Version version) {
        mVersion = version;
        SharedPreferences settings = mContext.getSharedPreferences(String.valueOf(R.string.gameVersionID), MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.putInt(String.valueOf(R.string.gameVersionID), mVersion.getId());
        editor.commit();
    }

    public Version loadVersion() {
        SharedPreferences settings = mContext.getSharedPreferences(String.valueOf(R.string.gameVersionID), MODE_PRIVATE);
        mVersion.setId(settings.getInt(String.valueOf(R.string.gameVersionID), DataBaseHelper.defaultVersionId));
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.putInt(String.valueOf(R.string.gameVersionID), mVersion.getId());
        editor.commit();

        return mVersion;
    }

    public void onClickMenuSetGame() {

        // Get list of versions
        VersionDAO versionDAO = new VersionDAO(mContext);
        List<Version> versionList = versionDAO.getAllVersions();

        // Get view
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View setGameVersionView = layoutInflater.inflate(R.layout.dialog_set_game_version, null);

        // Setup spinner
        Spinner versionSpinner = setGameVersionView.findViewById(R.id.spinner_game_version);
        final VersionSpinnerAdapter versionSpinnerAdapter = new VersionSpinnerAdapter(mContext, R.layout.dialog_set_game_version, versionList);
        versionSpinner.setAdapter(versionSpinnerAdapter);

        // Set position
        loadVersion();
        versionSpinner.setSelection(versionSpinnerAdapter.getPositionByVersion(mVersion.getId()));

        versionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mVersion = versionSpinnerAdapter.getItem(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Setup a dialog window
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(R.string.set_game_version);
        alertDialogBuilder.setView(setGameVersionView);
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Save game version
                        dialog.dismiss();
                        saveVersion(mVersion);

                        if (mContext instanceof PokemonDisplayActivity) {
                            // Restart activity with new version
                            Intent intent = ((PokemonDisplayActivity) mContext).getIntent();
                            mContext.startActivity(intent);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        loadVersion();
                        dialog.dismiss();
                    }
                });


        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
