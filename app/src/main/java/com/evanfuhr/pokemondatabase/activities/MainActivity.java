package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.DataBaseHelper;
import com.evanfuhr.pokemondatabase.fragments.PokemonListFragment;
import com.evanfuhr.pokemondatabase.fragments.TypeListFragment;

import org.jetbrains.annotations.NonNls;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements PokemonListFragment.OnPokemonListFragmentInteractionListener,
        TypeListFragment.OnTypeListFragmentInteractionListener {

    @NonNls
    public static final String POKEMON = "Pokémon";
    @NonNls
    public static final String MENU_ITEM_NOT_IMPLEMENTED_YET = "Menu item not implemented yet";

    Button _pokemonButton;
    Button _typeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        //myDbHelper = new DataBaseHelper(this);

        try {

            Toast toast = Toast.makeText(getApplicationContext(), "Creating database", Toast.LENGTH_LONG);
            toast.show();

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            Toast toast = Toast.makeText(getApplicationContext(), "Failed to create database", Toast.LENGTH_LONG);
            toast.show();

            throw new Error("Unable to create database");

        }

        try {

            Toast toast = Toast.makeText(getApplicationContext(), "Opening database", Toast.LENGTH_LONG);
            toast.show();

            myDbHelper.openDataBase();

        }catch(SQLException sqle){

            Toast toast = Toast.makeText(getApplicationContext(), "Failed to open database", Toast.LENGTH_LONG);
            toast.show();

            throw sqle;

        }
        setTitle(POKEMON);
        setListButtons();
    }

    private void setListButtons() {
        _pokemonButton = (Button) this.findViewById(R.id.buttonPokemonList);
        _pokemonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton(view);
            }
        });

        _typeButton = (Button) this.findViewById(R.id.buttonTypeList);
        _typeButton.setOnClickListener(new View.OnClickListener() {
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
                onClickMenuSetGame(item);
                break;
            case R.id.action_search_pokemon_list:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    void onClickMenuSetGame(MenuItem item) {
        Toast toast = Toast.makeText(getApplicationContext(), MENU_ITEM_NOT_IMPLEMENTED_YET, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onPokemonListFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTypeListFragmentInteraction(Uri uri) {

    }
}
