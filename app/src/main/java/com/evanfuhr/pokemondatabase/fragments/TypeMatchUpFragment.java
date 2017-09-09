package com.evanfuhr.pokemondatabase.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.data.TypeDAO;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.List;

public class TypeMatchUpFragment extends Fragment {

    Type _type;

    List<Type> _immunteTo;
    List<Type> _resistantTo;
    List<Type> _weakTo;

    private OnTypeMatchUpFragmentInteractionListener mListener;

    public TypeMatchUpFragment() {
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
        View matchUpFragmentView = inflater.inflate(R.layout.fragment_type_match_up, container, false);



        return matchUpFragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTypeMatchUpFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTypeMatchUpFragmentInteractionListener) {
            mListener = (OnTypeMatchUpFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTypeMatchUpFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setTypeMatchUps(Type type) {
        _type = type;
        setFragmentWeakTo();
    }

    void setFragmentWeakTo() {
        TypeDAO typeDAO = new TypeDAO(getActivity());
        _type.setWeakTo(typeDAO.getSingleTypeEfficacy(_type).getWeakTo());
    }

    public interface OnTypeMatchUpFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTypeMatchUpFragmentInteraction(Uri uri);
    }
}
