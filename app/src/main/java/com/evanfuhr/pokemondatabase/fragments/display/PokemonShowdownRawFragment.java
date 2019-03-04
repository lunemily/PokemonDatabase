package com.evanfuhr.pokemondatabase.fragments.display;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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


        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {

        } else {
            Log.i("AbilityDetFragment Log", "No bundle");
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
}
