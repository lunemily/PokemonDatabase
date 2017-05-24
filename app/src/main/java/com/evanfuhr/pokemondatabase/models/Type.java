package com.evanfuhr.pokemondatabase.models;

import com.evanfuhr.pokemondatabase.data.TypeDAO;

import java.util.ArrayList;
import java.util.List;

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

    // Static methods

    public static List<Type> loadTypes(List<Type> types, TypeDAO typeDAO) {
        List<Type> newTypes = new ArrayList<>();
        for (Type t: types) {
            Type type = typeDAO.getTypeByID(t);
            newTypes.add(type);
        }
        return newTypes;
    }
}
