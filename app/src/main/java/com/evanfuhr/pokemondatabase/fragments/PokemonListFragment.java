package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.adapters.MyPokemonRecyclerViewAdapter;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.models.Pokemon;

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

    RecyclerView _recyclerView;

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
        View view = inflater.inflate(R.layout.fragment_simple_list, container, false);

        List<Pokemon> pokemons = getTypedPokemon();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            _recyclerView = (RecyclerView) view;
            _recyclerView.setLayoutManager(new LinearLayoutManager(context));
            _recyclerView.setAdapter(new MyPokemonRecyclerViewAdapter(pokemons, mListener));
        }
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
        MyPokemonRecyclerViewAdapter adapter = (MyPokemonRecyclerViewAdapter) _recyclerView.getAdapter();
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

    List<Pokemon> getTypedPokemon() {
        PokemonDAO pokemonDAO = new PokemonDAO(getActivity());
        List<Pokemon> unTypedPokemons = pokemonDAO.getAllPokemon();
        List<Pokemon> typedPokemons = new ArrayList<>();

        for (Pokemon pokemon : unTypedPokemons) {
            pokemon.setTypes(pokemonDAO.getTypesForPokemon(pokemon));
            typedPokemons.add(pokemon);
        }

        pokemonDAO.close();

        return typedPokemons;
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
}
