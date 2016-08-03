package com.evanfuhr.pokemondatabase;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.List;

public class PokemonDetailsActivity extends AppCompatActivity
        implements OnPokemonSelectedListener {

    int _pokemon_id;
    Pokemon _pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        //Get hero id passed to this activity
        Intent intent = getIntent();
        //_pokemon_id = intent.getIntExtra(MainActivity.POKEMON_ID, 0);
        _pokemon.setID(intent.getIntExtra(MainActivity.POKEMON_ID, 0));
        //onPokemonSelected(_pokemon_id);
        onPokemonSelected(_pokemon);
    }

    @Override
    //public void onPokemonSelected(int pokemon_id) {
    public void onPokemonSelected(Pokemon pokemon) {
//        FragmentManager fm = getFragmentManager();
//        HeroTraitsFragment heroTraitsFragment =
//                (HeroTraitsFragment) fm.findFragmentById(R.id.heroTraitsFragment);
//        heroTraitsFragment.setHero(_pokemon_id);

        //setPokemonBackgroundColor(pokemon_id);
        setPokemonBackgroundColor(pokemon);

    }

    //private void setPokemonBackgroundColor(int pokemon_id) {
    private void setPokemonBackgroundColor(Pokemon pokemon) {
        DataBaseHelper db = new DataBaseHelper(this);

        //Create base background
        List<Type> types = db.getTypesForPokemon(pokemon.getID());
        int[] colors = {0, 0};
        if (types.size() == 1) {
            colors[0] = Color.parseColor(types.get(0).getColor());
            colors[1] = Color.parseColor(types.get(0).getColor());
        }
        else {
            colors[0] = Color.parseColor(types.get(0).getColor());
            colors[1] = Color.parseColor(types.get(1).getColor());
        }
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, colors);

        RelativeLayout pokemonDetailsActivity = (RelativeLayout) findViewById(R.id.pokemon_details_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            pokemonDetailsActivity.setBackground(gd);
        }
    }

    // Unfinished
    //public void generateMoveList(int pokemon_id) {
    public void generateMoveList(Pokemon pokemon) {
        DataBaseHelper db = new DataBaseHelper(this);

        List<Move> moves = db.getAllMovesForPokemonByGame(pokemon.getID());
        List<Move> levelMoves = Move.getLevelUpMoves(moves);
        List<Move> eggMoves = Move.getEggMoves(moves);
        List<Move> tutorMoves = Move.getTutorMoves(moves);
        List<Move> machineMoves = Move.getMachineMoves(moves);

    }
}
