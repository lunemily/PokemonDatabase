package com.evanfuhr.pokemondatabase.fragments.display;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.display.PokemonShowdownActivity;
import com.evanfuhr.pokemondatabase.fragments.list.PokemonShowdownParsedFragment;

public class PokemonShowdownRawFragment extends Fragment {

    EditText mRawTeams;
    Button mSavePokemonTeams;
    private PokemonShowdownRawFragment.OnFragmentInteractionListener mListener;

    public PokemonShowdownRawFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View detailsFragmentView = inflater.inflate(R.layout.fragment_pokemon_showdown_raw, container, false);

        mRawTeams = detailsFragmentView.findViewById(R.id.text_area_raw_teams);
        mSavePokemonTeams = detailsFragmentView.findViewById(R.id.save_button_raw_teams);

        setRawTeams();

        mSavePokemonTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSaveButton();
            }
        });

        return detailsFragmentView;
    }

    private void setRawTeams() {
        String rawTeams = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("RAWTEAMS", PokemonShowdownActivity.placeHolderRawTeams);
        mRawTeams.setText(rawTeams);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PokemonShowdownParsedFragment.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected void onClickSaveButton() {
        String rawTeams = String.valueOf(mRawTeams.getText());
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("RAWTEAMS", rawTeams).apply();
        if (mListener != null) {
            mListener.onSaveRawTeams();
        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSaveRawTeams();
    }
}
