package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.AbilityDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.MoveDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.TypeDisplayActivity;
import com.evanfuhr.pokemondatabase.data.MoveDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.DamageClass;

public class MoveDetailsFragment extends Fragment {

    public static final String TYPE_ID = "type_id";

    Move mMove = new Move();

    TextView _accuracy;
    TextView _category;
    TextView _effect;
    TextView _power;
    TextView _pp;
    Button _type;

    public MoveDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View detailsFragmentView = inflater.inflate(R.layout.fragment_move_details, container, false);

        _accuracy = detailsFragmentView.findViewById(R.id.moveAccuracyValue);
        _category = detailsFragmentView.findViewById(R.id.moveCategoryValue);
        _effect = detailsFragmentView.findViewById(R.id.moveEffectValue);
        _power = detailsFragmentView.findViewById(R.id.movePowerValue);
        _pp = detailsFragmentView.findViewById(R.id.movePPValue);
        _type = detailsFragmentView.findViewById(R.id.buttonMoveType);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if(bundle.containsKey(MoveDisplayActivity.MOVE_ID)) {
                mMove.setId(bundle.getInt(MoveDisplayActivity.MOVE_ID));
                setMoveDetails();
            }
        } else {
            Log.i("MoveDetailsFragment Log", "No bundle");
        }

        return detailsFragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    void setMoveDetails() {
        loadMove();
        setFragmentAccuracy();
        setFragmentCategory();
        setFragmentEffect();
        setFragmentPower();
        setFragmentPP();
        setFragmentType();
    }

    void loadMove() {
        MoveDAO moveDAO = new MoveDAO(getActivity());
        TypeDAO typeDAO = new TypeDAO(getActivity());

        mMove = moveDAO.getMoveByID(mMove);
        mMove.setType(typeDAO.getTypeByID(mMove.getType()));

        moveDAO.close();
        typeDAO.close();
    }

    void setFragmentAccuracy() {
        _accuracy.setText(Integer.toString(mMove.getAccuracy()) + "%");
    }

    void setFragmentCategory() {
        _category.setText(DamageClass.getName(mMove.getCategory()));
    }

    void setFragmentEffect() {
        _effect.setText(mMove.getEffect());
    }

    void setFragmentPower() {
        _power.setText(Integer.toString(mMove.getPower()));
    }

    void setFragmentPP() {
        _pp.setText(Integer.toString(mMove.getPP()));
    }

    void setFragmentType() {
        _type.setText(mMove.getType().getName());
        _type.setId(mMove.getType().getId());
        _type.setBackgroundColor(Color.parseColor(mMove.getType().getColor()));
        _type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButtonTypeDetails(view);
            }
        });
    }

    private void onClickButtonTypeDetails(View view) {
        int type_id = view.getId();

        //Build the intent to load the player sheet
        Intent intent = new Intent(getActivity(), TypeDisplayActivity.class);
        //Load the hero ID to send to the player sheet
        intent.putExtra(TYPE_ID, type_id);

        startActivity(intent);
    }
}
