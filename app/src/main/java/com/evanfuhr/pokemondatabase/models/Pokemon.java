package com.evanfuhr.pokemondatabase.models;

import java.util.List;

public class Pokemon {

    private List<Ability> mAbilities;
    private int mBaseExperience;
    private List<EggGroup> mEggGroups;
    private int mGenderRatio;
    private String mGenus;
    private Double mHeight;
    private int mId;
    private boolean mMega;
    private List<Move> mMoves;
    private String mName;
    private List<Type> mTypes;
    private Double mWeight;

    public Pokemon() {
    }

    public Pokemon(int id) {
        this.mId = id;
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

    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public boolean getMega() {
        return this.mMega;
    }

    public void setMega(boolean mega) {
        this.mMega = mega;
    }

    public List<Move> getMoves() {
        return mMoves;
    }

    public void setMoves(List<Move> moves) {
        this.mMoves = moves;
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
    
    public List<Type> getTypes() {
        return this.mTypes;
    }
    
    public void setTypes(List<Type> types) {
        this.mTypes = types;
    }

    public Double getWeight() {
        return this.mWeight;
    }

    public void setWeight(Double weight) {
        this.mWeight = weight;
    }

    //Not Getters and Setters

    public String getSpriteName() {
        String filename;
        filename = "" + this.mId;

        if(this.mId < 100) {
            if (this.mId < 10) {
                filename = "0" + filename;
            }
            filename = "0" + filename;
        }

        if (mMega) {
            filename = filename + "mega";
        }

        filename = "sprite" + filename;

        return filename;
    }
}
