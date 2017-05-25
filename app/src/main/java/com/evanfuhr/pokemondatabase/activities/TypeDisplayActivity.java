package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.interfaces.OnTypeSelectedListener;
import com.evanfuhr.pokemondatabase.models.Type;

public class TypeDisplayActivity extends AppCompatActivity
        implements OnTypeSelectedListener {

    Type _type = new Type();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_display);

        TypeDAO typeDAO = new TypeDAO(this);

        //Get pokemon id passed to this activity
        Intent intent = getIntent();
        _type.setID(intent.getIntExtra(PokemonListActivity.POKEMON_ID, 0));
        _type = typeDAO.getTypeByID(_type);
        onTypeSelected(_type);
        setTitle(_type.getName());
        //generateMovesCards();
    }

    @Override
    public void onTypeSelected(Type type) {

    }
}
