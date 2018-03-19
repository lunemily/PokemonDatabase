package com.evanfuhr.pokemondatabase.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.PokemonDisplayActivity;
import com.evanfuhr.pokemondatabase.adapters.AbilityRecyclerViewAdapter;
import com.evanfuhr.pokemondatabase.data.AbilityDAO;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.interfaces.PokemonDataInterface;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AbilityListFragment.OnListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AbilityListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AbilityListFragment extends Fragment {

    private AbilityListFragment.OnListFragmentInteractionListener mListener;

    Pokemon pokemon = new Pokemon();

    boolean isListByPokemon = false;

    RecyclerView mRecyclerView;
    TextView mTitle;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AbilityListFragment() {
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

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(PokemonDisplayActivity.POKEMON_ID)) {
                pokemon.setID(bundle.getInt(PokemonDisplayActivity.POKEMON_ID));
                isListByPokemon = true;
            }
        } else {
            Log.i("AbilityListFragment Log", "No bundle");
        }
        List<Ability> abilities = getFilteredAbilities();

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new AbilityRecyclerViewAdapter(abilities, mListener));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AbilityListFragment.OnListFragmentInteractionListener) {
            mListener = (AbilityListFragment.OnListFragmentInteractionListener) context;
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
    public interface OnListFragmentInteractionListener {

        void onListFragmentInteraction(Ability item);
    }

    private List<Ability> getFilteredAbilities() {
        AbilityDAO abilityDAO = new AbilityDAO(getActivity());
        List<Ability> unfilteredAbilities = abilityDAO.getAllAbilities();
        List<Ability> filteredAbilities = new ArrayList<>();

        if (isListByPokemon) {
            // Rather than iterating over ALL abilities, just get the pokemon's abilities and load
            PokemonDAO pokemonDAO = new PokemonDAO(getActivity());
            List<Ability> pokemonAbilities = pokemonDAO.getAbilitiesForPokemon(pokemon);
            for (Ability ability : pokemonAbilities) {
                filteredAbilities.add(abilityDAO.getAbilityByID(ability));
            }
            pokemonDAO.close();
            mTitle.setText(R.string.abilities);
        } else {
            filteredAbilities = unfilteredAbilities;

            // Title is activity title
            mTitle.setVisibility(View.INVISIBLE);
        }

        abilityDAO.close();

        return filteredAbilities;
    }
}
