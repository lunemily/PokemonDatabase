package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.TypeDisplayActivity;
import com.evanfuhr.pokemondatabase.data.EggGroupDAO;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.EggGroup;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;
import com.evanfuhr.pokemondatabase.views.GifImageView;

import java.util.List;

public class PokemonDetailsFragment extends Fragment {

    public static final String TYPE_ID = "type_id";

    Pokemon _pokemon;

    TextView _bulbapedia;
    LinearLayout _eggGroups;
    TextView _genus;
    TextView _height;
    TextView _smogon;
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

        _bulbapedia = detailsFragmentView.findViewById(R.id.bulbapediaLink);
        _eggGroups = detailsFragmentView.findViewById(R.id.pokemonEggGroupsList);
        _genus = detailsFragmentView.findViewById(R.id.pokemonGenusText);
        _height = detailsFragmentView.findViewById(R.id.pokemonHeightValue);
        _smogon = detailsFragmentView.findViewById(R.id.smogonLink);
        _spriteGif = detailsFragmentView.findViewById(R.id.gifImageViewPokemonSprite);
        _weight = detailsFragmentView.findViewById(R.id.pokemonWeightValue);

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
        setFragmentGenus();
        setFragmentSprite();
        setFragmentHeightAndWeight();
        setFragmentEggGroups();
        setExternalLinks();
    }

    void setFragmentEggGroups() {
        PokemonDAO pokemonDAO = new PokemonDAO(getActivity());
        EggGroupDAO eggGroupDAO = new EggGroupDAO(getActivity());
        _pokemon.setEggGroups(pokemonDAO.getEggGroupsForPokemon(_pokemon));
        List<EggGroup> eggGroups = _pokemon.getEggGroups();

        for (EggGroup eg : eggGroups) {
            TextView textViewEggGroup = new TextView(getActivity());
            textViewEggGroup.setText(eggGroupDAO.getEggGroupByID(eg).getName());
            _eggGroups.addView(textViewEggGroup);
        }
    }

    void setFragmentGenus() {
        String genus = _pokemon.getGenus();
        _genus.setText(genus);
    }

    void setFragmentHeightAndWeight() {
        String height = _pokemon.getHeight() + " m";
        String weight = _pokemon.getWeight() + " kg";

        _height.setText(height);
        _weight.setText(weight);
    }

    void setFragmentSprite() {
        int spriteID = getContext().getResources().getIdentifier(_pokemon.getSpriteName(), "drawable", getContext().getPackageName());
        _spriteGif.setGifImageResource(spriteID);
    }

    void setExternalLinks() {
        _bulbapedia.setClickable(true);
        _bulbapedia.setMovementMethod(LinkMovementMethod.getInstance());
        _bulbapedia.setText(Html.fromHtml("<a href='https://bulbapedia.bulbagarden.net/wiki/" + _pokemon.getName() + "_(Pok%C3%A9mon)'>Bulbapedia</a>"));

        _smogon.setClickable(true);
        _smogon.setMovementMethod(LinkMovementMethod.getInstance());
        _smogon.setText(Html.fromHtml("<a href='http://www.smogon.com/dex/sm/pokemon/" + _pokemon.getName() + "'>Smogon</a>"));
    }
}
