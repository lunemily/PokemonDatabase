package com.evanfuhr.pokemondatabase.models;

public class TeamPokemon extends BaseTypedObject {

    private Ability mAbility;
    private String mNickname;
    private boolean mShiny;
    private Item mItem;
    private Nature mNature;
    private int[] mIVs;
    private int[] mEVs;

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

    public int[] getIVs() {
        return mIVs;
    }

    public void setIVs(int[] ivs) {
        this.mIVs = ivs;
    }

    public int[] getEVs() {
        return mEVs;
    }

    public void setEVs(int[] evs) {
        this.mEVs = evs;
    }
}
