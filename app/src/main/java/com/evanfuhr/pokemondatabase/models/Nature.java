package com.evanfuhr.pokemondatabase.models;

public class Nature {

    private int id;
    private String name;
    private int decreasedStatID;
    private int increasedStatID;
    private int hatesFlavorID;
    private int gameIndex;

    public Nature() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDecreasedStatID() {
        return decreasedStatID;
    }

    public void setDecreasedStatID(int decreasedStatID) {
        this.decreasedStatID = decreasedStatID;
    }

    public int getIncreasedStatID() {
        return increasedStatID;
    }

    public void setIncreasedStatID(int increasedStatID) {
        this.increasedStatID = increasedStatID;
    }

    public int getHatesFlavorID() {
        return hatesFlavorID;
    }

    public void setHatesFlavorID(int hatesFlavorID) {
        this.hatesFlavorID = hatesFlavorID;
    }

    public int getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(int gameIndex) {
        this.gameIndex = gameIndex;
    }
}
