package com.evanfuhr.pokemondatabase.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.PokemonDisplayActivity;
import com.evanfuhr.pokemondatabase.adapters.EvolutionRecyclerViewAdapter;
import com.evanfuhr.pokemondatabase.data.EvolutionDAO;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Evolution;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class EvolutionListFragment extends Fragment {

    private EvolutionListFragment.OnListFragmentInteractionListener mListener;

    Pokemon mPokemon = new Pokemon();

    boolean isListByPokemon = false;

    RecyclerView mRecyclerView;
    TextView mTitle;
    private ProgressBar mProgressBar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EvolutionListFragment() {
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
        mTitle.setText(R.string.evolutions);
        mProgressBar = view.findViewById(R.id.progressBar);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(PokemonDisplayActivity.POKEMON_ID)) {
                mPokemon.setId(bundle.getInt(PokemonDisplayActivity.POKEMON_ID));
                isListByPokemon = true;
                mTitle.setVisibility(View.VISIBLE);
            }
        } else {
            Log.i("EvoListFragment Log", "No bundle");
            setHasOptionsMenu(true);
        }
        List<Evolution> evolutions = new ArrayList<>();

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new EvolutionRecyclerViewAdapter(evolutions, mListener));

        new EvolutionLoader(getActivity()).execute("");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AbilityListFragment.OnListFragmentInteractionListener) {
            mListener = (EvolutionListFragment.OnListFragmentInteractionListener) context;
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

        void onListFragmentInteraction(Pokemon item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_search_list:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private class EvolutionLoader extends AsyncTask<String, Void, List<Evolution>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        Context mContext;

        EvolutionLoader(Context context) {
            this.mContext = context;
        }

        @Override
        protected List<Evolution> doInBackground(String... strings) {

            EvolutionDAO evolutionDAO = new EvolutionDAO(mContext);
            PokemonDAO pokemonDAO = new PokemonDAO(mContext);
            TypeDAO typeDAO = new TypeDAO(mContext);

            List<Evolution> rawEvolutions = evolutionDAO.getAllEvolutions(mPokemon);
            List<Evolution> evolutions = new ArrayList<>();

            for (Evolution evolution : rawEvolutions) {
                evolution = evolutionDAO.getEvolution(evolution);
                Pokemon beforePokemon = pokemonDAO.getPokemon(evolution.getBeforePokemon());
                beforePokemon.setTypes(typeDAO.getTypes(beforePokemon));
                evolution.setBeforePokemon(beforePokemon);

                Pokemon afterPokemon = pokemonDAO.getPokemon(evolution.getAfterPokemon());
                afterPokemon.setTypes(typeDAO.getTypes(afterPokemon));
                evolution.setAfterPokemon(afterPokemon);
                evolutions.add(evolution);
            }
            typeDAO.close();
            pokemonDAO.close();
            evolutionDAO.close();

            return evolutions;
        }

        @Override
        protected void onPostExecute(List<Evolution> evolutions) {
            super.onPostExecute(evolutions);
            EvolutionRecyclerViewAdapter adapter = (EvolutionRecyclerViewAdapter) mRecyclerView.getAdapter();
            adapter.injectEvolutions(evolutions);
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
