package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.activities.TypeDisplayActivity;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.List;

public class TypeListFragment extends Fragment {

    public static final String TYPE_ID = "type_id";

    LinearLayout _typeList;

    public TypeListFragment() {
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
        View typeListFragment = inflater.inflate(R.layout.fragment_type_list, container, false);

        _typeList = (LinearLayout) typeListFragment.findViewById(R.id.typeListLayout);

        generateTypeList();

        return typeListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void generateTypeList() {
        generateTypeList("%");
    }

    public void generateTypeList(String nameSearchParam) {
        TypeDAO typeDAO = new TypeDAO(getActivity());

        List<Type> types = typeDAO.getAllTypes(nameSearchParam);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 16, 0, 0);

        for (Type type : types) {

            // Create pokemon button
            final Button type_button = new Button(getActivity());
            type = typeDAO.getTypeByID(type);

            type_button.setLayoutParams(params);
            type_button.setText(type.getName());
            type_button.setId(type.getID());
            type_button.setBackgroundColor(Color.parseColor(type.getColor()));

            //Set click listener for the button
            type_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickButtonTypeDetails(view);
                }
            });

            // Add the type button to the list
            _typeList.addView(type_button);

            registerForContextMenu(type_button);
        }

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
