package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.MoveDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.fragments.PokemonListFragment;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import org.jetbrains.annotations.NonNls;

public class MoveDisplayActivity extends AppCompatActivity
        implements PokemonListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String MOVE_ID = "move_id";

    Move mMove = new Move();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_display);

        MoveDAO moveDAO = new MoveDAO(this);

        //Get pokemon id passed to this activity
        Intent intent = getIntent();
        mMove.setId(intent.getIntExtra(MOVE_ID, 0));
        mMove = moveDAO.getMoveByID(mMove);
        setMoveBackgroundColor(mMove);
        setTitle(mMove.getName());

        moveDAO.close();
    }

    private void setMoveBackgroundColor(Move move) {
        TypeDAO typeDAO = new TypeDAO(this);

        RelativeLayout moveDisplayActivity = findViewById(R.id.move_display_activity);

        moveDisplayActivity.setBackgroundColor(Color.parseColor(typeDAO.getTypeByID(move.getType()).getColor()));
    }

    @Override
    public void onListFragmentInteraction(Pokemon pokemon) {
        PokemonUtils.showLoadingToast(this);
        int pokemon_id = pokemon.getID();

        //Build the intent to load the pokemon display
        Intent intent = new Intent(this, PokemonDisplayActivity.class);
        //Load the pokemon ID to send to the player sheet
        intent.putExtra(PokemonDisplayActivity.POKEMON_ID, pokemon_id);

        startActivity(intent);

    }
}
