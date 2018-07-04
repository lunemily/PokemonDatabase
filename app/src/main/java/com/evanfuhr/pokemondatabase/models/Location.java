package com.evanfuhr.pokemondatabase.models;

import java.util.List;

public class Location extends BaseNamedObject {

    private List<Pokemon> mPokemon;
    private Region mRegion;

    public Location() {
        super();
    }

    public Location(int id) {
        super(id);
    }

    public List<Pokemon> getPokemon() {
        return mPokemon;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.mPokemon = pokemon;
    }

    public Region getRegion() {
        return mRegion;
    }

    public void setRegion(Region mRegion) {
        this.mRegion = mRegion;
    }
}
