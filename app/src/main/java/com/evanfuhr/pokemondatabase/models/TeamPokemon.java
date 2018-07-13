package com.evanfuhr.pokemondatabase.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeamPokemon extends BaseTypedObject {

    private Ability mAbility;
    private Forme mForme;
    private Gender mGender;
    private String mNickname;
    private boolean mShiny = false;
    private Item mItem;
    private List<Move> mMoves;
    private Nature mNature;
    private Map<IndividualValues, Integer> mIVs;
    private Map<EffortValue, Integer> mEVs;
    private String mRawPokemon;

    public TeamPokemon() {
        super();
    }

    public TeamPokemon(int id) {
        super(id);
    }

    public Ability getAbility() {
        return mAbility;
    }

    public void setAbility(Ability ability) {
        this.mAbility = ability;
    }

    public String getNickname() {
        return mNickname;
    }

    public void setNickname(String nickname) {
        this.mNickname = nickname;
    }

    public boolean isShiny() {
        return mShiny;
    }

    public void setShiny(boolean shiny) {
        this.mShiny = shiny;
    }

    public Item getItem() {
        return mItem;
    }

    public void setItem(Item item) {
        this.mItem = item;
    }

    public Nature getNature() {
        return mNature;
    }

    public void setNature(Nature nature) {
        this.mNature = nature;
    }

    public Map<IndividualValues, Integer> getIVs() {
        return mIVs;
    }

    public void setIVs(Map<IndividualValues, Integer> ivs) {
        this.mIVs = ivs;
    }

    public Map<EffortValue, Integer> getEVs() {
        return mEVs;
    }

    public void setEVs(Map<EffortValue, Integer> evs) {
        this.mEVs = evs;
    }

    public String getRawPokemon() {
        return mRawPokemon;
    }

    public void setRawPokemon(String rawPokemon) {
        this.mRawPokemon = rawPokemon;
    }

    public List<Move> getMoves() {
        return mMoves != null ? mMoves : new ArrayList<Move>();
    }

    public void setMoves(List<Move> moves) {
        this.mMoves = moves;
    }

    public Gender getGender() {
        return mGender;
    }

    public void setGender(Gender gender) {
        this.mGender = gender;
    }

    public Forme getForme() {
        return mForme;
    }

    public void setForme(Forme forme) {
        this.mForme = forme;
    }
}
