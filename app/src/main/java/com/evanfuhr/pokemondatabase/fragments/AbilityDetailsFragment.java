package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.AbilityDAO;
import com.evanfuhr.pokemondatabase.models.Ability;

public class AbilityDetailsFragment extends Fragment {

    Ability mAbility;

    TextView mAbilityProseText;

    public AbilityDetailsFragment() {
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
        View detailsFragmentView = inflater.inflate(R.layout.fragment_ability_details, container, false);

        mAbilityProseText = detailsFragmentView.findViewById(R.id.abilityProseText);

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

    public void setAbilityDetails(Ability ability) {
        loadAbility(ability);
        setProse();
    }

    private void loadAbility(Ability ability) {
        AbilityDAO abilityDAO = new AbilityDAO(getActivity());

        mAbility = abilityDAO.getAbilityByID(ability);

        abilityDAO.close();
    }

    private void setProse() {
        mAbilityProseText.setText(mAbility.getProse());
    }
}
