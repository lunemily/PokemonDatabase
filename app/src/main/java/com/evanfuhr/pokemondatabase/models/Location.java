package com.evanfuhr.pokemondatabase.models;

import java.util.List;

public class Location {
    private int mId;
    private String mName;
    private List<LocationArea> mLocationAreas;
    private Region mRegion;

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

    public List<LocationArea> getLocationAreas() {
        return mLocationAreas;
    }

    public void setLocationAreas(List<LocationArea> mLocationAreas) {
        this.mLocationAreas = mLocationAreas;
    }

    public Region getRegion() {
        return mRegion;
    }

    public void setRegion(Region mRegion) {
        this.mRegion = mRegion;
    }
}
