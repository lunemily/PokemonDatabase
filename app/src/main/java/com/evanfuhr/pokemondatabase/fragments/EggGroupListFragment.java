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
import com.evanfuhr.pokemondatabase.adapters.EggGroupRecyclerViewAdapter;
import com.evanfuhr.pokemondatabase.data.EggGroupDAO;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.models.EggGroup;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SEARCH_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EggGroupListFragment.OnListFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EggGroupListFragment extends Fragment
        implements SearchView.OnQueryTextListener {

    private EggGroupListFragment.OnListFragmentInteractionListener mListener;

    Pokemon mPokemon = new Pokemon();

    boolean isListByPokemon = false;

    RecyclerView mRecyclerView;
    TextView mTitle;
    private ProgressBar mProgressBar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EggGroupListFragment() {
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
        mTitle.setText(R.string.egg_groups);
        mProgressBar = view.findViewById(R.id.progressBar);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(PokemonDisplayActivity.POKEMON_ID)) {
                mPokemon.setId(bundle.getInt(PokemonDisplayActivity.POKEMON_ID));
                isListByPokemon = true;
                mTitle.setVisibility(View.VISIBLE);
            }
        } else {
            Log.i("EggGroupList Log", "No bundle");
            setHasOptionsMenu(true);
        }
        List<EggGroup> eggGroups = new ArrayList<>();

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new EggGroupRecyclerViewAdapter(eggGroups, mListener));

        new EggGroupLoader(getActivity()).execute("");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EggGroupListFragment.OnListFragmentInteractionListener) {
            mListener = (EggGroupListFragment.OnListFragmentInteractionListener) context;
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
        EggGroupRecyclerViewAdapter adapter = (EggGroupRecyclerViewAdapter) mRecyclerView.getAdapter();
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

        void onEggGroupListFragmentInteraction(EggGroup item);
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
    private class EggGroupLoader extends AsyncTask<String, Void, List<EggGroup>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        Context mContext;

        EggGroupLoader(Context context) {
            this.mContext = context;
        }

        @Override
        protected List<EggGroup> doInBackground(String... strings) {

            EggGroupDAO EggGroupDAO = new EggGroupDAO(mContext);
            List<EggGroup> raweggGroups;
            List<EggGroup> eggGroups = new ArrayList<>();

            if (isListByPokemon) {
                raweggGroups = EggGroupDAO.getEggGroups(mPokemon);
            } else {
                raweggGroups = EggGroupDAO.getAllEggGroups();
            }

            for (EggGroup EggGroup : raweggGroups) {
                eggGroups.add(EggGroupDAO.getEggGroup(EggGroup));
            }

            EggGroupDAO.close();

            return eggGroups;
        }

        @Override
        protected void onPostExecute(List<EggGroup> eggGroups) {
            super.onPostExecute(eggGroups);
            EggGroupRecyclerViewAdapter adapter = (EggGroupRecyclerViewAdapter) mRecyclerView.getAdapter();
            adapter.injectEggGroups(eggGroups);
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
