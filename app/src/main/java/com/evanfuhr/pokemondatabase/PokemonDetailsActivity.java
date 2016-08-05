package com.evanfuhr.pokemondatabase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PokemonDetailsActivity extends AppCompatActivity
        implements OnPokemonSelectedListener {

    RelativeLayout _RelativeLayout;
    LinearLayout _moveListLayout;

    Pokemon _pokemon = new Pokemon();
    List<Move> _levelMoves = new ArrayList<>();
    List<Move> _eggMoves = new ArrayList<>();
    List<Move> _tutorMoves = new ArrayList<>();
    List<Move> _machineMoves = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        _RelativeLayout = (RelativeLayout) findViewById(R.id.pokemon_details_activity);
        _moveListLayout = (LinearLayout) findViewById(R.id.move_list);

        //Get hero id passed to this activity
        Intent intent = getIntent();
        _pokemon.setID(intent.getIntExtra(MainActivity.POKEMON_ID, 0));
        onPokemonSelected(_pokemon);
        generateMovesCards();
    }

    @Override
    public void onPokemonSelected(Pokemon pokemon) {

        setPokemonBackgroundColor(pokemon);

    }

    //private void setPokemonBackgroundColor(int pokemon_id) {
    private void setPokemonBackgroundColor(Pokemon pokemon) {
        DataBaseHelper db = new DataBaseHelper(this);

        //Create base background
        List<Type> types = db.getTypesForPokemon(pokemon.getID());
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

        RelativeLayout pokemonDetailsActivity = (RelativeLayout) findViewById(R.id.pokemon_details_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            pokemonDetailsActivity.setBackground(gd);
        }
    }

    public void generateMovesCards() {
        DataBaseHelper db = new DataBaseHelper(this);

        List<Move> moves = db.getAllMovesForPokemonByGame(_pokemon.getID());
        _levelMoves = Move.getLevelUpMoves(moves);
        _eggMoves = Move.getEggMoves(moves);
        _tutorMoves = Move.getTutorMoves(moves);
        _machineMoves = Move.getMachineMoves(moves);

        CardView levelUpMovesCard = createMovesCard(_levelMoves);
        CardView machineMovesCard = createMovesCard(_machineMoves);
        CardView eggMovesCard = createMovesCard(_eggMoves);
        CardView tutorMovesCard = createMovesCard(_tutorMoves);

        // Finally, add the CardView in root layout
        _moveListLayout.addView(createLargeSpacer());
        _moveListLayout.addView(levelUpMovesCard);
        _moveListLayout.addView(createSmallSpacer());
        _moveListLayout.addView(machineMovesCard);
        _moveListLayout.addView(createSmallSpacer());
        _moveListLayout.addView(eggMovesCard);
        _moveListLayout.addView(createSmallSpacer());
        _moveListLayout.addView(tutorMovesCard);
        _moveListLayout.addView(createLargeSpacer());
    }

    private CardView createMovesCard(List<Move> moves) {
        DataBaseHelper db = new DataBaseHelper(this);

        // Set the CardView layoutParams
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
        );

        // Initialize a new CardView
        CardView card = new CardView(this);
        card.setLayoutParams(params);
        card.setRadius(1);
        card.setContentPadding(15, 15, 15, 15);
        card.setCardBackgroundColor(Color.parseColor("#fafafa"));
        card.setMaxCardElevation(15);
        card.setCardElevation(9);

        // Initialize a new TextView to put in CardView
        TextView tv = new TextView(this);
        tv.setLayoutParams(params);
        //TODO: Change to dynamically decide what title to use
        tv.setText("Moves");
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        tv.setTextColor(Color.BLACK);

        // Initialize a new Table to put in CardView
        final TableLayout tableLayout = new TableLayout(this);
        tableLayout.addView(tv);
        for (Move m : moves) {
            Move move = db.getMoveByID(m.getID());
            final TableRow row = new TableRow(this);
            final Button move_button = new Button(this);
            move_button.setText(move.getName());
            move_button.setId(move.getID());
            //TODO: Set color for move type
            row.addView(move_button);
            tableLayout.addView(row);
        }

        // Put the TextView in CardView
        card.addView(tableLayout);

        return card;
    }

    private View createLargeSpacer() {
        View view = new View(this);
        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 48));
        return view;
    }

    private View createSmallSpacer() {
        View view = new View(this);
        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 16));
        return view;
    }
}
