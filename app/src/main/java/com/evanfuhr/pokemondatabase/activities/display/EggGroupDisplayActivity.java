package com.evanfuhr.pokemondatabase.activities.display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.EggGroupDAO;
import com.evanfuhr.pokemondatabase.fragments.list.PokemonListFragment;
import com.evanfuhr.pokemondatabase.models.EggGroup;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import org.jetbrains.annotations.NonNls;

public class EggGroupDisplayActivity extends AppCompatActivity
        implements PokemonListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String EGG_GROUP_ID = "egg_group_id";

    EggGroup mEggGroup = new EggGroup();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_group_display);

        EggGroupDAO eggGroupDAO = new EggGroupDAO(this);

        // Get eggGroup_id passed to this activity
        Intent intent = getIntent();
        mEggGroup.setId(intent.getIntExtra(EGG_GROUP_ID, 0));
        mEggGroup = eggGroupDAO.getEggGroup(mEggGroup);
        setTitle(mEggGroup.getName());

        eggGroupDAO.close();
    }

    @Override
    public void onPokemonListFragmentInteraction(Pokemon pokemon) {

        //Build the intent to load the pokemon display
        Intent intent = new Intent(this, PokemonDisplayActivity.class);
        //Load the pokemon ID to send to the player sheet
        intent.putExtra(PokemonDisplayActivity.POKEMON_ID, pokemon.getId());

        startActivity(intent);
    }
}
