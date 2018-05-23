package com.evanfuhr.pokemondatabase.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.AbilityDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.LocationDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.MoveDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.TypeDisplayActivity;
import com.evanfuhr.pokemondatabase.adapters.PokemonRecyclerViewAdapter;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Location;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SEARCH_SERVICE;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PokemonListFragment extends Fragment
    implements SearchView.OnQueryTextListener {

    private OnListFragmentInteractionListener mListener;

    Ability mAbility = new Ability();
    Location mLocation = new Location();
    Move mMove = new Move();
    Type mType = new Type();

    boolean isListByAbility = false;
    boolean isListByLocation = false;
    boolean isListByMove = false;
    boolean isListByType = false;

    RecyclerView mRecyclerView;
    TextView mTitle;
    private ProgressBar mProgressBar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PokemonListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_simple_card_list, container, false);

        mTitle = view.findViewById(R.id.card_list_title);
        mTitle.setText(R.string.pokemon);
        mProgressBar = view.findViewById(R.id.progressBar);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(TypeDisplayActivity.TYPE_ID)) {
                mType.setId(bundle.getInt(TypeDisplayActivity.TYPE_ID));
                isListByType = true;
            } else if (bundle.containsKey(AbilityDisplayActivity.ABILITY_ID)) {
                mAbility.setId(bundle.getInt(AbilityDisplayActivity.ABILITY_ID));
                isListByAbility = true;
            } else if (bundle.containsKey(MoveDisplayActivity.MOVE_ID)) {
                mMove.setId(bundle.getInt(MoveDisplayActivity.MOVE_ID));
                isListByMove = true;
            } else if (bundle.containsKey(LocationDisplayActivity.LOCATION_ID)) {
                mLocation.setId(bundle.getInt(LocationDisplayActivity.LOCATION_ID));
                isListByLocation = true;
            }
        } else {
            Log.i("PokemonListFragment Log", "No bundle");
        }

        List<Pokemon> pokemons = new ArrayList<>();

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new PokemonRecyclerViewAdapter(pokemons, mListener));

        new PokemonLoader(getActivity()).execute("");
        PokemonUtils.transitionToast.cancel();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        PokemonRecyclerViewAdapter adapter = (PokemonRecyclerViewAdapter) mRecyclerView.getAdapter();
        adapter.filter(newText);
        return true;
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
        void onListFragmentInteraction(Pokemon item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        inflater.inflate(R.menu.menu_list, menu);

        SearchManager searchManager = (SearchManager)
        getActivity().getSystemService(SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search_list);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        if (!searchMenuItem.isActionViewExpanded()) {
            searchMenuItem.expandActionView();
        }
        else {
            searchMenuItem.collapseActionView();
        }

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
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
    private class PokemonLoader extends AsyncTask<String, Void, List<Pokemon>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        Context mContext;

        PokemonLoader(Context context) {
            this.mContext = context;
        }

        @Override
        protected List<Pokemon> doInBackground(String... strings) {

            PokemonDAO pokemonDAO = new PokemonDAO(mContext);
            List<Pokemon> rawPokemon;
            List<Pokemon> typedPokemon = new ArrayList<>();

            if (isListByAbility) {
                rawPokemon = pokemonDAO.getPokemon(mAbility);
            } else if (isListByMove) {
                rawPokemon = pokemonDAO.getPokemon(mMove);
            } else if (isListByLocation) {
                rawPokemon = pokemonDAO.getPokemon(mLocation);
            } else if (isListByType) {
                rawPokemon = pokemonDAO.getPokemon(mType);
            } else {
                rawPokemon = pokemonDAO.getAllPokemon();
            }

            TypeDAO typeDAO = new TypeDAO(mContext);
            for (Pokemon pokemon : rawPokemon) {
                pokemon = pokemonDAO.getPokemon(pokemon);
                pokemon.setTypes(typeDAO.getTypes(pokemon));
                typedPokemon.add(pokemon);
            }
            pokemonDAO.close();
            typeDAO.close();

            return rawPokemon;
        }

        @Override
        protected void onPostExecute(List<Pokemon> pokemon) {
            super.onPostExecute(pokemon);
            PokemonRecyclerViewAdapter adapter = (PokemonRecyclerViewAdapter) mRecyclerView.getAdapter();
            adapter.injectPokemon(pokemon);
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
