package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.List;

public interface MoveDataInterface {

    List<Move> getAllMoves();

    List<Move> getAllMoves(String nameSearchParam);

    Move getMoveByID(Move move);

    Move getMoveMetaById(Move move);

    Move getMoveByIdentifier(String identifier);

    List<Move> getMovesByType(Type type);
}
