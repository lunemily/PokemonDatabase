package com.evanfuhr.pokemondatabase.models;

public class EggGroup {

    private int mId;
    private String _name;

    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
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
