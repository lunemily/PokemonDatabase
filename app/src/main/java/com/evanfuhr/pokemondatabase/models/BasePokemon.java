package com.evanfuhr.pokemondatabase.models;

public class BasePokemon extends BaseTypedObject {

    Forme mForme;

    public BasePokemon() {
        super();
    }

    public BasePokemon(int id) {
        super(id);
    }

    public Forme getForme() {
        return this.mForme;
    }

    public void setForme(Forme forme) {
        this.mForme = forme;
    }
}
