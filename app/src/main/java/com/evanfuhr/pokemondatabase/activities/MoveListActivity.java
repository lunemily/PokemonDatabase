package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.MoveListFragment;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import org.jetbrains.annotations.NonNls;

public class MoveListActivity extends AppCompatActivity
        implements MoveListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String MOVE_ID = "move_id";
    @NonNls
    public static final String MOVE = "Type";
    @NonNls
    public static final String MENU_ITEM_NOT_IMPLEMENTED_YET = "Menu item not implemented yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_list);

        setTitle(MOVE);
    }

    @Override
    public void onListFragmentInteraction(Move move) {
        PokemonUtils.showLoadingToast(this);
        int move_id = move.getId();

        //Build the intent to load the player sheet
        Intent intent = new Intent(this, MoveDisplayActivity.class);
        //Load the hero ID to send to the player sheet
        intent.putExtra(MOVE_ID, move_id);

        startActivity(intent);
    }
}
