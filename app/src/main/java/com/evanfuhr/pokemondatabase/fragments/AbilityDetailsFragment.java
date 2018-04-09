package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.AbilityDAO;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import java.util.ArrayList;
import java.util.List;

public class AbilityDetailsFragment extends Fragment {

    Ability mAbility;

    TextView mAbilityProseText;
    LinearLayout mReferencesList;

    List<String> proseLinks = new ArrayList<>();

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
        mReferencesList = detailsFragmentView.findViewById(R.id.referencesList);

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
        if (proseLinks.size() >= 1) {
            mReferencesList.setVisibility(View.VISIBLE);
        }
    }

    private void loadAbility(Ability ability) {
        AbilityDAO abilityDAO = new AbilityDAO(getActivity());

        mAbility = abilityDAO.getAbilityByID(ability);

        abilityDAO.close();
    }

    private void setProse() {
        String prose = mAbility.getProse();
        proseLinks = PokemonUtils.getProseLinks(prose);
        prose = PokemonUtils.replaceProseLinks(getActivity(), prose);

        mAbilityProseText.setText(prose);
    }
}
