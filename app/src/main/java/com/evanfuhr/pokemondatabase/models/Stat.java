package com.evanfuhr.pokemondatabase.models;

public class Stat extends BaseNamedObject {

    private int damageClassId;
    private boolean isBattleOnly;
    private int gameIndex;

    public Stat() {
        super();
    }

    public Stat(int id) {
        super(id);
    }

    public int getDamageClassId() {
        return damageClassId;
    }

    public void setDamageClassId(int damageClassId) {
        this.damageClassId = damageClassId;
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
