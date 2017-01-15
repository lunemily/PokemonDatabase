package com.evanfuhr.pokemondatabase;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        DataBaseHelper db = new DataBaseHelper(this);

        _RelativeLayout = (RelativeLayout) findViewById(R.id.pokemon_details_activity);
        _moveListLayout = (LinearLayout) findViewById(R.id.move_list);

        //Get pokemon id passed to this activity
        Intent intent = getIntent();
        _pokemon.setID(intent.getIntExtra(MainActivity.POKEMON_ID, 0));
        _pokemon = db.getSinglePokemonByID(_pokemon);
        onPokemonSelected(_pokemon);
        setTitle(_pokemon.getName());
        generateMovesCards();
    }

    @Override
    public void onPokemonSelected(Pokemon pokemon) {

        setPokemonBackgroundColor(pokemon);

    }

    private void setPokemonBackgroundColor(Pokemon pokemon) {
        DataBaseHelper db = new DataBaseHelper(this);

        //Create base background
        List<Type> types = db.getTypesForPokemon(pokemon);
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void generateMovesCards() {
        DataBaseHelper db = new DataBaseHelper(this);

        List<Move> moves = db.getAllMovesForPokemonByGame(_pokemon);
        _levelMoves = Move.getLevelUpMoves(moves);
        _eggMoves = Move.getEggMoves(moves);
        _tutorMoves = Move.getTutorMoves(moves);
        _machineMoves = Move.getMachineMoves(moves);

        CardView levelUpMovesCard = createMovesCard(_levelMoves, "Level Up Moves");
        CardView machineMovesCard = createMovesCard(_machineMoves, "TM Moves");
        CardView eggMovesCard = createMovesCard(_eggMoves, "Egg Moves");
        CardView tutorMovesCard = createMovesCard(_tutorMoves, "Tutor Moves");

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private CardView createMovesCard(List<Move> moves, String moveMethod) {
        DataBaseHelper db = new DataBaseHelper(this);

        // Set the CardView layoutParams
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 16, 0, 0);

        // Initialize a new CardView
        CardView card = new CardView(this);
        card.setLayoutParams(params);
        card.setRadius(1);
        card.setContentPadding(15, 15, 15, 15);
        card.setCardBackgroundColor(Color.parseColor("#fafafa"));
        card.setMaxCardElevation(15);
        card.setCardElevation(9);

        // Initialize a new TextView to put in CardView
        TextView moveMethodLabel = new TextView(this);
        moveMethodLabel.setLayoutParams(params);
        moveMethodLabel.setText(moveMethod);
        moveMethodLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        moveMethodLabel.setTextColor(Color.BLACK);

        // Initialize a new Table to put in CardView
        TableLayout tableLayout = generateMoveTable(moves, db);
        tableLayout.addView(moveMethodLabel, 0);

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private TableLayout generateMoveTable(List<Move> moves, DataBaseHelper db) {
        final TableLayout tableLayout = new TableLayout(this);
        TableRow.LayoutParams tableParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
        );
        tableParams.setMargins(0, 16, 0, 0);

        for (Move m : moves) {
            Move move = db.getMoveByID(m.getID());
            final TableRow row = new TableRow(this);
            final Button move_button = new Button(this);
            move_button.setText(move.getName());
            move_button.setId(move.getID());
            move.setType(db.getTypeByID(move.getType()));
            //TODO: Set color for move type
            move_button.setBackgroundColor(Color.parseColor(move.getType().getColor()));
            move_button.setLayoutParams(tableParams);

            TextView power = new TextView(this);
            power.setText(Integer.toString(move.getPower()));
            power.setTextColor(Color.BLACK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                power.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            }
            power.setGravity(View.TEXT_ALIGNMENT_CENTER);
            power.setLayoutParams(tableParams);

            row.addView(move_button);
            row.addView(power);

            tableLayout.addView(row);
        }

        return tableLayout;
    }

    private void generateLocationCard() {
        //TODO
    }
}
