package com.evanfuhr.pokemondatabase.activities;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.NatureDAO;
import com.evanfuhr.pokemondatabase.models.Nature;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

import org.jetbrains.annotations.NonNls;

public class NatureDisplayActivity extends AppCompatActivity {

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
        setNatureBackgroundColor(mNature);
        setTitle(mNature.getName());

        natureDAO.close();
    }

    private void setNatureBackgroundColor(Nature nature) {
        NatureDAO natureDAO = new NatureDAO(this);

        //Create base background
        nature = natureDAO.getNatureById(nature);
        GradientDrawable gd = PokemonUtils.getColorGradientByFlavors(natureDAO.getNatureById(nature).getFlavors());

        RelativeLayout natureDisplayActivity = (RelativeLayout) findViewById(R.id.nature_display_activity);
        natureDisplayActivity.setBackground(gd);

        natureDAO.close();
    }

}
