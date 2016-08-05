package com.evanfuhr.pokemondatabase;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String POKEMON_ID = "pokemon_id";

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

    private void onClickButtonPokemonDetails(View view) {
        //Get the ID associated to the clicked button
        int hero_id = view.getId();

        //Build the intent to load the player sheet
        Intent intent = new Intent(this, PokemonDetailsActivity.class);
        //Load the hero ID to send to the player sheet
        intent.putExtra(POKEMON_ID, hero_id);

        startActivity(intent);
    }

    void generatePokemonList() {
        final LinearLayout pokemon_list = (LinearLayout) findViewById(R.id.pokemonlist);

        DataBaseHelper db = new DataBaseHelper(this);

        List<Pokemon> pokemons = db.getAllPokemon();

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 16, 0, 0);

        int counter = 1;

        for (Pokemon pokemon : pokemons) {

            //Create hero button
            final Button pokemon_button = new Button(this);
            List<Type> types = db.getTypesForPokemon(pokemon.getID());


            //create a new gradient color
            //int[] colors = {0, 0};
            //4 integers for a sharper color change
            int[] colors = {0, 0, 0, 0};
            if (types.size() == 1) {
                colors[0] = Color.parseColor(types.get(0).getColor());
                colors[1] = Color.parseColor(types.get(0).getColor());
                colors[2] = Color.parseColor(types.get(0).getColor());
                colors[3] = Color.parseColor(types.get(0).getColor());
            }
            else {
                colors[0] = Color.parseColor(types.get(0).getColor());
                colors[1] = Color.parseColor(types.get(0).getColor());
                colors[2] = Color.parseColor(types.get(1).getColor());
                colors[3] = Color.parseColor(types.get(1).getColor());
            }
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT, colors);

            pokemon_button.setLayoutParams(params);
            pokemon_button.setText(pokemon.getName());
            pokemon_button.setId(pokemon.getID());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                pokemon_button.setBackground(gd);
            }

            //Set click listener for the button
            pokemon_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickButtonPokemonDetails(view);
                }
            });

            //Add the pokemon button to the table row
            pokemon_list.addView(pokemon_button);

            registerForContextMenu(pokemon_button);


            if (counter >= 250) {
                break;
            }
            counter++;
        }
    }
}
