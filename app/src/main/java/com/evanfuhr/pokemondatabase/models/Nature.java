package com.evanfuhr.pokemondatabase.models;

import java.util.ArrayList;
import java.util.List;

public class Nature {

    private int id;
    private String name;
    private Stat decreasedStat;
    private Stat increasedStat;
    private Flavor likesFlavor;
    private Flavor hatesFlavor;
    private List<Flavor> flavors;
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

    public Stat getDecreasedStat() {
        return decreasedStat;
    }

    public void setDecreasedStat(Stat decreasedStat) {
        this.decreasedStat = decreasedStat;
    }

    public Stat getIncreasedStat() {
        return increasedStat;
    }

    public void setIncreasedStat(Stat increasedStat) {
        this.increasedStat = increasedStat;
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

    public List<Flavor> getFlavors() {
        List<Flavor> flavors = new ArrayList<>();
        flavors.add(this.likesFlavor);
        flavors.add(this.hatesFlavor);
        return flavors;
    }
}
