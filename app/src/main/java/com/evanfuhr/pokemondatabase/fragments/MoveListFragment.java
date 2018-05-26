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
import com.evanfuhr.pokemondatabase.activities.PokemonDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.TypeDisplayActivity;
import com.evanfuhr.pokemondatabase.adapters.MoveRecyclerViewAdapter;
import com.evanfuhr.pokemondatabase.adapters.PokemonMoveRecyclerViewAdapter;
import com.evanfuhr.pokemondatabase.adapters.PokemonRecyclerViewAdapter;
import com.evanfuhr.pokemondatabase.data.MoveDAO;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.interfaces.MoveDataInterface;
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
public class MoveListFragment extends Fragment
        implements SearchView.OnQueryTextListener {

    private OnListFragmentInteractionListener mListener;

    Pokemon mPokemon = new Pokemon();
    Type mType = new Type();

    boolean isListByPokemon = false;
    boolean isListByType = false;

    RecyclerView mRecyclerView;
    TextView mTitle;
    private ProgressBar mProgressBar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MoveListFragment() {
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
        mTitle.setText(R.string.moves);
        mProgressBar = view.findViewById(R.id.progressBar);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(PokemonDisplayActivity.POKEMON_ID)) {
                mPokemon.setId(bundle.getInt(PokemonDisplayActivity.POKEMON_ID));
                isListByPokemon = true;
            } else if (bundle.containsKey(TypeDisplayActivity.TYPE_ID)) {
                mType.setId(bundle.getInt(TypeDisplayActivity.TYPE_ID));
                isListByType = true;
            }
        } else {
            Log.i("MoveListFragment Log", "No bundle");
            setHasOptionsMenu(true);
        }


        List<Move> moves = new ArrayList<>();

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        if (isListByPokemon) {
            mRecyclerView.setAdapter(new PokemonMoveRecyclerViewAdapter(moves, mListener));
        } else {
            mRecyclerView.setAdapter(new MoveRecyclerViewAdapter(moves, mListener));
        }

        new MoveLoader(getActivity()).execute("");
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
        MoveRecyclerViewAdapter adapter = (MoveRecyclerViewAdapter) mRecyclerView.getAdapter();
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
        void onListFragmentInteraction(Move move);
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
    private class MoveLoader extends AsyncTask<String, Void, List<Move>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        Context mContext;

        MoveLoader(Context context) {
            this.mContext = context;
        }

        @Override
        protected List<Move> doInBackground(String... strings) {

            MoveDAO moveDAO = new MoveDAO(mContext);
            List<Move> rawMoves;
            List<Move> typedMoves = new ArrayList<>();

            if (isListByPokemon) {
                rawMoves = moveDAO.getMoves(mPokemon);
            } else if (isListByType) {
                rawMoves = moveDAO.getMoves(mType);
            } else {
                rawMoves = moveDAO.getAllMoves();
            }
            moveDAO.close();

            for (Move move : rawMoves) {
                move = moveDAO.getMove(move);
                typedMoves.add(move);
            }

            return typedMoves;
        }

        @Override
        protected void onPostExecute(List<Move> moves) {
            super.onPostExecute(moves);

            if (isListByPokemon) {
                PokemonMoveRecyclerViewAdapter adapter = (PokemonMoveRecyclerViewAdapter) mRecyclerView.getAdapter();
                adapter.injectMoves(moves);
            } else {
                MoveRecyclerViewAdapter adapter = (MoveRecyclerViewAdapter) mRecyclerView.getAdapter();
                adapter.injectMoves(moves);
            }
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
