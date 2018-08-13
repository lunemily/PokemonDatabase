package com.evanfuhr.pokemondatabase.fragments.display;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.display.PokemonDisplayActivity;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.utils.ExternalLink;
import com.evanfuhr.pokemondatabase.views.GifImageView;

public class PokemonDetailsFragment extends Fragment {

    public static final String TYPE_ID = "type_id";

    Pokemon mPokemon = new Pokemon();

    TextView mBulbapedia;
    TextView mGenus;
    TextView mHeight;
    TextView mSerebii;
    TextView mSmogon;
    GifImageView mSpriteGif;
    TextView mWeight;

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

        mBulbapedia = detailsFragmentView.findViewById(R.id.bulbapediaLink);
        mGenus = detailsFragmentView.findViewById(R.id.pokemonGenusText);
        mHeight = detailsFragmentView.findViewById(R.id.pokemonHeightValue);
        mSerebii = detailsFragmentView.findViewById(R.id.serebiiLink);
        mSmogon = detailsFragmentView.findViewById(R.id.smogonLink);
        mSpriteGif = detailsFragmentView.findViewById(R.id.gifImageViewPokemonSprite);
        mWeight = detailsFragmentView.findViewById(R.id.pokemonWeightValue);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(PokemonDisplayActivity.POKEMON_ID)) {
                mPokemon.setId(bundle.getInt(PokemonDisplayActivity.POKEMON_ID));
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
        setExternalLinks();
    }

    void loadPokemon() {
        PokemonDAO pokemonDAO = new PokemonDAO(getActivity());
        mPokemon = pokemonDAO.getPokemon(mPokemon);
        pokemonDAO.close();
    }

    void setFragmentGenus() {
        String genus = mPokemon.getGenus();
        mGenus.setText(genus);
    }

    void setFragmentHeightAndWeight() {
        String height = mPokemon.getHeight() + " m";
        String weight = mPokemon.getWeight() + " kg";

        mHeight.setText(height);
        mWeight.setText(weight);
    }

    void setFragmentSprite() {
        int spriteID = getContext().getResources().getIdentifier(mPokemon.getSpriteName(), "drawable", getContext().getPackageName());
        try {
            mSpriteGif.setGifImageResource(spriteID);
        } catch (Exception e) {
            Log.w("PokemonDetFragment", "Sprite not found");
        }
    }

    void setExternalLinks() {
        mBulbapedia.setClickable(true);
        mBulbapedia.setMovementMethod(LinkMovementMethod.getInstance());
        mBulbapedia.setText(ExternalLink.getBulbapediaLink(mPokemon, getActivity()));

        mSmogon.setClickable(true);
        mSmogon.setMovementMethod(LinkMovementMethod.getInstance());
        mSmogon.setText(ExternalLink.getSmogonLink(mPokemon, getActivity()));

        mSerebii.setClickable(true);
        mSerebii.setMovementMethod(LinkMovementMethod.getInstance());
        mSerebii.setText(ExternalLink.getSerebiiLink(mPokemon, getActivity()));
    }
}
