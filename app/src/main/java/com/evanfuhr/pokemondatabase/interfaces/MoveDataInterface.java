package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Move;

import java.util.List;

public interface MoveDataInterface {

    List<Move> getAllMoves();

    List<Move> getAllMoves(String nameSearchParam);

    Move getMoveByID(Move move);
}
