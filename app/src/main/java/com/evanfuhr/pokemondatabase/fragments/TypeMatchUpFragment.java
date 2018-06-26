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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.PokemonDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.TypeDisplayActivity;
import com.evanfuhr.pokemondatabase.adapters.TypeEfficacyRecyclerViewAdapter;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.List;

public class TypeMatchUpFragment extends Fragment {

    private OnTypeMatchUpListFragmentInteractionListener mListener;

    public static final String TYPE_ID = "type_id";

    Pokemon mPokemon = new Pokemon();
    Type mType = new Type();

    boolean isListByPokemon = false;

    RecyclerView mRecyclerView;
    Button mToggle;
    private ProgressBar mProgressBar;

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

        mToggle = view.findViewById(R.id.card_list_button);
        mToggle.setText("Type Match Ups");
        mProgressBar = view.findViewById(R.id.progressBar);
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setNestedScrollingEnabled(false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(PokemonDisplayActivity.POKEMON_ID)) {
                mPokemon.setId(bundle.getInt(PokemonDisplayActivity.POKEMON_ID));
                isListByPokemon = true;
                mToggle.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            } else if (bundle.containsKey(TypeDisplayActivity.TYPE_ID)) { // TODO: Turn into try-catch
                mType.setId(bundle.getInt(TypeDisplayActivity.TYPE_ID));
                mToggle.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        } else {
            Log.i("TypeMatchUpFragment Log", "No bundle");
        }
        List<Type> types = new ArrayList<>();

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new TypeEfficacyRecyclerViewAdapter(types, mListener));

        new TypeMatchUpLoader(getActivity()).execute("");

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
        if (context instanceof OnTypeMatchUpListFragmentInteractionListener) {
            mListener = (OnTypeMatchUpListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTypeMatchUpListFragmentInteractionListener");
        }
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
    public interface OnTypeMatchUpListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Type type);
    }

    @SuppressLint("StaticFieldLeak")
    private class TypeMatchUpLoader extends AsyncTask<String, Void, List<Type>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        Context mContext;

        TypeMatchUpLoader(Context context) {
            this.mContext = context;
        }

        @Override
        protected List<Type> doInBackground(String... strings) {

            TypeDAO typeDAO = new TypeDAO(mContext);
            List<Type> types;

            if (isListByPokemon) {
                // Rather than iterating over ALL types, just get the mPokemon's types and load
                List<Type> pokemonTypes = typeDAO.getTypes(mPokemon);

                // Check for single or dual type mPokemon
                if (pokemonTypes.size() == 1) {
                    types = typeDAO.getSingleTypeEfficacy(pokemonTypes.get(0), true);
                } else {
                    types = typeDAO.getDualTypeEfficacy(pokemonTypes.get(0), pokemonTypes.get(1));
                }
            } else {
                types = typeDAO.getSingleTypeEfficacy(mType);
            }
            typeDAO.close();

            return types;
        }

        @Override
        protected void onPostExecute(List<Type> moves) {
            super.onPostExecute(moves);

            TypeEfficacyRecyclerViewAdapter adapter = (TypeEfficacyRecyclerViewAdapter) mRecyclerView.getAdapter();
            adapter.injectTypes(moves);
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
