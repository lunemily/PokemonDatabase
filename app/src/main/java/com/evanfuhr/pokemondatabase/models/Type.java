package com.evanfuhr.pokemondatabase.models;

import com.evanfuhr.pokemondatabase.data.TypeDAO;

import java.util.ArrayList;
import java.util.List;

public class Type {

    private String _color;
    private int _id;
    private List<Type> _immuneTo;
    private List<Type> _ineffectiveAgainst;
    private String _name;
    private List<Type> _notVeryEffectiveAgainst;
    private List<Type> _resistantTo;
    private int _slot;
    private List<Type> _superEffectiveAgainst;
    private List<Type> _weakTo;

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

    public List<Type> getImmuneTo() {
        return _immuneTo;
    }

    public void setImmuneTo(List<Type> immuneTo) {
        this._immuneTo = immuneTo;
    }

    public List<Type> getIneffectiveAgainst() {
        return _ineffectiveAgainst;
    }

    public void setIneffectiveAgainst(List<Type> ineffectiveAgainst) {
        this._ineffectiveAgainst = ineffectiveAgainst;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public List<Type> getNotVeryEffectiveAgainst() {
        return _notVeryEffectiveAgainst;
    }

    public void setNotVeryEffectiveAgainst(List<Type> notVeryEffectiveAgainst) {
        this._notVeryEffectiveAgainst = notVeryEffectiveAgainst;
    }

    public List<Type> getResistantTo() {
        return this._resistantTo;
    }

    public void setResistantTo(List<Type> resistantTo) {
        this._resistantTo = resistantTo;
    }

    public int getSlot() {
        return this._slot;
    }

    public void setSlot(int slot) {
        this._slot = slot;
    }

    public List<Type> getSuperEffectiveAgainst() {
        return _superEffectiveAgainst;
    }

    public void setSuperEffectiveAgainst(List<Type> superEffectiveAgainst) {
        this._superEffectiveAgainst = superEffectiveAgainst;
    }

    public List<Type> getWeakTo() {
        return _weakTo;
    }

    public void setWeakTo(List<Type> weakTo) {
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
