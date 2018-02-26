package com.evanfuhr.pokemondatabase.models;

public class Nature {

    private int id;
    private String name;
    private int decreasedStatID;
    private int increasedStatID;
    private Flavor likesFlavor;
    private Flavor hatesFlavor;
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

    public Flavor getHatesFlavor() {
        return hatesFlavor;
    }

    public void setHatesFlavor(Flavor hatesFlavor) {
        this.hatesFlavor = hatesFlavor;
    }

    public int getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(int gameIndex) {
        this.gameIndex = gameIndex;
    }

    public Flavor getLikesFlavor() {
        return likesFlavor;
    }

    public void setLikesFlavor(Flavor likesFlavor) {
        this.likesFlavor = likesFlavor;
    }
}
