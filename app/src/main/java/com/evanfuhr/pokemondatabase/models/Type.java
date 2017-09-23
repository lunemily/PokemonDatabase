package com.evanfuhr.pokemondatabase.models;

import android.support.annotation.Nullable;

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

    /*
    Takes in both types for a given pokemon
     */
    public static List<Type> combineDualTypeEfficacies(List<Type> types) {
        List<Type> combinedEfficaciesTypes = new ArrayList<>();

        for (Type pokemonType : types) {
            for (Type pokemonTypeWithEfficacy : pokemonType.get_defendingTypes()) {
                if (!alreadyContainsType(combinedEfficaciesTypes, pokemonTypeWithEfficacy.getID())) {
                    combinedEfficaciesTypes.add(pokemonTypeWithEfficacy);
                } else {
                    // Store first occurrence
                    Type firstOccurrence = new Type();
                    firstOccurrence.setID(getTypeInListByID(combinedEfficaciesTypes, pokemonTypeWithEfficacy.getID()).getID());
                    firstOccurrence.setEfficacy(getTypeInListByID(combinedEfficaciesTypes, pokemonTypeWithEfficacy.getID()).getEfficacy());

                    // Remove from dual type list
                    combinedEfficaciesTypes.remove(getTypeInListByID(combinedEfficaciesTypes, pokemonTypeWithEfficacy.getID()));

                    // Multiply efficacies
                    Type newEfficacyType = new Type();
                    newEfficacyType.setID(pokemonTypeWithEfficacy.getID());
                    newEfficacyType.setEfficacy(firstOccurrence.getEfficacy() * pokemonTypeWithEfficacy.getEfficacy());

                    // TODO: Re-add type to list if percentEfficacy != 100
                    if (Math.round(newEfficacyType.getEfficacy() * 100) != 100) {
                        combinedEfficaciesTypes.add(newEfficacyType);
                    }
                }
            }
        }

        return combinedEfficaciesTypes;
    }

    static boolean alreadyContainsType(List<Type> list, int id) {
        for (Type object : list) {
            if (object.getID() == id) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    static Type getTypeInListByID(List<Type> list, int id) {
        for (Type object : list) {
            if (object.getID() == id) {
                return object;
            }
        }
        return new Type();
    }
}
