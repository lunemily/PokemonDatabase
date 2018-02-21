package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.interfaces.MoveDataInterface;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.MoveCategory;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.List;

public class MoveDAO extends DataBaseHelper implements MoveDataInterface {

    public MoveDAO(Context context) {
        super(context);
    }

    /**
     * Returns a list of all moves
     *
     * @return      An unfiltered list of Move objects
     * @see         Move
     */
    public List<Move> getAllMoves() {
        return getAllMoves("%");
    }

    /**
     * Returns a list of all moves that contain nameSearchParam
     *
     * @param   nameSearchParam A substring to filter Move names with
     * @return                  A filtered list of Pokemon objects
     * @see                     Move
     */
    public List<Move> getAllMoves(String nameSearchParam) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Move> moves = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_MOVES + "." + ID +
                ", " + TABLE_MOVE_NAMES + "." + NAME +
                ", " + TABLE_MOVES + "." + TYPE_ID +
                " FROM " + TABLE_MOVES +
                ", " + TABLE_MOVE_NAMES +
                ", " + TYPES +
                " WHERE " + TABLE_MOVES + "." + ID + " = " + TABLE_MOVE_NAMES + "." + KEY_MOVE_ID +
                " AND " + TABLE_MOVES + "." + TYPE_ID + " = " + TYPES + "." + ID +
                " AND " + TABLE_MOVE_NAMES + "." + LOCAL_LANGUAGE_ID + " = " + _language_id +
                " ORDER BY " + TABLE_MOVE_NAMES + "." + NAME + " ASC"
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);


        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Move move = new Move();
                Type type = new Type();
                move.setID(Integer.parseInt(cursor.getString(0)));
                move.setName(cursor.getString(1));
                type.setID(Integer.parseInt(cursor.getString(2)));
                type.setColor(Type.getTypeColor(type.getID()));
                move.setType(type);
                //add pokemon to list
                moves.add(move);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return moves;
    }

    /**
     * Returns a Move object with most of its non-list data
     *
     * @param   move    A Move object to be modified with additional data
     * @return          The modified input is returned
     * @see             Move
     */
    public Move getMoveByID(Move move) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT " + TABLE_MOVES + "." + ID +
                ", " + TABLE_MOVE_NAMES + "." + NAME +
                ", " + TABLE_MOVES + "." + TYPE_ID +
                ", " + TABLE_MOVES + "." + KEY_POWER +
                ", " + TABLE_MOVES + "." + KEY_PP +
                ", " + TABLE_MOVES + "." + KEY_ACCURACY +
                ", " + TABLE_MOVES + "." + KEY_CATEGORY +
                // TODO: Will need refactoring to handle substitution values
                ", " + TABLE_MOVE_EFFECT_PROSE + "." + KEY_SHORT_EFFECT +
                " FROM " + TABLE_MOVES +
                ", " + TABLE_MOVE_NAMES +
                ", " + TABLE_MOVE_EFFECT_PROSE +
                " WHERE " + TABLE_MOVES + "." + ID + " = " + TABLE_MOVE_NAMES + "." + KEY_MOVE_ID +
                " AND " + TABLE_MOVES + "." + KEY_EFFECT_ID + " = " + TABLE_MOVE_EFFECT_PROSE + "." + KEY_MOVE_EFFECT_ID +
                " AND " + TABLE_MOVES + "." + ID + " = " + move.getID() +
                " AND " + TABLE_MOVE_NAMES + "." + LOCAL_LANGUAGE_ID + " = " + _language_id +
                " AND " + TABLE_MOVE_EFFECT_PROSE + "." + LOCAL_LANGUAGE_ID + " = " + _language_id
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                move.setID(Integer.parseInt(cursor.getString(0)));
                move.setName(cursor.getString(1));
                int type_id = Integer.parseInt(cursor.getString(2));
                Type type = new Type();
                type.setID(type_id);
                move.setType(type);
                if (!cursor.isNull(3)) {
                    move.setPower(Integer.parseInt(cursor.getString(3)));
                }
                move.setPP(Integer.parseInt(cursor.getString(4)));
                if (!cursor.isNull(5)) {
                    move.setAccuracy(Integer.parseInt(cursor.getString(5)));
                }
                move.setCategory(MoveCategory.get(Integer.parseInt(cursor.getString(6))));
                move.setEffect(cursor.getString(7));
            }
            cursor.close();
        }
        return move;
    }
}
