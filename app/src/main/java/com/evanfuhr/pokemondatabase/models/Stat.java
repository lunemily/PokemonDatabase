package com.evanfuhr.pokemondatabase.models;

public class Stat {

    private int id;
    private int damageClassId;
    private String name;
    private boolean isBattleOnly;
    private int gameIndex;

    public Stat() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDamageClassId() {
        return damageClassId;
    }

    public void setDamageClassId(int damageClassId) {
        this.damageClassId = damageClassId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBattleOnly() {
        return isBattleOnly;
    }

    public void setBattleOnly(boolean battleOnly) {
        isBattleOnly = battleOnly;
    }

    public int getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(int gameIndex) {
        this.gameIndex = gameIndex;
    }
}
