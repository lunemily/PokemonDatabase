package com.evanfuhr.pokemondatabase.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evanfuhr.pokemondatabase.data.TypeDAO;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public class Type {

    private List<Type> _attackingTypes;
    private String color;
    private List<Type> _defendingTypes;
    private float _efficacy;
    private boolean isAttacker = false;
    private int id = 0;
    private String _name;
    private int _slot;

    public List<Type> get_attackingTypes() {
        return this._attackingTypes;
    }

    public void set_attackingTypes(List<Type> attackingTypes) {
        this._attackingTypes = attackingTypes;
    }

    public String getColor() {
        return getTypeColor(this.id);
    }

    public void setColor(String color) {
        this.color = color;
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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isAttacker() {
        return isAttacker;
    }

    public void setAttacker(boolean attacker) {
        isAttacker = attacker;
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

    @NonNull
    @Contract(pure = true)
    // TODO: Use an enum for this
    public static String getTypeColor(int id) {
        switch (id) {
            case 1:
                return "#A8A878"; // Normal
            case 2:
                return "#C03028"; // Fighting
            case 3:
                return "#A890F0"; // Flying
            case 4:
                return "#A040A0"; // Poison
            case 5:
                return "#E0C068"; // Ground
            case 6:
                return "#B8A038"; // Rock
            case 7:
                return "#A8B820"; // Bug
            case 8:
                return "#705898"; // Ghost
            case 9:
                return "#B8B8D0"; // Steel
            case 10:
                return "#F08030"; // Fire
            case 11:
                return "#6890F0"; // Water
            case 12:
                return "#78C850"; // Grass
            case 13:
                return "#F8D030"; // Electric
            case 14:
                return "#F85888"; // Psychic
            case 15:
                return "#98D8D8"; // Ice
            case 16:
                return "#7038F8"; // Dragon
            case 17:
                return "#705848"; // Dark
            case 18:
                return "#EE99AC"; // Fairy
            case 10001:
                return "#68A090"; // Unknown
            case 10002:
                return "#555555"; // Shadow
            default:
                return "#000000";
        }
    }
}
