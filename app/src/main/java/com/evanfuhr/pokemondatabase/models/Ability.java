package com.evanfuhr.pokemondatabase.models;

public class Ability extends BaseNamedObject {

    private boolean _is_hidden;
    private String prose;
    private int _slot;

    public Ability() {
        super();
    }

    public Ability(int id) {
        super(id);
    }

    public boolean getIsHidden() {
        return this._is_hidden;
    }

    public void setIsHidden(boolean is_hidden) {
        this._is_hidden = is_hidden;
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
