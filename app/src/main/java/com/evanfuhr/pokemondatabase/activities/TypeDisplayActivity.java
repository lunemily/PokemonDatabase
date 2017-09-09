package com.evanfuhr.pokemondatabase.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.fragments.TypeMatchUpFragment;
import com.evanfuhr.pokemondatabase.interfaces.OnTypeSelectedListener;
import com.evanfuhr.pokemondatabase.models.Type;

public class TypeDisplayActivity extends AppCompatActivity
        implements OnTypeSelectedListener,
        TypeMatchUpFragment.OnTypeMatchUpFragmentInteractionListener {

    Type _type = new Type();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_display);

        TypeDAO typeDAO = new TypeDAO(this);

        //Get pokemon id passed to this activity
        Intent intent = getIntent();
        _type.setID(intent.getIntExtra(TypeListActivity.TYPE_ID, 0));
        _type = typeDAO.getTypeByID(_type);
        onTypeSelected(_type);
        setTitle(_type.getName());
    }

    @Override
    public void onTypeSelected(Type type) {

        setTypeBackgroundColor(type);

        FragmentManager fm = getFragmentManager();

        TypeMatchUpFragment typeMatchUpFragment = (TypeMatchUpFragment) fm.findFragmentById(R.id.typeMatchUpFragment);
        typeMatchUpFragment.setTypeMatchUps(type);
    }

    @Override
    public void onTypeMatchUpFragmentInteraction(Uri uri) {

    }

    private void setTypeBackgroundColor(Type type) {
        TypeDAO typeDAO = new TypeDAO(this);
        ConstraintLayout typeDisplayActivity = (ConstraintLayout) findViewById(R.id.type_display_activity);
        typeDisplayActivity.setBackgroundColor(Color.parseColor(typeDAO.getTypeByID(type).getColor()));
    }
}
