package com.evanfuhr.pokemondatabase.activities.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.display.TypeDisplayActivity;
import com.evanfuhr.pokemondatabase.fragments.list.TypeListFragment;
import com.evanfuhr.pokemondatabase.models.Type;

import org.jetbrains.annotations.NonNls;

public class TypeListActivity extends AppCompatActivity
        implements TypeListFragment.OnListFragmentInteractionListener {

    @NonNls
    public static final String TYPE = "Types";
    @NonNls
    public static final String MENU_ITEM_NOT_IMPLEMENTED_YET = "Menu item not implemented yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_list);

        setTitle(TYPE);
    }

    @Override
    public void onListFragmentInteraction(Type type) {
        int type_id = type.getId();

        //Build the intent to load the player sheet
        Intent intent = new Intent(this, TypeDisplayActivity.class);
        //Load the hero ID to send to the player sheet
        intent.putExtra(TypeDisplayActivity.TYPE_ID, type_id);

        startActivity(intent);
    }
}
