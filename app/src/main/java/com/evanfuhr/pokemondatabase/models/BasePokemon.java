package com.evanfuhr.pokemondatabase.models;

public class BasePokemon extends BaseTypedObject {

    private Forme mForme;
    int mSpeciesId;

    BasePokemon() {
        super();
    }

    BasePokemon(int id) {
        super(id);
    }

    public Forme getForme() {
        return this.mForme;
    }

    public void setForme(Forme forme) {
        this.mForme = forme;
    }

    public int getSpecies() {
        return mSpeciesId;
    }

    public void setSpecies(int speciesId) {
        this.mSpeciesId = speciesId;
    }
}
