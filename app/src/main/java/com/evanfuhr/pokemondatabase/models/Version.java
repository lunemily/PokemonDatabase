package com.evanfuhr.pokemondatabase.models;

public class Version {

    private int mId;
    private int mGenerationId;
    private int mGroupId;
    private String mName;

    public Version() {

    }

    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getGenerationId() {
        return this.mGenerationId;
    }

    public void setGenerationId(int generation_id) {
        this.mGenerationId = generation_id;
    }

    public int getGroupId() {
        return this.mGroupId;
    }

    public void setGroupId(int group_id) {
        this.mGroupId = group_id;
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

    public String getSmogonVersion() {
        switch(mGenerationId) {
            case 1:
                return "rb";
            case 2:
                return "gs";
            case 3:
                return "rs";
            case 4:
                return "dp";
            case 5:
                return "bw";
            case 6:
                return "xy";
            case 7:
                return "sm";
            default:
                return "undefined";
        }
    }

}
