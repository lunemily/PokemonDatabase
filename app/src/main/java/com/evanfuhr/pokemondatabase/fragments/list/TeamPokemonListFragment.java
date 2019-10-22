package com.evanfuhr.pokemondatabase.fragments.list;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.display.PokemonDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.display.PokemonShowdownActivity;
import com.evanfuhr.pokemondatabase.adapters.TeamPokemonRecyclerViewAdapter;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.TeamPokemon;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;
import com.evanfuhr.pokemondatabase.utils.PokemonShowdownParser;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class TeamPokemonListFragment extends Fragment {

    private TeamPokemonListFragment.OnListFragmentInteractionListener mListener;

    Pokemon mPokemon = new Pokemon();

    boolean isListByPokemon = false;

    RecyclerView mRecyclerView;
    Button mToggle;
    private ProgressBar mProgressBar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TeamPokemonListFragment() {
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

        mToggle = view.findViewById(R.id.card_list_button);
        mProgressBar = view.findViewById(R.id.progressBar);
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setNestedScrollingEnabled(false);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), VERTICAL);
        mRecyclerView.addItemDecoration(itemDecor);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(PokemonDisplayActivity.POKEMON_ID)) {
                mPokemon.setId(bundle.getInt(PokemonDisplayActivity.POKEMON_ID));
                isListByPokemon = true;
                mToggle.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        } else {
            Log.i("EvoListFragment Log", "No bundle");
            setHasOptionsMenu(true);
        }
        List<TeamPokemon> teamPokemons = new ArrayList<>();

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new TeamPokemonRecyclerViewAdapter(teamPokemons, mListener));

        new TeamPokemonLoader(getActivity()).execute("");

        mToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerView.getVisibility() == View.GONE) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
                else if (mRecyclerView.getVisibility() == View.VISIBLE) {
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof TeamPokemonListFragment.OnListFragmentInteractionListener) {
//            mListener = (TeamPokemonListFragment.OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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

        void onPokemonSelected(Pokemon pokemon);
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
    private class TeamPokemonLoader extends AsyncTask<String, Void, List<TeamPokemon>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        Context mContext;

        TeamPokemonLoader(Context context) {
            this.mContext = context;
        }

        @Override
        protected List<TeamPokemon> doInBackground(String... strings) {
            PokemonDAO pokemonDAO = new PokemonDAO(mContext);
            TypeDAO typeDAO = new TypeDAO(mContext);

            String rawTeams = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("RAWTEAMS", PokemonShowdownActivity.placeHolderRawTeams);
            PokemonShowdownParser parser = new PokemonShowdownParser(rawTeams);
            parser.parse();
            List<TeamPokemon> rawTeamPokemons = parser.getTeams().get(0).getPokemons();
            List<TeamPokemon> teamPokemons = new ArrayList<>();
            Pokemon pokemon;

            for (TeamPokemon teamPokemon : rawTeamPokemons) {
                pokemon = pokemonDAO.getPokemon(teamPokemon.getName());
                List<Type> pokemonTypes = new ArrayList<>();

                for (Type type : typeDAO.getTypes(pokemon)) {
                    type = typeDAO.getType(type);
                    pokemonTypes.add(type);
                }
                teamPokemon.setTypes(pokemonTypes);

                teamPokemons.add(teamPokemon);
            }

            typeDAO.close();
            pokemonDAO.close();

            return teamPokemons;
        }

        @Override
        protected void onPostExecute(List<TeamPokemon> teamPokemons) {
            super.onPostExecute(teamPokemons);
            TeamPokemonRecyclerViewAdapter adapter = (TeamPokemonRecyclerViewAdapter) mRecyclerView.getAdapter();
            adapter.injectTeamPokemons(teamPokemons);
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
