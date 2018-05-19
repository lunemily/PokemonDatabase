package com.evanfuhr.pokemondatabase.models;

public class Encounter {
    private int mId;
    private LocationArea mLocationArea;
    private Pokemon mPokemon;
    private Version mVersion;

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public LocationArea getLocationArea() {
        return mLocationArea;
    }

    public void setLocationArea(LocationArea mLocationArea) {
        this.mLocationArea = mLocationArea;
    }

    public Pokemon getPokemon() {
        return mPokemon;
    }

    public void setPokemon(Pokemon mPokemon) {
        this.mPokemon = mPokemon;
    }

    public Version getVersion() {
        return mVersion;
    }

    public void setVersion(Version mVersion) {
        this.mVersion = mVersion;
    }
}
