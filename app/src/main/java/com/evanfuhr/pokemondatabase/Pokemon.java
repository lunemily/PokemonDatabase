package com.evanfuhr.pokemondatabase;

public class Pokemon {

    private int _id;
    private String _name;
    private int _type_1;
    private int _type_2;
    private List<Type> _types;

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    @Deprecated
    public int getType1() {
        return this._type_1;
    }

    @Deprecated
    public void setType1(int type_id) {
        this._type_1 = type_id;
    }

    @Deprecated
    public int getType2() {
        return this._type_2;
    }

    @Deprecated
    public void setType2(int type_id) {
        this._type_2 = type_id;
    }
    
    public List<Type> getTypes() {
        return this._types;
    }
    
    public void setTypes(List<Type> types) {
        this._types = types;
    }
}
