package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

/**
 * Created by evanf on 02-Sep-17.
 */

public class MoveDAO extends DataBaseHelper {
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public MoveDAO(Context context) {
        super(context);
    }

    public Move getMoveByID(Move move) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT " + TABLE_MOVES + "." + KEY_ID +
                ", " + TABLE_MOVE_NAMES + "." + KEY_NAME +
                ", " + TABLE_MOVES + "." + KEY_TYPE_ID +
                ", " + TABLE_MOVES + "." + KEY_POWER +
                ", " + TABLE_MOVES + "." + KEY_PP +
                ", " + TABLE_MOVES + "." + KEY_ACCURACY +
                " FROM " + TABLE_MOVES +
                ", " + TABLE_MOVE_NAMES +
                " WHERE " + TABLE_MOVES + "." + KEY_ID + " = " + TABLE_MOVE_NAMES + "." + KEY_MOVE_ID +
                " AND " + TABLE_MOVES + "." + KEY_ID + " = " + move.getID() +
                " AND " + TABLE_MOVE_NAMES + "." + KEY_LOCAL_LANGUAGE_ID + " = '" + _language_id + "'"
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
                if (cursor.getString(3).length() != 0) {
                    move.setPower(Integer.parseInt(cursor.getString(3)));
                }
                move.setPP(Integer.parseInt(cursor.getString(4)));
                if (cursor.getString(5).length() != 0) {
                    move.setAccuracy(Integer.parseInt(cursor.getString(5)));
                }
            }
            cursor.close();
        }
        return move;
    }
}
