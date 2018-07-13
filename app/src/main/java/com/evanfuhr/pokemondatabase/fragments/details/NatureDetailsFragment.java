package com.evanfuhr.pokemondatabase.fragments.details;

import android.content.Context;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.display.NatureDisplayActivity;
import com.evanfuhr.pokemondatabase.data.FlavorDAO;
import com.evanfuhr.pokemondatabase.data.NatureDAO;
import com.evanfuhr.pokemondatabase.data.StatDAO;
import com.evanfuhr.pokemondatabase.models.Flavor;
import com.evanfuhr.pokemondatabase.models.Nature;

public class NatureDetailsFragment extends Fragment {

    Nature mNature = new Nature();

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

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if(bundle.containsKey(NatureDisplayActivity.NATURE_ID)) {
                mNature.setId(bundle.getInt(NatureDisplayActivity.NATURE_ID));
                setNatureDetails();
            }
        } else {
            Log.i("MoveDetailsFragment Log", "No bundle");
        }
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

    void setNatureDetails() {
        loadNature();
        loadNatureStats();
        loadNatureFlavors();
        setStats();
        setFlavors();
    }

    void loadNature() {
        NatureDAO natureDAO = new NatureDAO(getActivity());
        mNature = natureDAO.getNature(mNature);
        natureDAO.close();
    }

    void loadNatureStats() {
        StatDAO statDAO = new StatDAO(getActivity());

        mNature.setIncreasedStat(statDAO.getStatById(mNature.getIncreasedStat()));
        mNature.setDecreasedStat(statDAO.getStatById(mNature.getDecreasedStat()));

        statDAO.close();
    }

    void loadNatureFlavors() {
        FlavorDAO flavorDAO = new FlavorDAO(getActivity());

        mNature.setLikesFlavor(flavorDAO.getFlavor(mNature.getLikesFlavor()));
        mNature.setHatesFlavor(flavorDAO.getFlavor(mNature.getHatesFlavor()));

        flavorDAO.close();
    }

    void setStats() {
        increasedStatButton.setId(mNature.getIncreasedStat().getId());
        increasedStatButton.setText(mNature.getIncreasedStat().getName());
        increasedStatButton.setBackgroundColor(Color.parseColor(Flavor.getFlavorColor(mNature.getLikesFlavor().getId())));

        decreasedStatButton.setId(mNature.getDecreasedStat().getId());
        decreasedStatButton.setText(mNature.getDecreasedStat().getName());
        decreasedStatButton.setBackgroundColor(Color.parseColor(Flavor.getFlavorColor(mNature.getHatesFlavor().getId())));
    }

    void setFlavors() {
        likesFlavorButton.setId(mNature.getLikesFlavor().getId());
        likesFlavorButton.setText(mNature.getLikesFlavor().getName());
        likesFlavorButton.setBackgroundColor(Color.parseColor(Flavor.getFlavorColor(mNature.getLikesFlavor().getId())));

        hatesFlavorButton.setId(mNature.getHatesFlavor().getId());
        hatesFlavorButton.setText(mNature.getHatesFlavor().getName());
        hatesFlavorButton.setBackgroundColor(Color.parseColor(Flavor.getFlavorColor(mNature.getHatesFlavor().getId())));
    }
}
