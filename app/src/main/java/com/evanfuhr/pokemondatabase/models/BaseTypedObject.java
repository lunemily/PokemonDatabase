package com.evanfuhr.pokemondatabase.models;

import java.util.List;

public class BaseTypedObject extends BaseNamedObject {

    Type mType;
    List<Type> mTypes;

    BaseTypedObject() {
        super();
    }

    BaseTypedObject(int id) {
        super();
    }

    public Type getType() {
        return this.mType;
    }

    public void setType(Type type) {
        this.mType = type;
    }

    public List<Type> getTypes() {
        return this.mTypes;
    }

    public void setTypes(List<Type> types) {
        this.mTypes = types;
    }
}
