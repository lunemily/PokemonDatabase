package com.evanfuhr.pokemondatabase.models;

import java.util.List;

public class Team {

    private String mName;
    private List<TeamPokemon> mPokemons;
    private String mRawTeam;

    public Team() {

    }

    public String getName() {
        if (mName == null) {
            mName = "undefined";
        }

        return this.mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public List<TeamPokemon> getPokemons() {
        return this.mPokemons;
    }

    public void setPokemons(List<TeamPokemon> pokemons) {
        this.mPokemons = pokemons;
    }

    public String getRawTeam() {
        return mRawTeam;
    }

    public void setRawTeam(String rawTeam) {
        this.mRawTeam = rawTeam;
    }
}
