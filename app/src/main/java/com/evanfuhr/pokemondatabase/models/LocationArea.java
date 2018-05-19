package com.evanfuhr.pokemondatabase.models;

import java.util.List;

public class LocationArea {
    private int mId;
    private String mName;
    private List<Pokemon> mPokemon;

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public List<Pokemon> getPokemon() {
        return mPokemon;
    }

    public void setPokemon(List<Pokemon> mPokemon) {
        this.mPokemon = mPokemon;
    }
}
