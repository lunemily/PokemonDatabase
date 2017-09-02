package com.evanfuhr.pokemondatabase.models;

import java.util.List;

public class Pokemon {

    private List<Ability> _abilities;
    private int _base_experience;
    private List<EggGroup> _egg_groups;
    private int _gender_ratio;
    private Double _height;
    private int _id;
    private boolean _mega;
    private List<Move> _moves;
    private String _name;
    private List<Type> _types;
    private Double _weight;

    public Pokemon() {
    }

    public List<Ability> getAbilities() {
        return this._abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this._abilities = abilities;
    }

    public int getBaseExperience() {
        return this._base_experience;
    }

    public void setBaseExperience(int base_experience) {
        this._base_experience = base_experience;
    }

    public List<EggGroup> getEggGroups() {
        return this._egg_groups;
    }

    public void setEggGroups(List<EggGroup> egg_groups) {
        this._egg_groups = egg_groups;
    }

    public int getGenderRatio() {
        return this._gender_ratio;
    }

    public void setGenderRatio(int gender_ratio) {
        this._gender_ratio = gender_ratio;
    }

    public Double getHeight() {
        return this._height;
    }

    public void setHeight(Double height) {
        this._height = height;
    }

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public boolean getMega() {
        return this._mega;
    }

    public void setMega(boolean mega) {
        this._mega = mega;
    }

    public List<Move> getMoves() {
        return _moves;
    }

    public void setMoves(List<Move> moves) {
        this._moves = moves;
    }

    public String getName() {
        if (_name == null) {
            _name = "undefined";
        }

        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }
    
    public List<Type> getTypes() {
        return this._types;
    }
    
    public void setTypes(List<Type> types) {
        this._types = types;
    }

    public Double getWeight() {
        return this._weight;
    }

    public void setWeight(Double weight) {
        this._weight = weight;
    }

    //Not Getters and Setters

    public String getSpriteName() {
        String filename;
        filename = "" + this._id;

        if(this._id < 100) {
            if (this._id < 10) {
                filename = "0" + filename;
            }
            filename = "0" + filename;
        }

        if (_mega) {
            filename = filename + "mega";
        }

        filename = "sprite" + filename;

        return filename;
    }
}
