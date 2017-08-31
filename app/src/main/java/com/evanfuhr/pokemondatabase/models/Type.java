package com.evanfuhr.pokemondatabase.models;

import com.evanfuhr.pokemondatabase.data.TypeDAO;

import java.util.ArrayList;
import java.util.List;

public class Type {

    private String _color;
    private int _id;
    private int[] _immuneTo;
    private String _name;
    private int[] _resistantTo;
    private int _slot;
    private int[] _weakTo;

    //TODO: Need to enumerate type colors separately from db
    public String getColor() {
        return this._color;
    }

    public void setColor(String color) {
        this._color = color;
    }

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int[] getImmuneTo() {
        return _immuneTo;
    }

    public void setImmuneTo(int[] immuneTo) {
        this._immuneTo = immuneTo;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int[] getResistantTo() {
        return this._resistantTo;
    }

    public void setResistantTo(int[] resistantTo) {
        this._resistantTo = resistantTo;
    }

    public int getSlot() {
        return this._slot;
    }

    public void setSlot(int slot) {
        this._slot = slot;
    }

    public int[] getWeakTo() {
        return _weakTo;
    }

    public void setWeakTo(int[] weakTo) {
        this._weakTo = weakTo;
    }

    // Static methods

    public static List<Type> loadTypesForPokemon(List<Type> types, TypeDAO typeDAO) {
        List<Type> newTypes = new ArrayList<>();
        for (Type t: types) {
            Type type = typeDAO.getTypeByID(t);
            newTypes.add(type);
        }
        return newTypes;
    }
}
