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
import com.evanfuhr.pokemondatabase.activities.MoveDisplayActivity;
import com.evanfuhr.pokemondatabase.activities.TypeDisplayActivity;
import com.evanfuhr.pokemondatabase.data.MoveDAO;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.DamageClass;
import com.evanfuhr.pokemondatabase.utils.PokemonUtils;

public class MoveDetailsFragment extends Fragment {

    public static final String TYPE_ID = "type_id";

    Move mMove = new Move();

    TextView mAccuracy;
    TextView mCategory;
    TextView mEffect;
    TextView mPower;
    TextView mPp;
    Button mType;

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

        mAccuracy = detailsFragmentView.findViewById(R.id.moveAccuracyValue);
        mCategory = detailsFragmentView.findViewById(R.id.moveCategoryValue);
        mEffect = detailsFragmentView.findViewById(R.id.moveEffectValue);
        mPower = detailsFragmentView.findViewById(R.id.movePowerValue);
        mPp = detailsFragmentView.findViewById(R.id.movePPValue);
        mType = detailsFragmentView.findViewById(R.id.buttonMoveType);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            if(bundle.containsKey(MoveDisplayActivity.MOVE_ID)) {
                mMove.setId(bundle.getInt(MoveDisplayActivity.MOVE_ID));
                setMoveDetails();
            }
        } else {
            Log.i("MoveDetailsFragment Log", "No bundle");
        }

        PokemonUtils.transitionToast.cancel();
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

        mMove = moveDAO.getMove(mMove);
        mMove.setType(typeDAO.getType(mMove.getType()));

        moveDAO.close();
        typeDAO.close();
    }

    void setFragmentAccuracy() {
        mAccuracy.setText(Integer.toString(mMove.getAccuracy()) + "%");
    }

    void setFragmentCategory() {
        mCategory.setText(DamageClass.getName(mMove.getCategory()));
    }

    void setFragmentEffect() {
        String effect = mMove.getEffect();
        effect = PokemonUtils.replaceProseLinks(getActivity(), effect, mMove.getId());
        mEffect.setText(effect);
    }

    void setFragmentPower() {
        mPower.setText(Integer.toString(mMove.getPower()));
    }

    void setFragmentPP() {
        mPp.setText(Integer.toString(mMove.getPP()));
    }

    void setFragmentType() {
        mType.setText(mMove.getType().getName());
        mType.setId(mMove.getType().getId());
        mType.setBackgroundColor(Color.parseColor(mMove.getType().getColor()));
        mType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButtonTypeDetails(view);
            }
        });
    }

    private void onClickButtonTypeDetails(View view) {
        PokemonUtils.showLoadingToast(getActivity());
        int type_id = view.getId();

        //Build the intent to load the player sheet
        Intent intent = new Intent(getActivity(), TypeDisplayActivity.class);
        //Load the hero ID to send to the player sheet
        intent.putExtra(TYPE_ID, type_id);

        startActivity(intent);
    }
}
