package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.TypeDisplayActivity;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Type;

public class TypeMatchUpFragment extends Fragment {

    public static final String TYPE_ID = "type_id";

    Type _type;

    LinearLayout _attackerLayout;
    LinearLayout _defenderLayout;
    TextView _attackerLabel;
    TextView _defenderLabel;

    TableRow.LayoutParams _buttonParams;
    TableRow.LayoutParams _fieldParams;

    public TypeMatchUpFragment() {
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
        View matchUpFragmentView = inflater.inflate(R.layout.fragment_type_match_up, container, false);

        _attackerLayout = (LinearLayout) matchUpFragmentView.findViewById(R.id.attacker_layout);
        _defenderLayout = (LinearLayout) matchUpFragmentView.findViewById(R.id.defender_layout);
        _attackerLabel = (TextView) matchUpFragmentView.findViewById(R.id.attacker_text);
        _defenderLabel = (TextView) matchUpFragmentView.findViewById(R.id.defender_text);

        _buttonParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        _buttonParams.setMargins(16, 4, 16, 4);

        _fieldParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        _fieldParams.setMargins(16, 4, 16, 4);

        return matchUpFragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setTypeMatchUps(Type type) {
        TypeDAO typeDAO = new TypeDAO(getActivity());

        _type = type;
        _type.set_attackingTypes(typeDAO.getSingleTypeEfficacy(_type).get_attackingTypes());
        _type.set_defendingTypes(typeDAO.getSingleTypeEfficacy(_type).get_defendingTypes());

        fillMatchUpCards();
    }

    // ORCHESTRATOR
    public void fillMatchUpCards() {

        _attackerLabel.setText(_type.getName().toUpperCase() + " " + getActivity().getString(R.string.typeIsAttacker));
        _defenderLabel.setText(_type.getName().toUpperCase() + " " + getActivity().getString(R.string.typeIsDefender));

        _attackerLayout.addView(generateAttackTable(_type));
        _defenderLayout.addView(generateDefenseTable(_type));

    }

    // Type is attacker
    private TableLayout generateAttackTable(Type type) {
        TypeDAO typeDAO = new TypeDAO(getActivity());

        final TableLayout tableLayout = new TableLayout(getActivity());

        for (Type t : type.get_defendingTypes()) {
            t = typeDAO.getTypeByID(t);
            tableLayout.addView(generateTypeMatchUpRow(t));
        }

        typeDAO.close();
        return tableLayout;
    }

    // Type is defender
    private TableLayout generateDefenseTable(Type type) {
        TypeDAO typeDAO = new TypeDAO(getActivity());

        final TableLayout tableLayout = new TableLayout(getActivity());

        for (Type t : type.get_attackingTypes()) {
            t = typeDAO.getTypeByID(t);
            tableLayout.addView(generateTypeMatchUpRow(t));
        }

        typeDAO.close();
        return tableLayout;
    }

    private TableRow generateTypeMatchUpRow(Type type) {
        TypeDAO typeDAO = new TypeDAO(getActivity());
        type = typeDAO.getTypeByID(type);
        final TableRow row = new TableRow(getActivity());

        final Button typeButton = new Button(getActivity());typeButton.setText(type.getName());
        typeButton.setId(type.getID());
        typeButton.setBackgroundColor(Color.parseColor(type.getColor()));
        typeButton.setLayoutParams(_buttonParams);
        typeButton.setPadding(16, 0, 16, 0);
        typeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButtonTypeDetails(view);
            }
        });
        row.addView(typeButton);

        String multiplier;
        switch (Math.round(type.getEfficacy() * 100)) {
            case 0:
                multiplier = "x0";
                break;
            case 50:
                multiplier = "x1/2";
                break;
            case 200:
                multiplier = "x2";
                break;
            default:
                multiplier = "xERROR";
                break;
        }

        TextView field = new TextView(getActivity());
        field.setText(multiplier);
        field.setTextColor(Color.BLACK);
        field.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        field.setLayoutParams(_fieldParams);
        row.addView(field);

        typeDAO.close();
        return row;
    }

    private void onClickButtonTypeDetails(View view) {
        int type_id = view.getId();

        //Build the intent to load the player sheet
        Intent intent = new Intent(getActivity(), TypeDisplayActivity.class);
        //Load the hero ID to send to the player sheet
        intent.putExtra(TYPE_ID, type_id);

        startActivity(intent);
    }
}
