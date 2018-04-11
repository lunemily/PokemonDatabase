package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.PokemonDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.TypeDisplayActivity;
import com.evanfuhr.pokemondatabase.adapters.TypeEfficacyRecyclerViewAdapter;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.List;

public class TypeMatchUpFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    public static final String TYPE_ID = "type_id";

    Pokemon mPokemon = new Pokemon();
    Type mType = new Type();

    boolean isListByPokemon = false;
    boolean isListByType = false;

    RecyclerView mRecyclerView;
    TextView mTitle;

    public TypeMatchUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_card_list, container, false);

        mTitle = view.findViewById(R.id.card_list_title);
        mTitle.setText("Type Match Ups");

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(PokemonDisplayActivity.POKEMON_ID)) {
                mPokemon.setId(bundle.getInt(PokemonDisplayActivity.POKEMON_ID));
                isListByPokemon = true;
            }
            if (bundle.containsKey(TypeDisplayActivity.TYPE_ID)) {
                mType.setId(bundle.getInt(TypeDisplayActivity.TYPE_ID));
                isListByType = true;
            }
        } else {
            Log.i("TypeMatchUpFragment Log", "No bundle");
        }
        List<Type> types = getFilteredTypes();

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new TypeEfficacyRecyclerViewAdapter(types, mListener));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Type type);
    }

    private List<Type> getFilteredTypes() {
        TypeDAO typeDAO = new TypeDAO(getActivity());
        List<Type> filteredTypes = new ArrayList<>();

        if (isListByType) {
            filteredTypes = typeDAO.getSingleTypeEfficacy(mType);
        } else if (isListByPokemon) {
            // Rather than iterating over ALL types, just get the mPokemon's types and load
            PokemonDAO pokemonDAO = new PokemonDAO(getActivity());
            List<Type> pokemonTypes = pokemonDAO.getTypesForPokemon(mPokemon);

            // Check for single or dual type mPokemon
            if (pokemonTypes.size() == 1) {
                filteredTypes = typeDAO.getSingleTypeEfficacy(pokemonTypes.get(0));
            } else {
                filteredTypes = typeDAO.getDualTypeEfficacy(pokemonTypes.get(0), pokemonTypes.get(1));
            }

            pokemonDAO.close();
        }

        typeDAO.close();

        return filteredTypes;
    }
}
