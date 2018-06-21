package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.DataBaseHelper;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;
import com.evanfuhr.pokemondatabase.utils.VersionManager;

import org.jetbrains.annotations.NonNls;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @NonNls
    public static final String POKEMON = "Pokémon Database";

    Button mPokemonButton;
    Button mTypeButton;
    Button mMoveButton;
    Button mAbilityButton;
    Button mLocationButton;
    Button mEggGroupButton;
    Button mNatureButton;

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

        } catch(SQLException sqle) {

            throw sqle;

        }

        setTitle(POKEMON);
        setListButtons();
    }

    private void setListButtons() {
        mPokemonButton = this.findViewById(R.id.buttonPokemonList);
        mPokemonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton(view);
            }
        });

        mTypeButton = this.findViewById(R.id.buttonTypeList);
        mTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton(view);
            }
        });

        mMoveButton = this.findViewById(R.id.buttonMoveList);
        mMoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton(view);
            }
        });

        mAbilityButton = this.findViewById(R.id.buttonAbilityList);
        mAbilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton(view);
            }
        });

        mLocationButton = this.findViewById(R.id.buttonLocationList);
        mLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton(view);
            }
        });

        mEggGroupButton = this.findViewById(R.id.buttonEggGroupList);
        mEggGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton(view);
            }
        });

        mNatureButton = this.findViewById(R.id.buttonNatureList);
        mNatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton(view);
            }
        });
    }

    protected void onClickButton(View view) {
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
            case R.id.buttonLocationList:
                intent = new Intent(this, LocationListActivity.class);
                break;
            case R.id.buttonEggGroupList:
                intent = new Intent(this, EggGroupListActivity.class);
                break;
            case R.id.buttonNatureList:
                intent = new Intent(this, NatureListActivity.class);
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                break;
        }
        startActivity(intent);
    }

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
                new VersionManager(this).onClickMenuSetGame();
                break;
            case R.id.action_search_list:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
