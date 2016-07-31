package com.evanfuhr.pokemondatabase;

/**
 * Created by Evan on 30-Jul-16.
 */
public class Type {

    private int _id;
    private String _name;
    private int _slot;
    private String _color;

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

    public int getSlot() {
        return this._slot;
    }

    public void setSlot(int slot) {
        this._slot = slot;
    }

    public String getColor() {
        return this._color;
    }

    public void setColor(String color) {
        this._color = color;
    }
}
