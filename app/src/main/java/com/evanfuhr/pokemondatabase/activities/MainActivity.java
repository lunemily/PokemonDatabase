package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.evanfuhr.pokemondatabase.data.DataBaseHelper;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.models.Type;

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
        setTitle("Pokémon");
        generatePokemonList();
    }

    private void onClickButtonPokemonDetails(View view) {
        //Get the ID associated to the clicked button
        int hero_id = view.getId();

        //Build the intent to load the player sheet
        Intent intent = new Intent(this, PokemonDisplayActivity.class);
        //Load the hero ID to send to the player sheet
        intent.putExtra(POKEMON_ID, hero_id);

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
        boolean handled = true;
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_set_game:
                onClickMenuSetGame(item);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return handled;
    }

    void onClickMenuSetGame(MenuItem item) {
        Toast toast = Toast.makeText(getApplicationContext(), "Set Game implemented yet", Toast.LENGTH_LONG);
        toast.show();
//        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putInt(getString(R.string.saved_high_score), newHighScore);
//        editor.commit();
    }

    void generatePokemonList() {
        final LinearLayout pokemon_list = (LinearLayout) findViewById(R.id.pokemonlist);

        PokemonDAO db = new PokemonDAO(this);

        List<Pokemon> pokemons = db.getAllPokemon();

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 16, 0, 0);

        int counter = 1;

        for (Pokemon pokemon : pokemons) {

            //Create hero button
            final Button pokemon_button = new Button(this);
            List<Type> types = db.getTypesForPokemon(pokemon);


            //create a new gradient color
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
            pokemon_button.setBackground(gd);

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

//            if (counter >= 250) {
//                break;
//            }
            counter++;
        }
    }
}
