package com.evanfuhr.pokemondatabase.activities.display;

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
        mNature = natureDAO.getNature(mNature);
        setNatureBackgroundColor(mNature);
        setTitle(mNature.getName());

        natureDAO.close();
    }

    private void setNatureBackgroundColor(Nature nature) {
        NatureDAO natureDAO = new NatureDAO(this);

        //Create base background
        nature = natureDAO.getNature(nature);
        GradientDrawable gd = PokemonUtils.getColorGradientByFlavors(natureDAO.getNature(nature).getFlavors());

        RelativeLayout natureDisplayActivity = findViewById(R.id.nature_display_activity);
        natureDisplayActivity.setBackground(gd);

        natureDAO.close();
    }

}
