package com.evanfuhr.pokemondatabase.activities;

import android.app.FragmentManager;
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

import com.evanfuhr.pokemondatabase.data.DataBaseHelper;
import com.evanfuhr.pokemondatabase.interfaces.OnPokemonSelectedListener;
import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.PokemonDetailsFragment;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.List;

public class PokemonDisplayActivity extends AppCompatActivity
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
        setContentView(R.layout.activity_pokemon_display);

        DataBaseHelper db = new DataBaseHelper(this);

        _RelativeLayout = (RelativeLayout) findViewById(R.id.pokemon_display_activity);
        _moveListLayout = (LinearLayout) findViewById(R.id.move_list);

        //Get pokemon id passed to this activity
        Intent intent = getIntent();
        _pokemon.setID(intent.getIntExtra(MainActivity.POKEMON_ID, 0));
        _pokemon = db.getSinglePokemonByID(_pokemon);
        onPokemonSelected(_pokemon);
        setTitle(_pokemon.getName());
        //generateMovesCards();
    }

    @Override
    public void onPokemonSelected(Pokemon pokemon) {

        setPokemonBackgroundColor(pokemon);

        FragmentManager fm = getFragmentManager();
        PokemonDetailsFragment pokemonDetailsFragment = (PokemonDetailsFragment) fm.findFragmentById(R.id.pokemonDetailsFragment);
        pokemonDetailsFragment.setPokemonDetails(pokemon);
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

        RelativeLayout pokemonDetailsActivity = (RelativeLayout) findViewById(R.id.pokemon_display_activity);
        pokemonDetailsActivity.setBackground(gd);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void generateMovesCards() {
        DataBaseHelper db = new DataBaseHelper(this);

        List<Move> moves = db.getAllMovesForPokemonByGame(_pokemon);
        _levelMoves = Move.getLevelUpMoves(moves);
        _eggMoves = Move.getEggMoves(moves);
        _tutorMoves = Move.getTutorMoves(moves);
        _machineMoves = Move.getMachineMoves(moves);

        CardView levelUpMovesCard = createMovesCard(_levelMoves, 1);
        CardView machineMovesCard = createMovesCard(_machineMoves, 2);
        CardView eggMovesCard = createMovesCard(_eggMoves, 3);
        CardView tutorMovesCard = createMovesCard(_tutorMoves, 4);

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
    private CardView createMovesCard(List<Move> moves, int moveMethodID) {
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

        String moveMethod;
        switch (moveMethodID) {
            case 1:
                moveMethod = "Level Up Moves";
                break;
            case 2:
                moveMethod = "Machine Moves";
                break;
            case 3:
                moveMethod = "Egg Moves";
                break;
            case 4:
                moveMethod = "Tutor Moves";
                break;
            default:
                moveMethod = "Moves";
                break;
        }

        moveMethodLabel.setText(moveMethod);
        moveMethodLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        moveMethodLabel.setTextColor(Color.BLACK);

        // Initialize a new Table to put in CardView
        TableLayout tableLayout = generateMoveTable(moves, db, moveMethodID);
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
    private TableLayout generateMoveTable(List<Move> moves, DataBaseHelper db, int moveMethodID) {
        final TableLayout tableLayout = new TableLayout(this);
        TableRow.LayoutParams tableParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
        );

        tableParams.setMargins(12, 16, 12, 0);

        final TableRow header = new TableRow(this);
        final TextView move_header = createMoveHeader("com.evanfuhr.pokemondatabase.models.Move", tableParams);
        final TextView power_header = createMoveHeader("Power", tableParams);
        final TextView pp_header = createMoveHeader("PP", tableParams);
        final TextView accuracy_header = createMoveHeader("Accuracy", tableParams);

        String moveMethod;
        switch (moveMethodID) {
            case 1:
                moveMethod = "Level";
                break;
            case 2:
                moveMethod = "TM";
                break;
            case 3:
                moveMethod = "Parent";
                break;
            case 4:
                moveMethod = "BP Cost";
                break;
            default:
                moveMethod = "Error";
                break;
        }

        final TextView method_header = createMoveHeader(moveMethod, tableParams);

        header.addView(move_header);
        header.addView(power_header);
        header.addView(pp_header);
        header.addView(accuracy_header);
        header.addView(method_header);

        tableLayout.addView(header);

        for (Move m : moves) {
            Move move = db.getMoveByID(m);
            final TableRow row = new TableRow(this);
            final Button move_button = new Button(this);
            move_button.setText(move.getName());
            move_button.setId(move.getID());
            move.setType(db.getTypeByID(move.getType()));
            //TODO: Set color for move type
            move_button.setBackgroundColor(Color.parseColor(move.getType().getColor()));
            move_button.setLayoutParams(tableParams);

            TextView power = createMoveField(Integer.toString(move.getPower()), tableParams);
            TextView pp = createMoveField(Integer.toString(move.getPP()), tableParams);
            TextView accuracy = createMoveField(Integer.toString(move.getAccuracy()), tableParams);
            TextView methodDetail;

            switch (moveMethodID) {
                case 1:
                    move = db.getMoveLevelForPokemonByGame(move, _pokemon);
                    methodDetail = createMoveField(Integer.toString(move.getLevel()), tableParams);
                    break;
                default:
                    methodDetail = createMoveField("", tableParams);
            }

            row.addView(move_button);
            row.addView(power);
            row.addView(pp);
            row.addView(accuracy);
            row.addView(methodDetail);

            tableLayout.addView(row);
        }

        return tableLayout;
    }

    private TextView createMoveHeader(String text, TableRow.LayoutParams tableParams) {
        TextView field = new TextView(this);
        field.setText(text);
        field.setTextColor(Color.BLACK);
        field.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        field.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        field.setGravity(View.TEXT_ALIGNMENT_CENTER);
        field.setLayoutParams(tableParams);

        return field;
    }

    private TextView createMoveField(String text, TableRow.LayoutParams tableParams) {
        TextView field = new TextView(this);
        field.setText(text);
        field.setTextColor(Color.BLACK);
        field.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        field.setGravity(View.TEXT_ALIGNMENT_CENTER);
        field.setLayoutParams(tableParams);

        return field;
    }

    private void generateLocationCard() {
        //TODO
    }
}
