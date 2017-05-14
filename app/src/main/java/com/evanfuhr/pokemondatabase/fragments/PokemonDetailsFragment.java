package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.views.GifImageView;

import java.util.List;

public class PokemonDetailsFragment extends Fragment {

    Pokemon _pokemon;

    LinearLayout _abilities;
    TextView _dexID;
    TextView _height;
    GifImageView _spriteGif;
    TextView _weight;


    public PokemonDetailsFragment() {
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
        View detailsFragmentView = inflater.inflate(R.layout.fragment_pokemon_details, container, false);

        _abilities = (LinearLayout) detailsFragmentView.findViewById(R.id.pokemonAbilitiesList);
        _dexID = (TextView) detailsFragmentView.findViewById(R.id.nationalDexNumber);
        _height = (TextView) detailsFragmentView.findViewById(R.id.pokemonHeightValue);
        _spriteGif = (GifImageView) detailsFragmentView.findViewById(R.id.gifImageViewPokemonSprite);
        _weight = (TextView) detailsFragmentView.findViewById(R.id.pokemonWeightValue);

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

    public void setPokemonDetails(Pokemon pokemon) {
        _pokemon = pokemon;
        setDexID();
        setSprite();
        setAbilities();
        setHeightAndWeight();
    }

    void setAbilities() {
        PokemonDAO db_details_fragment = new PokemonDAO(getActivity());
        _pokemon.setAbilities(db_details_fragment.getAbilitiesForPokemon(_pokemon));
        List<Ability> abilities = _pokemon.getAbilities();

        for (Ability a : abilities) {
            TextView textViewAbility = new TextView(getActivity());
            textViewAbility.setText(a.getName());
            if (a.getIsHidden()) {
                textViewAbility.setTextColor(Color.argb(90, 0, 0, 0));
            }
            _abilities.addView(textViewAbility);
        }
    }

    void setDexID() {
        String dexID = "#" + _pokemon.getID();
        _dexID.setText(dexID);
    }

    void setHeightAndWeight() {
        String height = _pokemon.getHeight() + " m";
        String weight = _pokemon.getWeight() + " kg";

        _height.setText(height);
        _weight.setText(weight);
    }

    void setSprite() {
        int spriteID = getContext().getResources().getIdentifier(_pokemon.getSpriteName(), "drawable", getContext().getPackageName());
        _spriteGif.setGifImageResource(spriteID);
    }
}
