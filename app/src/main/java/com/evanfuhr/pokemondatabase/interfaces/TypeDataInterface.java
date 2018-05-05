package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Type;

import java.util.List;

public interface TypeDataInterface {

    List<Type> getAllTypes();

    List<Type> getAllTypes(String nameSearchParam);

    Type getTypeByID(Type type);

    Type getTypeByIdentifier(String identifier);

    List<Type> getSingleTypeEfficacy(Type type);

    List<Type> getDualTypeEfficacy(Type type1, Type type2);
}
