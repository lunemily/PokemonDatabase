package com.evanfuhr.pokemondatabase;

import java.util.List;

public class Pokemon {

    private int _id;
    private String _name;
    private List<Type> _types;

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getName() {
        if (_name == null) {
            //load name
        }
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
}
