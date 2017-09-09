package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.DataBaseHelper;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonMovesFragment extends Fragment {

    Pokemon _pokemon;

    List<Move> _levelMoves = new ArrayList<>();
    List<Move> _eggMoves = new ArrayList<>();
    List<Move> _tutorMoves = new ArrayList<>();
    List<Move> _machineMoves = new ArrayList<>();

    LinearLayout _levelMovesLayout;
    LinearLayout _tmMovesLayout;
    LinearLayout _eggMovesLayout;
    LinearLayout _tutorMovesLayout;

    public PokemonMovesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View detailsFragmentView = inflater.inflate(R.layout.fragment_pokemon_moves, container, false);

        _levelMovesLayout = (LinearLayout) detailsFragmentView.findViewById(R.id.level_up_moves_layout);
        _tmMovesLayout = (LinearLayout) detailsFragmentView.findViewById(R.id.tm_moves_layout);
        _eggMovesLayout = (LinearLayout) detailsFragmentView.findViewById(R.id.egg_moves_layout);
        _tutorMovesLayout = (LinearLayout) detailsFragmentView.findViewById(R.id.tutor_moves_layout);

        return detailsFragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setPokemonMoves(Pokemon pokemon) {
        _pokemon = pokemon;
        //generateMovesCards();
        fillMovesCards();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void generateMovesCards() {
        PokemonDAO db = new PokemonDAO(getActivity());

        List<Move> moves = db.getMovesForPokemonByGame(_pokemon);
        _levelMoves = Move.getLevelUpMoves(moves);
        _eggMoves = Move.getEggMoves(moves);
        _tutorMoves = Move.getTutorMoves(moves);
        _machineMoves = Move.getMachineMoves(moves);
    }

    public void fillMovesCards() {
        PokemonDAO db = new PokemonDAO(getActivity());

        List<Move> moves = db.getMovesForPokemonByGame(_pokemon);
        _levelMoves = Move.getLevelUpMoves(moves);
        _eggMoves = Move.getEggMoves(moves);
        _tutorMoves = Move.getTutorMoves(moves);
        _machineMoves = Move.getMachineMoves(moves);

        _levelMovesLayout.addView(generateMoveTable(_levelMoves, db, 1));
        _tmMovesLayout.addView(generateMoveTable(_machineMoves, db, 2));
        _eggMovesLayout.addView(generateMoveTable(_eggMoves, db, 3));
        _tutorMovesLayout.addView(generateMoveTable(_tutorMoves, db, 4));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private CardView createMovesCard(List<Move> moves, int moveMethodID) {
        DataBaseHelper db = new DataBaseHelper(getActivity());

        // Set the CardView layoutParams
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 16, 0, 0);

        // Initialize a new CardView
        CardView card = new CardView(getActivity());
        card.setLayoutParams(params);
        card.setRadius(1);
        card.setContentPadding(15, 15, 15, 15);
        card.setCardBackgroundColor(Color.parseColor("#fafafa"));
        card.setMaxCardElevation(15);
        card.setCardElevation(9);

        // Initialize a new TextView to put in CardView
        TextView moveMethodLabel = new TextView(getActivity());
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private TableLayout generateMoveTable(List<Move> moves, DataBaseHelper db, int moveMethodID) {
        TypeDAO typeDAO = new TypeDAO(getActivity());
        final TableLayout tableLayout = new TableLayout(getActivity());
        TableRow.LayoutParams tableParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT
                , TableRow.LayoutParams.WRAP_CONTENT
        );

        tableParams.setMargins(12, 16, 12, 0);

        final TableRow header = new TableRow(getActivity());
        final TextView move_header = createMoveHeader("Name", tableParams);
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
            final TableRow row = new TableRow(getActivity());
            final Button move_button = new Button(getActivity());
            move_button.setText(move.getName());
            move_button.setId(move.getID());
            move.setType(typeDAO.getTypeByID(move.getType()));
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
        TextView field = new TextView(getActivity());
        field.setText(text);
        field.setTextColor(Color.BLACK);
        field.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        field.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        field.setGravity(View.TEXT_ALIGNMENT_CENTER);
        field.setLayoutParams(tableParams);

        return field;
    }

    private TextView createMoveField(String text, TableRow.LayoutParams tableParams) {
        TextView field = new TextView(getActivity());
        field.setText(text);
        field.setTextColor(Color.BLACK);
        field.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        field.setGravity(View.TEXT_ALIGNMENT_CENTER);
        field.setLayoutParams(tableParams);

        return field;
    }
}
