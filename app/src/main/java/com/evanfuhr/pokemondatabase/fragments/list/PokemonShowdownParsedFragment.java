package com.evanfuhr.pokemondatabase.fragments.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.display.PokemonShowdownActivity;
import com.evanfuhr.pokemondatabase.data.PokemonDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Team;
import com.evanfuhr.pokemondatabase.models.TeamPokemon;
import com.evanfuhr.pokemondatabase.utils.PokemonShowdownParser;

import org.w3c.dom.Text;

import java.util.List;

public class PokemonShowdownParsedFragment extends Fragment {

    LinearLayout mParsedTeamsLayout;
    Button mEditPokemonTeams;
    List<Team> mParsedTeams;

    private OnFragmentInteractionListener mListener;

    public PokemonShowdownParsedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View detailsFragmentView = inflater.inflate(R.layout.fragment_pokemon_showdown_parsed, container, false);

        mParsedTeamsLayout = detailsFragmentView.findViewById(R.id.linear_layout_pokemon_teams);
        mEditPokemonTeams = detailsFragmentView.findViewById(R.id.edit_button_pokemon_teams);

        mEditPokemonTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEditButton();
            }
        });

        mParsedTeams = parseTeams();
        addTeamsToView();

        return detailsFragmentView;
    }

    public void onClickEditButton() {
        if (mListener != null) {
            mListener.onEditRawTeams();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public interface OnFragmentInteractionListener {
        void onEditRawTeams();

    }

    List<Team> parseTeams() {
        String rawTeams = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("RAWTEAMS", PokemonShowdownActivity.placeHolderRawTeams);
        PokemonShowdownParser parser = new PokemonShowdownParser(rawTeams);
        parser.parse();
        return parser.getTeams();
    }

    @SuppressLint("SetTextI18n")
    void addTeamsToView() {
        for (TeamPokemon teamPokemon : mParsedTeams.get(0).getPokemons()) {
            PokemonDAO pokemonDAO = new PokemonDAO(getContext());
            TypeDAO typeDAO = new TypeDAO(getContext());

            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.fragment_team_pokemon, mParsedTeamsLayout, false);

            final TextView mNickname = view.findViewById(R.id.text_view_team_pokemon_nickname);
            final TextView mName = view.findViewById(R.id.text_view_team_pokemon_name);
            final TextView mAbility = view.findViewById(R.id.text_view_ability);
            final TextView mNature = view.findViewById(R.id.text_view_nature);
            final TextView mMove1 = view.findViewById(R.id.text_view_team_pokemon_move_1);
            final TextView mMove2 = view.findViewById(R.id.text_view_team_pokemon_move_2);
            final TextView mMove3 = view.findViewById(R.id.text_view_team_pokemon_move_3);
            final TextView mMove4 = view.findViewById(R.id.text_view_team_pokemon_move_4);
            final TextView mType1 = view.findViewById(R.id.text_view_type_1);
            final TextView mType2 = view.findViewById(R.id.text_view_type_2);

            mNickname.setText(teamPokemon.getNickname());
            if (teamPokemon.getName().equals(teamPokemon.getNickname())) {
                mName.setVisibility(View.INVISIBLE);
            } else {
                mName.setText("'" + teamPokemon.getName() + "'");
            }
            mAbility.setText(teamPokemon.getAbility().getName());
            mNature.setText(teamPokemon.getNature().getName());
            mMove1.setText(teamPokemon.getMoves().get(0).getName());
            mMove2.setText(teamPokemon.getMoves().get(1).getName());
            mMove3.setText(teamPokemon.getMoves().get(2).getName());
            mMove4.setText(teamPokemon.getMoves().get(3).getName());

            Pokemon pokemon = pokemonDAO.getPokemon(teamPokemon.getName());
            teamPokemon.setTypes(typeDAO.getTypes(pokemon));
            mType1.setText(typeDAO.getType(teamPokemon.getTypes().get(0)).getName());
            if (teamPokemon.getTypes().size() == 2) {
                mType2.setText(typeDAO.getType(teamPokemon.getTypes().get(1)).getName());
            } else {
                mType2.setVisibility(View.INVISIBLE);
            }

            mParsedTeamsLayout.addView(view);
        }

    }
}
