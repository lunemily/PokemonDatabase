package com.evanfuhr.pokemondatabase.fragments;

import android.content.Context;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.FlavorDAO;
import com.evanfuhr.pokemondatabase.data.NatureDAO;
import com.evanfuhr.pokemondatabase.data.StatDAO;
import com.evanfuhr.pokemondatabase.models.Nature;

public class NatureDetailsFragment extends Fragment {

    Nature mNature;

    Button increasedStatButton;
    Button decreasedStatButton;
    Button likesFlavorButton;
    Button hatesFlavorButton;

    public NatureDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View detailsFragmentView = inflater.inflate(R.layout.fragment_nature_details, container, false);

        increasedStatButton = detailsFragmentView.findViewById(R.id.increasedStatButton);
        decreasedStatButton = detailsFragmentView.findViewById(R.id.decreasedStatButton);
        likesFlavorButton = detailsFragmentView.findViewById(R.id.likesFlavorButton);
        hatesFlavorButton = detailsFragmentView.findViewById(R.id.hatesFlavorButton);

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
        loadNature(nature);
        loadNatureStats();
        loadNatureFlavors();
        setStats();
        setFlavors();
    }

    private void loadNature(Nature nature) {
        NatureDAO natureDAO = new NatureDAO(getActivity());

        mNature = natureDAO.getNatureById(nature);

        natureDAO.close();
    }

    private void loadNatureStats() {
        StatDAO statDAO = new StatDAO(getActivity());

        mNature.setIncreasedStat(statDAO.getStatById(mNature.getIncreasedStat()));
        mNature.setDecreasedStat(statDAO.getStatById(mNature.getDecreasedStat()));

        statDAO.close();
    }

    private void loadNatureFlavors() {
        FlavorDAO flavorDAO = new FlavorDAO(getActivity());

        mNature.setLikesFlavor(flavorDAO.getFlavorById(mNature.getLikesFlavor()));
        mNature.setHatesFlavor(flavorDAO.getFlavorById(mNature.getHatesFlavor()));

        flavorDAO.close();
    }

    private void setStats() {
        increasedStatButton.setId(mNature.getIncreasedStat().getId());
        increasedStatButton.setText(mNature.getIncreasedStat().getName());

        decreasedStatButton.setId(mNature.getDecreasedStat().getId());
        decreasedStatButton.setText(mNature.getDecreasedStat().getName());
    }

    private void setFlavors() {
        likesFlavorButton.setId(mNature.getLikesFlavor().getId());
        likesFlavorButton.setText(mNature.getLikesFlavor().getName());

        hatesFlavorButton.setId(mNature.getHatesFlavor().getId());
        hatesFlavorButton.setText(mNature.getHatesFlavor().getName());
    }
}
