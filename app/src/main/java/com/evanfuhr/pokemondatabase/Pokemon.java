package com.evanfuhr.pokemondatabase;

/**
 * Created by Evan on 30-Jul-16.
 */
public class Pokemon {

    private int _id;
    private String _name;
    private int _type_1;
    private int _type_2;

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

    public int getType1() {
        return this._type_1;
    }

    public void setType1(int type_id) {
        this._type_1 = type_id;
    }

    public int getType2() {
        return this._type_2;
    }

    public void setType2(int type_id) {
        this._type_2 = type_id;
    }
}
