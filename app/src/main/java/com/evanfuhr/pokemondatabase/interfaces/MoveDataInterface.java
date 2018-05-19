package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.List;

public interface MoveDataInterface {

    List<Move> getAllMoves();

    List<Move> getAllMoves(String nameSearchParam);

    Move getMove(Move move);

    Move getMoveMeta(Move move);

    Move getMove(String identifier);

    List<Move> getMoves(Pokemon pokemon);

    List<Move> getMoves(Type type);
}
