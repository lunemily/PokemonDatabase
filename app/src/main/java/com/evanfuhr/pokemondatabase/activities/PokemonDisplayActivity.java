package com.evanfuhr.pokemondatabase.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.fragments.PokemonDetailsFragment;
import com.evanfuhr.pokemondatabase.fragments.PokemonMovesFragment;
import com.evanfuhr.pokemondatabase.fragments.TypeMatchUpFragment;
import com.evanfuhr.pokemondatabase.interfaces.OnPokemonSelectedListener;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import org.jetbrains.annotations.NonNls;

import java.util.List;

public class PokemonDisplayActivity extends AppCompatActivity
        implements OnPokemonSelectedListener {

    @NonNls
    public static final String POKEMON_ID = "pokemon_id";

    RelativeLayout _RelativeLayout;

    public Pokemon _pokemon = new Pokemon();

    private GestureDetector mDetector;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_display);

        PokemonDAO pokemonDAO = new PokemonDAO(this);

        _RelativeLayout = (RelativeLayout) findViewById(R.id.pokemon_display_activity);

        //Get pokemon id passed to this activity
        Intent intent = getIntent();
        _pokemon.setID(intent.getIntExtra(POKEMON_ID, 0));
        _pokemon = pokemonDAO.getSinglePokemonByID(_pokemon);
        onPokemonSelected(_pokemon);
        setTitle(_pokemon.getName());

        mDetector = new GestureDetector(this, new MyGestureListener(_pokemon.getID()));

        pokemonDAO.close();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean handled = mDetector.onTouchEvent(ev);
        if (!handled) {
            return super.dispatchTouchEvent(ev);
        }

        return handled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onPokemonSelected(Pokemon pokemon) {

        setPokemonBackgroundColor(pokemon);

        FragmentManager fm = getFragmentManager();

        PokemonDetailsFragment pokemonDetailsFragment = (PokemonDetailsFragment) fm.findFragmentById(R.id.pokemonDetailsFragment);
        pokemonDetailsFragment.setPokemonDetails(pokemon);

        TypeMatchUpFragment typeMatchUpFragment = (TypeMatchUpFragment) fm.findFragmentById(R.id.typeMatchUpFragment);
        typeMatchUpFragment.setTypeMatchUps(pokemon.getTypes(), true);

        PokemonMovesFragment pokemonMovesFragment = (PokemonMovesFragment) fm.findFragmentById(R.id.pokemonMoveFragment);
        pokemonMovesFragment.setPokemonMoves(pokemon);
    }

    private void setPokemonBackgroundColor(Pokemon pokemon) {
        PokemonDAO pokemonDAO = new PokemonDAO(this);
        TypeDAO typeDAO = new TypeDAO(this);

        //Create base background
        List<Type> types = pokemonDAO.getTypesForPokemon(pokemon);
        int[] colors = {0, 0, 0, 0};
        if (types.size() == 1) {
            colors[0] = Color.parseColor(typeDAO.getTypeByID(types.get(0)).getColor());
            colors[1] = Color.parseColor(typeDAO.getTypeByID(types.get(0)).getColor());
            colors[2] = Color.parseColor(typeDAO.getTypeByID(types.get(0)).getColor());
            colors[3] = Color.parseColor(typeDAO.getTypeByID(types.get(0)).getColor());
        }
        else {
            colors[0] = Color.parseColor(typeDAO.getTypeByID(types.get(0)).getColor());
            colors[1] = Color.parseColor(typeDAO.getTypeByID(types.get(0)).getColor());
            colors[2] = Color.parseColor(typeDAO.getTypeByID(types.get(1)).getColor());
            colors[3] = Color.parseColor(typeDAO.getTypeByID(types.get(1)).getColor());
        }
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, colors);

        RelativeLayout pokemonDetailsActivity = (RelativeLayout) findViewById(R.id.pokemon_display_activity);
        pokemonDetailsActivity.setBackground(gd);

        pokemonDAO.close();
        typeDAO.close();
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";
        private int mCurrentPokemonID = 0;
        private float deltaX, deltaY;

        public MyGestureListener(int currentPokemonID) {
            this.mCurrentPokemonID = currentPokemonID;
        }

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return false;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            deltaX = event2.getX() - event1.getX();
            deltaY = event2.getY() - event1.getY();

            Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());

            if (Math.abs(deltaY) > Math.abs(deltaX)) { // If vertical fling, just scroll
                return false;
            } else { // Go to adjacent pokemon
                int newPokemonID = (deltaX < 0) ? mCurrentPokemonID + 1 : (mCurrentPokemonID - 1 > 0) ? mCurrentPokemonID - 1 : 1 ;
                Intent intent = new Intent(getApplicationContext(), PokemonDisplayActivity.class);
                intent.putExtra(POKEMON_ID, newPokemonID);

                startActivity(intent);
                return false;
            }
        }
    }
}
