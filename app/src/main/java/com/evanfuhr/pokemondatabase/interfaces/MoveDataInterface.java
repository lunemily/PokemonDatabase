package com.evanfuhr.pokemondatabase.interfaces;

import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;

import java.util.List;

public interface MoveDataInterface {

    List<Move> getAllMoves();

    List<Move> getAllMoves(String nameSearchParam);

    Move getMoveByID(Move move);

    List<Pokemon> getPokemonByMove(Move move);
}
