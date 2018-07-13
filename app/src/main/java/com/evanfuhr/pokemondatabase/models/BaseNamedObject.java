package com.evanfuhr.pokemondatabase.models;

public class BaseNamedObject {

    int mId;
    String mName;

    BaseNamedObject() {
    }

    BaseNamedObject(int id) {
        this.mId = id;
    }

    BaseNamedObject(String name) {
        this.mName = name;
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
