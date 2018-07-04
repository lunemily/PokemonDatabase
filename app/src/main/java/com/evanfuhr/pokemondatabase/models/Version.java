package com.evanfuhr.pokemondatabase.models;

public class Version extends BaseNamedObject {

    private int mGenerationId;
    private int mGroupId;

    public Version() {
        super();
    }

    public Version(int id) {
        super(id);
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

    public String getSerebiiVersion(Object object) {
        // Serebii is weird
        if (object instanceof Pokemon) {
            switch(mGenerationId) {
                case 1:
                    return "";
                case 2:
                    return "-gs";
                case 3:
                    return "-rs";
                case 4:
                    return "-dp";
                case 5:
                    return "-bw";
                case 6:
                    return "-xy";
                case 7:
                    return "-sm";
                default:
                    return "-undefined";
            }
        } else if (object instanceof Type) {
            switch(mGenerationId) {
                case 1:
                    return "-rby";
                case 2:
                    return "-gs";
                case 3:
                    return "";
                case 4:
                    return "-dp";
                case 5:
                    return "-bw";
                case 6:
                    return "-xy";
                case 7:
                    return "-sm";
                default:
                    return "-undefined";
            }
        }
        return "-undefined";
    }

}
