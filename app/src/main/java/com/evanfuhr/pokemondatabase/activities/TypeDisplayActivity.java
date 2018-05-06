package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.fragments.MoveListFragment;
import com.evanfuhr.pokemondatabase.fragments.PokemonListFragment;
import com.evanfuhr.pokemondatabase.fragments.TypeMatchUpFragment;
import com.evanfuhr.pokemondatabase.interfaces.OnTypeSelectedListener;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import org.jetbrains.annotations.NonNls;

public class TypeDisplayActivity extends AppCompatActivity
        implements OnTypeSelectedListener, PokemonListFragment.OnListFragmentInteractionListener,
        MoveListFragment.OnListFragmentInteractionListener, TypeMatchUpFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String TYPE_ID = "type_id";

    Type _type = new Type();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Get pokemon id passed to this activity
        Intent intent = getIntent();
        _type.setId(intent.getIntExtra(TYPE_ID, 0));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_display);

        TypeDAO typeDAO = new TypeDAO(this);
        _type = typeDAO.getTypeByID(_type);

        onTypeSelected(_type);
        setTitle(_type.getName());
    }

    @Override
    public void onTypeSelected(Type type) {
        setTypeBackgroundColor(type);
    }

    private void setTypeBackgroundColor(Type type) {
        TypeDAO typeDAO = new TypeDAO(this);
        RelativeLayout typeDisplayActivity = (RelativeLayout) findViewById(R.id.type_display_activity);
        typeDisplayActivity.setBackgroundColor(Color.parseColor(typeDAO.getTypeByID(type).getColor()));
    }

    @Override
    public void onListFragmentInteraction(Pokemon pokemon) {
        PokemonUtils.showLoadingToast(this);
        int pokemon_id = pokemon.getId();

        //Build the intent to load the pokemon display
        Intent intent = new Intent(this, PokemonDisplayActivity.class);
        //Load the pokemon ID to send to the player sheet
        intent.putExtra(PokemonDisplayActivity.POKEMON_ID, pokemon_id);

        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Move move) {
        PokemonUtils.showLoadingToast(this);
        // Build the intent to load the display
        Intent intent = new Intent(this, MoveDisplayActivity.class);
        // Add the id to send to the display activity
        intent.putExtra(MoveDisplayActivity.MOVE_ID, move.getId());

        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Type type) {
        PokemonUtils.showLoadingToast(this);
        // Build the intent to load the display
        Intent intent = new Intent(this, MoveDisplayActivity.class);
        // Add the id to send to the display activity
        intent.putExtra(TypeDisplayActivity.TYPE_ID, type.getId());

        startActivity(intent);
    }
}
