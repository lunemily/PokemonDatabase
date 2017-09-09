package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import com.evanfuhr.pokemondatabase.data.MoveDAO;
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
    LinearLayout _machineMovesLayout;
    LinearLayout _eggMovesLayout;
    LinearLayout _tutorMovesLayout;

    TableRow.LayoutParams _buttonParams;
    TableRow.LayoutParams _fieldParams;

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
        _machineMovesLayout = (LinearLayout) detailsFragmentView.findViewById(R.id.tm_moves_layout);
        _eggMovesLayout = (LinearLayout) detailsFragmentView.findViewById(R.id.egg_moves_layout);
        _tutorMovesLayout = (LinearLayout) detailsFragmentView.findViewById(R.id.tutor_moves_layout);

        _buttonParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        _buttonParams.span = 4;
        _buttonParams.column = 1;
        _buttonParams.setMargins(16, 4, 16, 4);

        _fieldParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        _fieldParams.span = 1;
        _fieldParams.setMargins(16, 4, 16, 4);

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
        fillMovesCards();
    }

    public void fillMovesCards() {
        PokemonDAO db = new PokemonDAO(getActivity());

        List<Move> moves = db.getMovesForPokemonByGame(_pokemon);
        _levelMoves = Move.getLevelUpMoves(moves);
        _eggMoves = Move.getEggMoves(moves);
        _tutorMoves = Move.getTutorMoves(moves);
        _machineMoves = Move.getMachineMoves(moves);

        _levelMovesLayout.addView(generateMoveTable(_levelMoves, 1));
        _machineMovesLayout.addView(generateMoveTable(_machineMoves, 4));
        _eggMovesLayout.addView(generateMoveTable(_eggMoves, 2));
        _tutorMovesLayout.addView(generateMoveTable(_tutorMoves, 3));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private TableLayout generateMoveTable(List<Move> moves, int moveMethodID) {
        MoveDAO moveDAO = new MoveDAO(getActivity());
        final TableLayout tableLayout = new TableLayout(getActivity());
        tableLayout.setColumnStretchable(1, true);

        String moveMethod;
        switch (moveMethodID) {
            case 1:
                moveMethod = "Lvl";
                break;
            case 2:
                moveMethod = "TM";
                break;
            case 3:
                moveMethod = "M/F";
                break;
            case 4:
                moveMethod = "BP";
                break;
            default:
                moveMethod = "Error";
                break;
        }

        final TableRow headerRow = new TableRow(getActivity());
        final TextView method_header = createMoveField(moveMethod, _fieldParams, true);
        final TextView move_header = createMoveField("Name", _buttonParams, true);
        final TextView power_header = createMoveField("Pwr", _fieldParams, true);
        final TextView pp_header = createMoveField("PP", _fieldParams, true);
        final TextView accuracy_header = createMoveField("Acc", _fieldParams, true);


        headerRow.addView(method_header);
        headerRow.addView(move_header);
        headerRow.addView(power_header);
        headerRow.addView(pp_header);
        headerRow.addView(accuracy_header);

        tableLayout.addView(headerRow);

        for (Move m : moves) {
            Move move = moveDAO.getMoveByID(m);
            tableLayout.addView(generateMoveTableRow(move));
        }

        moveDAO.close();
        return tableLayout;
    }

    private TableRow generateMoveTableRow(Move move) {
        TypeDAO typeDAO = new TypeDAO(getActivity());

        final TableRow row = new TableRow(getActivity());

        // Method Detail
        final TextView methodDetail;
        switch (move.getMethodID()) {
            case 1:
                methodDetail = createMoveField(Integer.toString(move.getLevel()), _fieldParams, false);
                break;
            case 4:
                //move = moveDAO.getTMForMove(move);
                methodDetail = createMoveField(Integer.toString(move.getTM()), _fieldParams, false);
                break;
            default:
                methodDetail = createMoveField("", _fieldParams, false);
        }
        row.addView(methodDetail);

        // Move Button
        final Button move_button = new Button(getActivity());move_button.setText(move.getName());
        move_button.setId(move.getID());
        move.setType(typeDAO.getTypeByID(move.getType()));
        move_button.setBackgroundColor(Color.parseColor(move.getType().getColor()));
        move_button.setLayoutParams(_buttonParams);
        move_button.setPadding(16, 0, 16, 0);
        row.addView(move_button);

        // Power
        TextView power = createMoveField(Integer.toString(move.getPower()), _fieldParams, false);
        row.addView(power);

        // PP
        TextView pp = createMoveField(Integer.toString(move.getPP()), _fieldParams, false);
        row.addView(pp);

        // Accuracy
        TextView accuracy = createMoveField(Integer.toString(move.getAccuracy()), _fieldParams, false);
        row.addView(accuracy);

        typeDAO.close();
        return row;
    }

    private TextView createMoveField(String text, TableRow.LayoutParams params, Boolean header) {
        TextView field = new TextView(getActivity());
        field.setText(text);
        field.setTextColor(Color.BLACK);
        if (header) {
            field.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        } else {
            field.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        }
        field.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        field.setLayoutParams(params);

        return field;
    }
}
