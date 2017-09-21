package com.evanfuhr.pokemondatabase.models;

import com.evanfuhr.pokemondatabase.data.TypeDAO;

import java.util.ArrayList;
import java.util.List;

public class Type {

    private List<Type> _attackingTypes;
    private String _color;
    private List<Type> _defendingTypes;
    private float _efficacy;
    private int _id;
    private String _name;
    private int _slot;

    public List<Type> get_attackingTypes() {
        return this._attackingTypes;
    }

    public void set_attackingTypes(List<Type> attackingTypes) {
        this._attackingTypes = attackingTypes;
    }

    public String getColor() {
        return this._color;
    }

    public void setColor(String color) {
        this._color = color;
    }

    public List<Type> get_defendingTypes() {
        return this._defendingTypes;
    }

    public void set_defendingTypes(List<Type> defendingTypes) {
        this._defendingTypes = defendingTypes;
    }

    public float getEfficacy() {
        return this._efficacy;
    }

    public void setEfficacy(float efficacy) {
        this._efficacy = efficacy;
    }

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
