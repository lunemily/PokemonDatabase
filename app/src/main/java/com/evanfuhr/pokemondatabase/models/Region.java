package com.evanfuhr.pokemondatabase.models;

import java.util.List;

public class Region {
    private int mId;
    private String mName;

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
