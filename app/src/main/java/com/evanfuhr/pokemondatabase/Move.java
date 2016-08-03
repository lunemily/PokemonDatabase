package com.evanfuhr.pokemondatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 01-Aug-16.
 */
public class Move {

    private int _id;
    private int _level;
    private int _method_id;
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

    public int getMethodID() {
        return this._method_id;
    }

    public void setMethodID(int method_id) {
        this._method_id = method_id;
    }

    public int getLevel() {
        return this._level;
    }

    public void setLevel(int level) {
        this._level = level;
    }

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
