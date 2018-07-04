package com.evanfuhr.pokemondatabase.models;

import java.util.List;

public class Pokemon extends BasePokemon {

    private List<Ability> mAbilities;
    private int mBaseExperience;
    private List<EggGroup> mEggGroups;
    private int mGenderRatio;
    private String mGenus;
    private Double mHeight;
    private List<Move> mMoves;
    private Double mWeight;

    public Pokemon() {
        super();
    }

    public Pokemon(int id) {
        super(id);
    }

    public List<Ability> getAbilities() {
        return this.mAbilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.mAbilities = abilities;
    }

    public int getBaseExperience() {
        return this.mBaseExperience;
    }

    public void setBaseExperience(int base_experience) {
        this.mBaseExperience = base_experience;
    }

    public List<EggGroup> getEggGroups() {
        return this.mEggGroups;
    }

    public void setEggGroups(List<EggGroup> egg_groups) {
        this.mEggGroups = egg_groups;
    }

    public int getGenderRatio() {
        return this.mGenderRatio;
    }

    public void setGenderRatio(int gender_ratio) {
        this.mGenderRatio = gender_ratio;
    }

    public String getGenus() {
        return this.mGenus;
    }

    public void setGenus(String genus) {
        this.mGenus = "The " + genus;
    }

    public Double getHeight() {
        return this.mHeight;
    }

    public void setHeight(Double height) {
        this.mHeight = height;
    }

    public List<Move> getMoves() {
        return mMoves;
    }

    public void setMoves(List<Move> moves) {
        this.mMoves = moves;
    }

    public Double getWeight() {
        return this.mWeight;
    }

    public void setWeight(Double weight) {
        this.mWeight = weight;
    }

    // Not Getters and Setters

    public String getThreeDigitStringId() {
        String id;
        id = "" + this.mId;

        if (this.mId < 100) {
            if (this.mId < 10) {
                id = "0" + id;
            }
            id = "0" + id;
        }
        return id;
    }

    public String getSpriteName() {
        String filename = this.getThreeDigitStringId();

        if (mForme == Forme.MEGA) {
            filename = filename + "mega";
        }

        filename = "sprite" + filename;

        return filename;
    }
}
