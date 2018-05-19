package com.evanfuhr.pokemondatabase.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.adapters.VersionSpinnerAdapter;
import com.evanfuhr.pokemondatabase.data.DataBaseHelper;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.data.VersionDAO;
import com.evanfuhr.pokemondatabase.fragments.AbilityListFragment;
import com.evanfuhr.pokemondatabase.fragments.MoveListFragment;
import com.evanfuhr.pokemondatabase.fragments.TypeListFragment;
import com.evanfuhr.pokemondatabase.fragments.TypeMatchUpFragment;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;
import com.evanfuhr.pokemondatabase.models.Version;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;
import java.util.List;

public class PokemonDisplayActivity extends AppCompatActivity
        implements AbilityListFragment.OnListFragmentInteractionListener,
        TypeListFragment.OnListFragmentInteractionListener, MoveListFragment.OnListFragmentInteractionListener,
        TypeMatchUpFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String POKEMON_ID = "pokemon_id";
    public static final String ANIMATION = "animation";
    public int mVersionId = 0;

    RelativeLayout mRelativeLayout;

    public Pokemon pokemon = new Pokemon();

    private GestureDetector mDetector;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_display);

        PokemonDAO pokemonDAO = new PokemonDAO(this);

        mRelativeLayout = findViewById(R.id.pokemon_display_activity);

        //Get pokemon id passed to this activity
        Intent intent = getIntent();
        pokemon.setId(intent.getIntExtra(POKEMON_ID, 0));
        pokemon = pokemonDAO.getPokemonByID(pokemon);
        setPokemonBackgroundColor(pokemon);
        setTitle("#" + pokemon.getId() + " " + pokemon.getName());

        mDetector = new GestureDetector(this, new MyGestureListener(pokemon.getId(), this));

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

        RelativeLayout pokemonDetailsActivity = findViewById(R.id.pokemon_display_activity);
        pokemonDetailsActivity.setBackground(gd);

        pokemonDAO.close();
        typeDAO.close();
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
        LayoutInflater layoutInflater = LayoutInflater.from(PokemonDisplayActivity.this);
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PokemonDisplayActivity.this);
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
                        editor.commit();

                        // Restart activity with new version
                        PokemonUtils.showLoadingToast(PokemonDisplayActivity.this);
                        Intent intent = new Intent(PokemonDisplayActivity.this, PokemonDisplayActivity.class);
                        intent.putExtra(PokemonDisplayActivity.POKEMON_ID, pokemon.getId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
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

        // Commit the edits!
        editor.commit();
    }

    @Override
    public void onListFragmentInteraction(Ability ability) {
        PokemonUtils.showLoadingToast(this);
        // Build the intent to load the display
        Intent intent = new Intent(this, AbilityDisplayActivity.class);
        // Add the id to send to the display activity
        intent.putExtra(AbilityDisplayActivity.ABILITY_ID, ability.getId());

        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Type type) {
        PokemonUtils.showLoadingToast(this);
        // Build the intent to load the display
        Intent intent = new Intent(this, TypeDisplayActivity.class);
        // Add the id to send to the display activity
        intent.putExtra(TypeDisplayActivity.TYPE_ID, type.getId());

        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Move move) {
        PokemonUtils.showLoadingToast(this);
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
        Context context;

        public MyGestureListener(int currentPokemonID, Context context) {
            this.context = context;
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

            if (Math.abs(deltaY) > 2 * Math.abs(deltaX)) { // If vertical fling, just scroll
                return false;
            } else { // Go to adjacent pokemon
                PokemonUtils.showLoadingToast(this.context);
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
