package com.evanfuhr.pokemondatabase.models;

import java.util.ArrayList;
import java.util.List;

public class Move implements Comparable<Move> {

    private int _accuracy = 0;
    private DamageClass _category = DamageClass.STATUS;
    private String _effect;
    private int id = 0;
    private int _level = 0;
    private MoveMethod _method_id = MoveMethod.LEVEL_UP;
    private String _name;
    private int _power = 0;
    private int _pp = 0;
    private int _tm = 0;
    private Type _type;

    

    public int getAccuracy() {
        return this._accuracy;
    }

    public void setAccuracy(int accuracy) {
        this._accuracy = accuracy;
    }
    
    public DamageClass getCategory() {
        return this._category;
    }

    public void setCategory(DamageClass category) {
        this._category = category;
    }

    public String getEffect() {
        return this._effect;
    }

    public void setEffect(String effect) {
        this._effect = effect;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return this._level;
    }

    public void setLevel(int level) {
        this._level = level;
    }

    public MoveMethod getMethodID() {
        return this._method_id;
    }

    public void setMethodID(MoveMethod method_id) {
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

    public int getTM() {
        return _tm;
    }

    public void setTM(int tm) {
        this._tm = tm;
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
            if (move.getMethodID() == MoveMethod.LEVEL_UP) {
                levelMoves.add(move);
            }
        }

        //Collections.sort(levelMoves);

        return levelMoves;
    }

    public static List<Move> getEggMoves(List<Move> moves) {
        List<Move> levelMoves = new ArrayList<>();

        for ( Move move : moves) {
            // Egg methods
            if (move.getMethodID() == MoveMethod.EGG || move.getMethodID() == MoveMethod.LIGHT_BALL_EGG) {
                levelMoves.add(move);
            }
        }

        return levelMoves;
    }

    public static List<Move> getTutorMoves(List<Move> moves) {
        List<Move> levelMoves = new ArrayList<>();

        for ( Move move : moves) {
            // Tutor methods
            if (move.getMethodID() == MoveMethod.TUTOR) {
                levelMoves.add(move);
            }
        }

        return levelMoves;
    }

    public static List<Move> getMachineMoves(List<Move> moves) {
        List<Move> machineMoves = new ArrayList<>();

        for ( Move move : moves) {
            // Machine methods
            if (move.getMethodID() == MoveMethod.MACHINE) {
                machineMoves.add(move);
            }
        }

        return machineMoves;
    }

    @Override
    public int compareTo(Move move) {
        if (this.getLevel() == ((Move) move).getLevel())
            return 0;
        else if ((this.getLevel()) > ((Move) move).getLevel())
            return 1;
        else
            return -1;
    }
}
