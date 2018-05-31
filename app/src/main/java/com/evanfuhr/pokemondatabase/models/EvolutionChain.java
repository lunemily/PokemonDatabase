package com.evanfuhr.pokemondatabase.models;

import java.util.List;

public class EvolutionChain {

    private int mId;
    private List<Evolution> mEvolutions;
    private List<Pokemon> mPokemon;

    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public List<Evolution> getEvolution() {
        return mEvolutions;
    }

    public void setEvolution(List<Evolution> mEvolutions) {
        this.mEvolutions = mEvolutions;
    }

    public List<Pokemon> getPokemon() {
        return mPokemon;
    }

    public void setPokemon(List<Pokemon> mPokemon) {
        this.mPokemon = mPokemon;
    }
}
