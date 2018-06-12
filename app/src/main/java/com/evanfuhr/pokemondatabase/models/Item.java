package com.evanfuhr.pokemondatabase.models;

public class Item {

    private int mId;
    private String mName;

    public Item() {
    }

    public Item(int id) {
        this.mId = id;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        if (mName == null) {
            mName = "undefined";
        }

        return this.mName;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
