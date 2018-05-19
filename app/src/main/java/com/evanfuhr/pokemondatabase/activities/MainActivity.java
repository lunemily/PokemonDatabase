package com.evanfuhr.pokemondatabase.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.adapters.VersionSpinnerAdapter;
import com.evanfuhr.pokemondatabase.data.DataBaseHelper;
import com.evanfuhr.pokemondatabase.data.VersionDAO;
import com.evanfuhr.pokemondatabase.models.Version;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import org.jetbrains.annotations.NonNls;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @NonNls
    public static final String POKEMON = "Pokémon Database";
    public int mVersionId = 0;

    Button _pokemonButton;
    Button _typeButton;
    Button _moveButton;
    Button _abilityButton;
    Button _natureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        //myDbHelper = new DataBaseHelper(this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }

        setTitle(POKEMON);
        setListButtons();
    }

    private void setListButtons() {
        _pokemonButton = this.findViewById(R.id.buttonPokemonList);
        _pokemonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton(view);
            }
        });

        _typeButton = this.findViewById(R.id.buttonTypeList);
        _typeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton(view);
            }
        });

        _moveButton = this.findViewById(R.id.buttonMoveList);
        _moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton(view);
            }
        });

        _abilityButton = this.findViewById(R.id.buttonAbilityList);
        _abilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton(view);
            }
        });

        _natureButton = this.findViewById(R.id.buttonNatureList);
        _natureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton(view);
            }
        });
    }

    private void onClickButton(View view) {
        Intent intent;

        switch(view.getId()) {
            case R.id.buttonPokemonList:
                intent = new Intent(this, PokemonListActivity.class);
                break;
            case R.id.buttonTypeList:
                intent = new Intent(this, TypeListActivity.class);
                break;
            case R.id.buttonMoveList:
                intent = new Intent(this, MoveListActivity.class);
                break;
            case R.id.buttonAbilityList:
                intent = new Intent(this, AbilityListActivity.class);
                break;
            case R.id.buttonNatureList:
                intent = new Intent(this, NatureListActivity.class);
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                break;
        }
        PokemonUtils.showLoadingToast(this);
        startActivity(intent);
    }

    // TODO: Util.java
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_set_game:
                onClickMenuSetGame();
                break;
            case R.id.action_search_list:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void onClickMenuSetGame() {

        // Get list of versions
        VersionDAO versionDAO = new VersionDAO(this);
        List<Version> versionList = versionDAO.getAllVersions();

        // Get view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View setGameVersionView = layoutInflater.inflate(R.layout.dialog_set_game_version, null);

        // Setup spinner
        Spinner versionSpinner = setGameVersionView.findViewById(R.id.spinner_game_version);
        final VersionSpinnerAdapter versionSpinnerAdapter = new VersionSpinnerAdapter(this, R.layout.dialog_set_game_version, versionList);
        versionSpinner.setAdapter(versionSpinnerAdapter);
        // TODO: Set position
        restorePreferences();
        versionSpinner.setSelection(versionSpinnerAdapter.getPositionByVersion(mVersionId));

        versionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Version version = versionSpinnerAdapter.getItem(position);
                mVersionId = version.getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Setup a dialog window
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle(R.string.set_game_version);
        alertDialogBuilder.setView(setGameVersionView);
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Save game version
                        dialog.dismiss();

                        // We need an Editor object to make preference changes.
                        // All objects are from android.context.Context
                        SharedPreferences settings = getSharedPreferences(String.valueOf(R.string.gameVersionID), MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.clear();
                        editor.putInt(String.valueOf(R.string.gameVersionID), mVersionId);

                        // Commit the edits!
                        editor.commit();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        restorePreferences();
                        dialog.dismiss();
                    }
                });


        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void restorePreferences() {
        SharedPreferences settings = getSharedPreferences(String.valueOf(R.string.gameVersionID), MODE_PRIVATE);
        mVersionId = settings.getInt(String.valueOf(R.string.gameVersionID), DataBaseHelper.defaultVersionId);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.putInt(String.valueOf(R.string.gameVersionID), mVersionId);
        editor.commit();
    }
    // TODO: End Util.java
}
