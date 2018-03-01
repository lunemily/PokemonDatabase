package com.evanfuhr.pokemondatabase.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.NatureDAO;
import com.evanfuhr.pokemondatabase.fragments.NatureDetailsFragment;
import com.evanfuhr.pokemondatabase.interfaces.OnNatureSelectedListener;
import com.evanfuhr.pokemondatabase.models.Nature;

import org.jetbrains.annotations.NonNls;

public class NatureDisplayActivity extends AppCompatActivity
        implements OnNatureSelectedListener {

    @NonNls
    public static final String NATURE_ID = "nature_id";

    Nature mNature = new Nature();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature_display);

        NatureDAO natureDAO = new NatureDAO(this);

        //Get pokemon id passed to this activity
        Intent intent = getIntent();
        mNature.setId(intent.getIntExtra(NATURE_ID, 0));
        mNature = natureDAO.getNatureById(mNature);
        onNatureSelected(mNature);
        setTitle(mNature.getName());

        natureDAO.close();
    }

    @Override
    public void onNatureSelected(Nature nature) {

        //setMoveBackgroundColor(nature);

        FragmentManager fm = getFragmentManager();

        NatureDetailsFragment natureDetailsFragment = (NatureDetailsFragment) fm.findFragmentById(R.id.natureDetailsFragment);
        natureDetailsFragment.setNatureDetails(nature);

    }

}
