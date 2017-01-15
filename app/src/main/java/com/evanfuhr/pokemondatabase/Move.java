package com.evanfuhr.pokemondatabase;

import java.util.ArrayList;
import java.util.List;

public class Move {

    private int _accuracy = 0;
    private int _id = 0;
    private int _level = 0;
    private int _method_id = 0;
    private String _name;
    private int _power = 0;
    private int _pp = 0;
    private Type _type;

    

    public int getAccuracy() {
        return this._accuracy;
    }

    public void setAccuracy(int accuracy) {
        this._accuracy = accuracy;
    }
    
    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getLevel() {
        return this._level;
    }

    public void setLevel(int level) {
        this._level = level;
    }

    public int getMethodID() {
        return this._method_id;
    }

    public void setMethodID(int method_id) {
        this._method_id = method_id;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int getPower() {
        return this._power;
    }

    public void setPower(int power) {
        this._power = power;
    }

    public int getPP() {
        return this._pp;
    }

    public void setPP(int pp) {
        this._pp = pp;
    }

    public Type getType() {
        return this._type;
    }

    public void setType(Type type) {
        this._type = type;
    }

    //Static methods

    public static List<Move> getLevelUpMoves(List<Move> moves) {
        List<Move> levelMoves = new ArrayList<>();

        for ( Move move : moves) {
            // Level-up methods
            if (move.getMethodID() == 1) {
                levelMoves.add(move);
            }
        }

        return levelMoves;
    }

    public static List<Move> getEggMoves(List<Move> moves) {
        List<Move> levelMoves = new ArrayList<>();

        for ( Move move : moves) {
            // Egg methods
            if (move.getMethodID() == 2) {
                levelMoves.add(move);
            }
        }

        return levelMoves;
    }

    public static List<Move> getTutorMoves(List<Move> moves) {
        List<Move> levelMoves = new ArrayList<>();

        for ( Move move : moves) {
            // Tutor methods
            if (move.getMethodID() == 3) {
                levelMoves.add(move);
            }
        }

        return levelMoves;
    }

    public static List<Move> getMachineMoves(List<Move> moves) {
        List<Move> levelMoves = new ArrayList<>();

        for ( Move move : moves) {
            // Machine methods
            if (move.getMethodID() == 4) {
                levelMoves.add(move);
            }
        }

        return levelMoves;
    }
}
