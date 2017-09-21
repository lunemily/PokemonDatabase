package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.PokemonDisplayActivity;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.List;

public class PokemonListFragment extends Fragment {

    public static final String POKEMON_ID = "pokemon_id";

    LinearLayout _pokemonList;

    public PokemonListFragment() {
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
        View pokemonListFragment = inflater.inflate(R.layout.fragment_pokemon_list, container, false);

        _pokemonList = (LinearLayout) pokemonListFragment.findViewById(R.id.pokemonListLayout);

        generatePokemonList();

        return pokemonListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void generatePokemonList() {
        generatePokemonList("%");
    }

    public void generatePokemonList(String nameSearchParam) {
        PokemonDAO pokemonDAO = new PokemonDAO(getActivity());
        TypeDAO typeDAO = new TypeDAO(getActivity());

        List<Pokemon> pokemons = pokemonDAO.getAllPokemon(nameSearchParam);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 16, 0, 0);

        int counter = 1;

        for (Pokemon pokemon : pokemons) {

            // Create pokemon button
            final AppCompatButton pokemon_button = new AppCompatButton(getActivity());
            //final Button pokemon_button = new Button(getActivity());
            List<Type> types = pokemonDAO.getTypesForPokemon(pokemon);


            // Create a new gradient color
            int[] colors = {0, 0, 0, 0};
            if (types.size() == 1) {
                colors[0] = Color.parseColor(typeDAO.getTypeByID(types.get(0)).getColor());
                colors[1] = Color.parseColor(typeDAO.getTypeByID(types.get(0)).getColor());
                colors[2] = Color.parseColor(typeDAO.getTypeByID(types.get(0)).getColor());
                colors[3] = Color.parseColor(typeDAO.getTypeByID(types.get(0)).getColor());
            }
            else {
                colors[0] = Color.parseColor(typeDAO.getTypeByID(types.get(0)).getColor());
                colors[1] = Color.parseColor(typeDAO.getTypeByID(types.get(0)).getColor());
                colors[2] = Color.parseColor(typeDAO.getTypeByID(types.get(1)).getColor());
                colors[3] = Color.parseColor(typeDAO.getTypeByID(types.get(1)).getColor());
            }
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT, colors);

            // Use when AppCompatButton can accept GradientDrawable for button color
//            pokemon_button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(typeDAO.getTypeByID(types.get(0)).getColor())));
//            if (types.size() > 1) {
//                pokemon_button.setTextColor(Color.parseColor(typeDAO.getTypeByID(types.get(1)).getColor()));
//            }

            pokemon_button.setLayoutParams(params);
            pokemon_button.setText(pokemon.getName());
            pokemon_button.setId(pokemon.getID());
            pokemon_button.setBackground(gd);

            //Set click listener for the button
            pokemon_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickButtonPokemonDetails(view);
                }
            });

            //Add the pokemon button to the list
            _pokemonList.addView(pokemon_button);

            registerForContextMenu(pokemon_button);

            // Used for throttling in testing and performance
            if (counter >= 151) {
                break;
            }
            counter++;
        }
    }

    public void regeneratePokemonList(String nameSearchParam) {
        _pokemonList.removeAllViews();
        generatePokemonList(nameSearchParam);
    }

    private void onClickButtonPokemonDetails(View view) {
        //Get the ID associated to the clicked button
        int pokemon_id = view.getId();

        //Build the intent to load the player sheet
        Intent intent = new Intent(getActivity(), PokemonDisplayActivity.class);
        //Load the hero ID to send to the player sheet
        intent.putExtra(POKEMON_ID, pokemon_id);

        startActivity(intent);
    }
}
