package com.evanfuhr.pokemondatabase.fragments.display;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.display.AbilityDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.display.PokemonShowdownActivity;
import com.evanfuhr.pokemondatabase.models.Ability;

import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;
import java.util.List;

public class PokemonShowdownRawFragment extends Fragment {

    @NonNls
    public static final String POKEMON_SHOWDOWN = "Pokémon Showdown";

    EditText mRawTeams;
    Button mSavePokemonTeams;

    public PokemonShowdownRawFragment() {
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
        View detailsFragmentView = inflater.inflate(R.layout.fragment_pokemon_showdown_raw, container, false);

        mRawTeams = detailsFragmentView.findViewById(R.id.text_area_raw_teams);
        mSavePokemonTeams = detailsFragmentView.findViewById(R.id.save_button_raw_teams);

        setRawTeams();

        mSavePokemonTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSaveButton();
            }
        });

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {

        } else {
            Log.i("RawTeamsFragment Log", "No bundle");
        }
        return detailsFragmentView;
    }

    private void setRawTeams() {
        String rawTeams = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("RAWTEAMS", PokemonShowdownActivity.placeHolderRawTeams);
        mRawTeams.setText(rawTeams);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected void onClickSaveButton() {
        String rawTeams = String.valueOf(mRawTeams.getText());
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("RAWTEAMS", rawTeams).apply();
    }
}
