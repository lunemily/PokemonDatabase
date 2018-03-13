package com.evanfuhr.pokemondatabase.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.MoveDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.fragments.MoveDetailsFragment;
import com.evanfuhr.pokemondatabase.interfaces.OnMoveSelectedListener;
import com.evanfuhr.pokemondatabase.models.Move;

import org.jetbrains.annotations.NonNls;

public class MoveDisplayActivity extends AppCompatActivity
        implements OnMoveSelectedListener {

    @NonNls
    public static final String MOVE_ID = "move_id";

    Move _move = new Move();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_display);

        MoveDAO moveDAO = new MoveDAO(this);

        //Get pokemon id passed to this activity
        Intent intent = getIntent();
        _move.setID(intent.getIntExtra(MOVE_ID, 0));
        _move = moveDAO.getMoveByID(_move);
        onMoveSelected(_move);
        setTitle(_move.getName());

        moveDAO.close();
    }

    @Override
    public void onMoveSelected(Move move) {

        setMoveBackgroundColor(move);

        FragmentManager fm = getFragmentManager();

        MoveDetailsFragment moveDetailsFragment = (MoveDetailsFragment) fm.findFragmentById(R.id.moveDetailsFragment);
        moveDetailsFragment.setMoveDetails(move);

    }

    private void setMoveBackgroundColor(Move move) {
        TypeDAO typeDAO = new TypeDAO(this);

        RelativeLayout moveDisplayActivity = findViewById(R.id.move_display_activity);

        moveDisplayActivity.setBackgroundColor(Color.parseColor(typeDAO.getTypeByID(move.getType()).getColor()));
    }
}
