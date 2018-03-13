package com.evanfuhr.pokemondatabase.activities;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.AbilityDetailsFragment;
import com.evanfuhr.pokemondatabase.fragments.MoveDetailsFragment;
import com.evanfuhr.pokemondatabase.interfaces.OnAbilitySelectedListener;
import com.evanfuhr.pokemondatabase.models.Ability;

public class AbilityDisplayActivity extends AppCompatActivity
        implements OnAbilitySelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability_display);
    }

    @Override
    public void onAbilitySelected(Ability ability) {

        FragmentManager fm = getFragmentManager();

        AbilityDetailsFragment moveDetailsFragment = (AbilityDetailsFragment) fm.findFragmentById(R.id.abilityDetailsFragment);
        moveDetailsFragment.setAbilityDetails(ability);
    }
}
