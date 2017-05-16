package com.evanfuhr.pokemondatabase.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.PokemonDisplayActivity;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;
import com.evanfuhr.pokemondatabase.views.GifImageView;

import java.util.List;

public class PokemonListFragment extends Fragment {

    public static final String POKEMON_ID = "pokemon_id";

    private OnFragmentInteractionListener mListener;

    LinearLayout _pokemonList;
    LinearLayout _abilities;
    TextView _dexID;
    LinearLayout _eggGroups;
    TextView _height;
    GifImageView _spriteGif;
    TextView _weight;

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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    void generatePokemonList() {
        PokemonDAO db = new PokemonDAO(getActivity());
        TypeDAO typeDAO = new TypeDAO(getActivity());

        List<Pokemon> pokemons = db.getAllPokemon();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 16, 0, 0);

        int counter = 1;

        for (Pokemon pokemon : pokemons) {

            //Create hero button
            final Button pokemon_button = new Button(getActivity());
            List<Type> types = db.getTypesForPokemon(pokemon);


            //create a new gradient color
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

            //Add the pokemon button to the table row
            _pokemonList.addView(pokemon_button);

            registerForContextMenu(pokemon_button);

            if (counter >= 200) {
                break;
            }
            counter++;
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
}
