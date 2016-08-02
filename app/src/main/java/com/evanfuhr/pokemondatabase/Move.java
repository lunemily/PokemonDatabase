package com.evanfuhr.pokemondatabase;

/**
 * Created by Evan on 01-Aug-16.
 */
public class Move {

    private int _id;
    private String _name;
    private int _type;

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int getType() {
        return this._type;
    }

    public void setType(int type_id) {
        this._type = type_id;
    }
}
