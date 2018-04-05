package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.PokemonDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.TypeDisplayActivity;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.List;

public class TypeMatchUpFragment extends Fragment {

    public static final String TYPE_ID = "type_id";

    Pokemon pokemon;
    Type _type;
    Type _secondaryType;
    List<Type> _types = new ArrayList<>();

    boolean isListByPokemon = false;
    boolean _forPokemon = false;
    boolean _isDualType = false;

    LinearLayout _attackerLayout;
    LinearLayout _defenderLayout;
    TextView _attackerLabel;
    TextView _defenderLabel;
    CardView _attackerCard;

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

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(PokemonDisplayActivity.POKEMON_ID)) {
                pokemon.setId(bundle.getInt(PokemonDisplayActivity.POKEMON_ID));
                isListByPokemon = true;
            }
        } else {
            Log.i("TypeMatchUpFragment Log", "No bundle");
        }

        _attackerLayout = matchUpFragmentView.findViewById(R.id.attacker_layout);
        _defenderLayout = matchUpFragmentView.findViewById(R.id.defender_layout);
        _attackerLabel = matchUpFragmentView.findViewById(R.id.attacker_text);
        _defenderLabel = matchUpFragmentView.findViewById(R.id.defender_text);
        _attackerCard = matchUpFragmentView.findViewById(R.id.attacker_card);

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

    public void setTypeMatchUps(Type type, boolean forPokemon) {
        List<Type> types = new ArrayList<>();
        types.add(type);
        setTypeMatchUps(types, forPokemon);
    }

    public void setTypeMatchUps(List<Type> types, boolean forPokemon) {
        TypeDAO typeDAO = new TypeDAO(getActivity());

        _types = types;
        _type = _types.get(0);
        _forPokemon = forPokemon;
        _isDualType = (_types.size() == 2);

        // If _secondaryType != null, it's for a pokemon
        if (!forPokemon) {
            _type.set_attackingTypes(typeDAO.getSingleTypeEfficacy(_type).get_attackingTypes());
            _type.set_defendingTypes(typeDAO.getSingleTypeEfficacy(_type).get_defendingTypes());
        } else {
            // Check for single or dual type pokemon
            if (!_isDualType) {
                _type.set_attackingTypes(typeDAO.getSingleTypeEfficacy(_type).get_attackingTypes());
            } else {
                _secondaryType = _types.get(1);
                _type.set_attackingTypes(typeDAO.getDualTypeEfficacy(_type, _secondaryType).get_attackingTypes());
            }
        }

        typeDAO.close();
        fillMatchUpCards();
    }

    // ORCHESTRATOR
    public void fillMatchUpCards() {
        if (_forPokemon) {
            ((LinearLayout) _attackerCard.getParent()).removeView(_attackerCard);
        } else {
            _attackerLabel.setText(_type.getName().toUpperCase() + " " + getActivity().getString(R.string.typeIsAttacker));
            _attackerLayout.addView(generateAttackTable(_type));
        }

        if (!_isDualType) {
            _defenderLabel.setText(_type.getName().toUpperCase() + " " + getString(R.string.typeIsDefender));
        } else {
            _defenderLabel.setText(_type.getName().toUpperCase() + "/" + _secondaryType.getName().toUpperCase() + " " + getString(R.string.typesAreDefenders));
        }
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
        typeButton.setId(type.getId());
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
            case 25:
                multiplier = "x1/4";
                break;
            case 50:
                multiplier = "x1/2";
                break;
            case 200:
                multiplier = "x2";
                break;
            case 400:
                multiplier = "x4";
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
