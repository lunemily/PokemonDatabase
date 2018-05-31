package com.evanfuhr.pokemondatabase.models;

public class Evolution {

    private Pokemon mBeforePokemon;
    private Pokemon mAfterPokemon;

    public Pokemon getBeforePokemon() {
        return mBeforePokemon;
    }

    public void setBeforePokemon(Pokemon beforePokemon) {
        this.mBeforePokemon = beforePokemon;
    }

    public Pokemon getAfterPokemon() {
        return mAfterPokemon;
    }

    public void setAfterPokemon(Pokemon afterPokemon) {
        this.mAfterPokemon = afterPokemon;
    }
}
