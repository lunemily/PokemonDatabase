package com.evanfuhr.pokemondatabase.fragments;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
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
import com.evanfuhr.pokemondatabase.activities.PokemonDisplayActivity;
import com.evanfuhr.pokemondatabase.adapters.AbilityRecyclerViewAdapter;
import com.evanfuhr.pokemondatabase.adapters.LocationRecyclerViewAdapter;
import com.evanfuhr.pokemondatabase.data.AbilityDAO;
import com.evanfuhr.pokemondatabase.data.LocationDAO;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Location;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SEARCH_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AbilityListFragment.OnListFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LocationListFragment extends Fragment
        implements SearchView.OnQueryTextListener {

    private LocationListFragment.OnListFragmentInteractionListener mListener;

    Pokemon mPokemon = new Pokemon();

    boolean isListByPokemon = false;

    RecyclerView mRecyclerView;
    TextView mTitle;
    private ProgressBar mProgressBar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LocationListFragment() {
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
        mProgressBar = view.findViewById(R.id.progressBar);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(PokemonDisplayActivity.POKEMON_ID)) {
                mPokemon.setId(bundle.getInt(PokemonDisplayActivity.POKEMON_ID));
                isListByPokemon = true;
            }
        } else {
            Log.i("Fragment Log", "No bundle");
            setHasOptionsMenu(true);
        }
        List<Location> locations = new ArrayList<>();

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new LocationRecyclerViewAdapter(locations, mListener));

        new LocationLoader(getActivity()).execute("");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LocationListFragment.OnListFragmentInteractionListener) {
            mListener = (LocationListFragment.OnListFragmentInteractionListener) context;
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        LocationRecyclerViewAdapter adapter = (LocationRecyclerViewAdapter) mRecyclerView.getAdapter();
        adapter.filter(newText);
        return true;
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

        void onListFragmentInteraction(Location location);
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
    private class LocationLoader extends AsyncTask<String, Void, List<Location>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        Context mContext;

        LocationLoader(Context context) {
            this.mContext = context;
        }

        @Override
        protected List<Location> doInBackground(String... strings) {

            LocationDAO locationDAO = new LocationDAO(mContext);
            List<Location> rawlocations;
            List<Location> locations = new ArrayList<>();

            if (isListByPokemon) {
                rawlocations = locationDAO.getLocations(mPokemon);
            } else {
                rawlocations = locationDAO.getAllLocations();
            }

            for (Location location : rawlocations) {
                locations.add(locationDAO.getLocation(location));
            }

            locationDAO.close();

            return locations;
        }

        @Override
        protected void onPostExecute(List<Location> locations) {
            super.onPostExecute(locations);
            LocationRecyclerViewAdapter adapter = (LocationRecyclerViewAdapter) mRecyclerView.getAdapter();
            adapter.injectLocations(locations);
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
