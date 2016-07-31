package com.evanfuhr.pokemondatabase;

import android.app.Activity;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        generatePokemonList();
    }

    void generatePokemonList() {
        final LinearLayout pokemon_list = (LinearLayout) findViewById(R.id.pokemonlist);

        DataBaseHelper db = new DataBaseHelper(this);

        List<Pokemon> pokemons = db.getAllPokemon();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 16, 0, 0);

        int counter = 1;

        for (Pokemon pokemon : pokemons) {

            //Create hero button
            final Button pokemon_button = new Button(this);
            List<Type> types = db.getTypesForPokemon(pokemon.getID());


            //create a new gradient color
            int[] colors = {0, 0};
            if (types.size() == 1) {
                colors[0] = Color.parseColor(types.get(0).getColor());
                colors[1] = Color.parseColor(types.get(0).getColor());
            }
            else {
                colors[0] = Color.parseColor(types.get(0).getColor());
                colors[1] = Color.parseColor(types.get(1).getColor());
            }
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT, colors);
            gd.setCornerRadius(0f);

            pokemon_button.setLayoutParams(params);
            pokemon_button.setText(pokemon.getName());
            pokemon_button.setId(pokemon.getID());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                pokemon_button.setBackground(gd);
            }


            //Set click listener for the button
            /*pokemon_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickButtonPlayerSheet(view);
                }
            });*/

            //Add the pokemon button to the table row
            pokemon_list.addView(pokemon_button);

            registerForContextMenu(pokemon_button);


            if (counter > 150) {
                break;
            }
            counter++;
        }
    }
}
