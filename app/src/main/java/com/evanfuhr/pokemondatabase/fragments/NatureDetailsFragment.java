package com.evanfuhr.pokemondatabase.fragments;

import android.content.Context;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.models.Nature;

public class NatureDetailsFragment extends Fragment {

    public NatureDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View detailsFragmentView = inflater.inflate(R.layout.fragment_nature_details, container, false);

//        _accuracy = (TextView) detailsFragmentView.findViewById(R.id.moveAccuracyValue);
//        _category = (TextView) detailsFragmentView.findViewById(R.id.moveCategoryValue);
//        _effect = (TextView) detailsFragmentView.findViewById(R.id.moveEffectValue);
//        _power = (TextView) detailsFragmentView.findViewById(R.id.movePowerValue);
//        _pp = (TextView) detailsFragmentView.findViewById(R.id.movePPValue);
//        _type = (Button) detailsFragmentView.findViewById(R.id.buttonMoveType);

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

    public void setNatureDetails(Nature nature) {

    }
}
