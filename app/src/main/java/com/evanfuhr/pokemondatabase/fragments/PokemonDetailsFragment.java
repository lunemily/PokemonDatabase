package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.PokemonDisplayActivity;
import com.evanfuhr.pokemondatabase.data.EggGroupDAO;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.models.EggGroup;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.views.GifImageView;

import java.util.List;

public class PokemonDetailsFragment extends Fragment {

    public static final String TYPE_ID = "type_id";

    Pokemon pokemon = new Pokemon();

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

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(PokemonDisplayActivity.POKEMON_ID)) {
                pokemon.setId(bundle.getInt(PokemonDisplayActivity.POKEMON_ID));
                setPokemonDetails();
            }
        } else {
            Log.i("PokemonDetFragment Log", "No bundle");
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

    void setPokemonDetails() {
        loadPokemon();
        setFragmentGenus();
        setFragmentSprite();
        setFragmentHeightAndWeight();
        setFragmentEggGroups();
        setExternalLinks();
    }

    void loadPokemon() {
        PokemonDAO pokemonDAO = new PokemonDAO(getActivity());
        pokemon = pokemonDAO.getPokemonByID(pokemon);
        pokemonDAO.close();
    }

    void setFragmentEggGroups() {
        PokemonDAO pokemonDAO = new PokemonDAO(getActivity());
        EggGroupDAO eggGroupDAO = new EggGroupDAO(getActivity());
        pokemon.setEggGroups(pokemonDAO.getEggGroupsForPokemon(pokemon));
        List<EggGroup> eggGroups = pokemon.getEggGroups();

        for (EggGroup eg : eggGroups) {
            TextView textViewEggGroup = new TextView(getActivity());
            textViewEggGroup.setText(eggGroupDAO.getEggGroupByID(eg).getName());
            _eggGroups.addView(textViewEggGroup);
        }
        pokemonDAO.close();
        eggGroupDAO.close();
    }

    void setFragmentGenus() {
        String genus = pokemon.getGenus();
        _genus.setText(genus);
    }

    void setFragmentHeightAndWeight() {
        String height = pokemon.getHeight() + " m";
        String weight = pokemon.getWeight() + " kg";

        _height.setText(height);
        _weight.setText(weight);
    }

    void setFragmentSprite() {
        int spriteID = getContext().getResources().getIdentifier(pokemon.getSpriteName(), "drawable", getContext().getPackageName());
        try {
            _spriteGif.setGifImageResource(spriteID);
        } catch (Exception e) {
            Log.w("PokemonDetFragment", "Sprite not found");
        }
    }

    void setExternalLinks() {
        _bulbapedia.setClickable(true);
        _bulbapedia.setMovementMethod(LinkMovementMethod.getInstance());
        _bulbapedia.setText(Html.fromHtml("<a href='https://bulbapedia.bulbagarden.net/wiki/" + pokemon.getName() + "_(Pok%C3%A9mon)'>Bulbapedia</a>"));

        _smogon.setClickable(true);
        _smogon.setMovementMethod(LinkMovementMethod.getInstance());
        _smogon.setText(Html.fromHtml("<a href='http://www.smogon.com/dex/sm/pokemon/" + pokemon.getName() + "'>Smogon</a>"));
    }
}
