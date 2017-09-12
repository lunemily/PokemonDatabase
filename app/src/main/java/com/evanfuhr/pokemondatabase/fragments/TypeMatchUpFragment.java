package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

import java.util.List;

public class TypeMatchUpFragment extends Fragment {

    public static final String TYPE_ID = "type_id";

    Type _type;

    List<Type> _immunteTo;
    List<Type> _ineffectiveAgainst;
    List<Type> _notVeryEffectiveAgainst;
    List<Type> _resistantTo;
    List<Type> _superEffectiveAgainst;
    List<Type> _weakTo;

    LinearLayout _attackerLayout;
    LinearLayout _defenderLayout;

    TableRow.LayoutParams _buttonParams;
    TableRow.LayoutParams _fieldParams;

    private OnTypeMatchUpFragmentInteractionListener mListener;

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

        _buttonParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        _buttonParams.setMargins(16, 4, 16, 4);

        _fieldParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        _fieldParams.setMargins(16, 4, 16, 4);

        return matchUpFragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTypeMatchUpFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTypeMatchUpFragmentInteractionListener) {
            mListener = (OnTypeMatchUpFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTypeMatchUpFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setTypeMatchUps(Type type) {
        _type = type;
        fillMatchUpCards();
    }

    public void fillMatchUpCards() {
        TypeDAO typeDAO = new TypeDAO(getActivity());
        _type.setSuperEffectiveAgainst(typeDAO.getSingleTypeEfficacy(_type).getSuperEffectiveAgainst());
        _type.setNotVeryEffectiveAgainst(typeDAO.getSingleTypeEfficacy(_type).getNotVeryEffectiveAgainst());
        _type.setIneffectiveAgainst(typeDAO.getSingleTypeEfficacy(_type).getIneffectiveAgainst());

        _attackerLayout.addView(generateAttackTable(_type));
        _defenderLayout.addView(generateDefenseTable(_type));

    }

    private TableLayout generateAttackTable(Type type) {
        TypeDAO typeDAO = new TypeDAO(getActivity());

        final TableLayout tableLayout = new TableLayout(getActivity());

        for (Type t : type.getSuperEffectiveAgainst()) {
            t = typeDAO.getTypeByID(t);
            tableLayout.addView(generateTypeMatchUpRow(t, "x2"));
        }

        for (Type t : type.getNotVeryEffectiveAgainst()) {
            t = typeDAO.getTypeByID(t);
            tableLayout.addView(generateTypeMatchUpRow(t, "x1/2"));
        }

        for (Type t : type.getIneffectiveAgainst()) {
            t = typeDAO.getTypeByID(t);
            tableLayout.addView(generateTypeMatchUpRow(t, "x0"));
        }

        typeDAO.close();
        return tableLayout;
    }

    private TableLayout generateDefenseTable(Type type) {
        TypeDAO typeDAO = new TypeDAO(getActivity());

        final TableLayout tableLayout = new TableLayout(getActivity());

        for (Type t : type.getImmuneTo()) {
            t = typeDAO.getTypeByID(t);
            tableLayout.addView(generateTypeMatchUpRow(t, "x0"));
        }

        for (Type t : type.getResistantTo()) {
            t = typeDAO.getTypeByID(t);
            tableLayout.addView(generateTypeMatchUpRow(t, "x1/2"));
        }

        for (Type t : type.getWeakTo()) {
            t = typeDAO.getTypeByID(t);
            tableLayout.addView(generateTypeMatchUpRow(t, "x2"));
        }

        typeDAO.close();
        return tableLayout;
    }

    private TableRow generateTypeMatchUpRow(Type type, String sMultiplier) {
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

        TextView field = new TextView(getActivity());
        field.setText(sMultiplier);
        field.setTextColor(Color.BLACK);
        field.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        field.setLayoutParams(_fieldParams);
        row.addView(field);

        typeDAO.close();
        return row;
    }

    public interface OnTypeMatchUpFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTypeMatchUpFragmentInteraction(Uri uri);
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
