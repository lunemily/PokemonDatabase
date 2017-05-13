package com.evanfuhr.pokemondatabase;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PokemonDetailsFragment extends Fragment {

    Pokemon _pokemon;
    ImageView _sprite;
    TextView _dexID;

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

        _dexID = (TextView) detailsFragmentView.findViewById(R.id.nationalDexNumber);
        _sprite = (ImageView) detailsFragmentView.findViewById(R.id.pokemonSprite);

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
    }

    void setDexID() {
        String dexID = "#" + _pokemon.getID();
        _dexID.setText(dexID);
    }

    void setSprite() {
        int spriteID = getContext().getResources().getIdentifier(_pokemon.getSpriteName(), "drawable", getContext().getPackageName());
        _sprite.setImageResource(spriteID);
    }
}
