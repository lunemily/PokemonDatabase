package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.TypeDisplayActivity;
import com.evanfuhr.pokemondatabase.data.AbilityDAO;
import com.evanfuhr.pokemondatabase.data.EggGroupDAO;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.EggGroup;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;
import com.evanfuhr.pokemondatabase.views.GifImageView;

import java.util.List;

public class PokemonDetailsFragment extends Fragment {

    public static final String TYPE_ID = "type_id";

    Pokemon _pokemon;

    LinearLayout _abilities;
    LinearLayout _eggGroups;
    TextView _height;
    GifImageView _spriteGif;
    Button _type1Button;
    Button _type2Button;
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
        _eggGroups = (LinearLayout) detailsFragmentView.findViewById(R.id.pokemonEggGroupsList);
        _height = (TextView) detailsFragmentView.findViewById(R.id.pokemonHeightValue);
        _spriteGif = (GifImageView) detailsFragmentView.findViewById(R.id.gifImageViewPokemonSprite);
        _type1Button = (Button) detailsFragmentView.findViewById(R.id.buttonPokemonType1);
        _type2Button = (Button) detailsFragmentView.findViewById(R.id.buttonPokemonType2);
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
        setFragmentSprite();
        setFragmentHeightAndWeight();
        setFragmentAbilities();
        setFragmentEggGroups();
        setFragmentTypes();
    }

    void setFragmentAbilities() {
        PokemonDAO pokemonDAO = new PokemonDAO(getActivity());
        AbilityDAO abilityDAO = new AbilityDAO(getActivity());

        _pokemon.setAbilities(pokemonDAO.getAbilitiesForPokemon(_pokemon));
        List<Ability> abilities = _pokemon.getAbilities();

        for (Ability a : abilities) {
            Ability ability = abilityDAO.getAbilityByIDForPokemon(a, _pokemon);
            TextView textViewAbility = new TextView(getActivity());
            textViewAbility.setText(ability.getName());
            if (ability.getIsHidden()) {
                textViewAbility.setTextColor(Color.argb(90, 0, 0, 0));
            }
            _abilities.addView(textViewAbility);
        }

        pokemonDAO.close();
        abilityDAO.close();
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

    void setFragmentTypes() {
        PokemonDAO pokemonDAO = new PokemonDAO(getActivity());
        TypeDAO typeDAO = new TypeDAO(getActivity());

        _pokemon.setTypes(pokemonDAO.getTypesForPokemon(_pokemon));
        List<Type> types = Type.loadTypesForPokemon(_pokemon.getTypes(), typeDAO);

        if (types.size() == 1) {
            _type1Button.setVisibility(View.INVISIBLE);
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
            params.column = 3;
            params.span = 3;
            _type2Button.setLayoutParams(params);
            _type2Button.setText(types.get(0).getName());
            _type2Button.setId(types.get(0).getID());
            _type2Button.setBackgroundColor(Color.parseColor(types.get(0).getColor()));

            //Set click listener for the button
            _type2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickButtonTypeDetails(view);
                }
            });
        } else {
            _type1Button.setText(types.get(0).getName());
            _type1Button.setId(types.get(0).getID());
            _type1Button.setBackgroundColor(Color.parseColor(types.get(0).getColor()));

            _type2Button.setText(types.get(1).getName());
            _type2Button.setId(types.get(1).getID());
            _type2Button.setBackgroundColor(Color.parseColor(types.get(1).getColor()));

            //Set click listener for the button
            _type1Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickButtonTypeDetails(view);
                }
            });

            //Set click listener for the button
            _type2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickButtonTypeDetails(view);
                }
            });
        }
    }

    private void onClickButtonTypeDetails(View view) {
        int type_id = view.getId();

        //Build the intent to load the player sheet
        Intent intent = new Intent(getActivity(), TypeDisplayActivity.class);
        //Load the hero ID to send to the player sheet
        intent.putExtra(TYPE_ID, type_id);

        startActivity(intent);
    }
}
