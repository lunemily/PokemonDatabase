package com.evanfuhr.pokemondatabase.models;

public class Ability {

    private int id;
    private boolean _is_hidden;
    private String _name;
    private String prose;
    private int _slot;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIsHidden() {
        return this._is_hidden;
    }

    public void setIsHidden(boolean is_hidden) {
        this._is_hidden = is_hidden;
    }

    public String getName() {
        if (_name == null) {
            _name = "undefined";
        }

        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getProse() {
        return prose;
    }

    public void setProse(String prose) {
        this.prose = prose;
    }

    public int getSlot() {
        return this._slot;
    }

    public void setSlot(int slot) {
        this._slot = slot;
    }
}
