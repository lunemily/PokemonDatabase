package com.evanfuhr.pokemondatabase.activities;

import android.app.FragmentManager;
import android.content.Intent;
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
import com.evanfuhr.pokemondatabase.fragments.AbilityListFragment;
import com.evanfuhr.pokemondatabase.fragments.MoveListFragment;
import com.evanfuhr.pokemondatabase.fragments.PokemonDetailsFragment;
import com.evanfuhr.pokemondatabase.fragments.TypeListFragment;
import com.evanfuhr.pokemondatabase.interfaces.OnPokemonSelectedListener;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;
import java.util.List;

public class PokemonDisplayActivity extends AppCompatActivity
        implements OnPokemonSelectedListener, AbilityListFragment.OnListFragmentInteractionListener,
        TypeListFragment.OnListFragmentInteractionListener, MoveListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String POKEMON_ID = "pokemon_id";
    public static final String ANIMATION = "animation";

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
        _pokemon.setId(intent.getIntExtra(POKEMON_ID, 0));
        _pokemon = pokemonDAO.getPokemonByID(_pokemon);
        onPokemonSelected(_pokemon);
        setTitle("#" + _pokemon.getID() + " " + _pokemon.getName());

        mDetector = new GestureDetector(this, new MyGestureListener(_pokemon.getID()));

        pokemonDAO.close();

        if (intent.getExtras().containsKey(ANIMATION)) {
            switch (intent.getIntExtra(ANIMATION, 0)) {
                case R.anim.trans_left_in:
                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
                    break;
                case R.anim.trans_right_in:
                    overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
                    break;
                default:
                    overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
                    break;
            }
        }
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
    }

    private void setPokemonBackgroundColor(Pokemon pokemon) {
        PokemonDAO pokemonDAO = new PokemonDAO(this);
        TypeDAO typeDAO = new TypeDAO(this);

        //Create base background
        List<Type> rawTypes = pokemonDAO.getTypesForPokemon(pokemon);
        List<Type> types = new ArrayList<>();
        for (Type t : rawTypes) {
            types.add(typeDAO.getTypeByID(t));
        }
        GradientDrawable gd = PokemonUtils.getColorGradientByTypes(types);

        RelativeLayout pokemonDetailsActivity = (RelativeLayout) findViewById(R.id.pokemon_display_activity);
        pokemonDetailsActivity.setBackground(gd);

        pokemonDAO.close();
        typeDAO.close();
    }

    @Override
    public void onListFragmentInteraction(Ability ability) {
        // Build the intent to load the display
        Intent intent = new Intent(this, AbilityDisplayActivity.class);
        // Add the id to send to the display activity
        intent.putExtra(AbilityDisplayActivity.ABILITY_ID, ability.getId());

        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Type type) {
        // Build the intent to load the display
        Intent intent = new Intent(this, TypeDisplayActivity.class);
        // Add the id to send to the display activity
        intent.putExtra(TypeDisplayActivity.TYPE_ID, type.getId());

        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Move move) {
        // Build the intent to load the display
        Intent intent = new Intent(this, MoveDisplayActivity.class);
        // Add the id to send to the display activity
        intent.putExtra(MoveDisplayActivity.MOVE_ID, move.getId());

        startActivity(intent);
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
                Intent intent = new Intent(getApplicationContext(), PokemonDisplayActivity.class);
                boolean swipeRight = (deltaX < 0);
                int newPokemonID;
                int animation;

                if (swipeRight) {
                    //TODO: Don't hardcode this
                    newPokemonID = (mCurrentPokemonID + 1 <= 802) ? mCurrentPokemonID + 1 : 1; // Wrap around
                    animation = R.anim.trans_left_in;
                } else { //swipeLeft
                    //TODO: Don't hardcode this
                    newPokemonID = (mCurrentPokemonID - 1 > 0) ? mCurrentPokemonID - 1 : 802; // Wrap around
                    animation = R.anim.trans_right_in;
                }

                intent.putExtra(POKEMON_ID, newPokemonID);
                intent.putExtra(ANIMATION, animation);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                return false;
            }
        }
    }
}
