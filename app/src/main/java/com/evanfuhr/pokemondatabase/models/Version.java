package com.evanfuhr.pokemondatabase.models;

public class Version {

    private int _id;
    private int _generation_id;
    private int _group_id;
    private String _name;

    public Version() {

    }

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getGenerationID() {
        return this._generation_id;
    }

    public void setGenerationID(int generation_id) {
        this._generation_id = generation_id;
    }

    public int getGroupID() {
        return this._group_id;
    }

    public void setGroupID(int group_id) {
        this._group_id = group_id;
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

}
