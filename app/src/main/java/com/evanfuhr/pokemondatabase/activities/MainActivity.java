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
import com.evanfuhr.pokemondatabase.utils.VersionManager;

import org.jetbrains.annotations.NonNls;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @NonNls
    public static final String POKEMON = "Pokémon Database";
    VersionManager mVersionManager;

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

        mVersionManager = new VersionManager(this);
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
                mVersionManager.onClickMenuSetGame();
                break;
            case R.id.action_search_list:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
