package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.MoveCategory;
import com.evanfuhr.pokemondatabase.models.Type;

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
                ", " + TABLE_MOVES + "." + KEY_CATEGORY +
                // TODO: Will need refactoring to handle substitution values
                ", " + TABLE_MOVE_EFFECT_PROSE + "." + KEY_SHORT_EFFECT +
                " FROM " + TABLE_MOVES +
                ", " + TABLE_MOVE_NAMES +
                ", " + TABLE_MOVE_EFFECT_PROSE +
                " WHERE " + TABLE_MOVES + "." + KEY_ID + " = " + TABLE_MOVE_NAMES + "." + KEY_MOVE_ID +
                " AND " + TABLE_MOVES + "." + KEY_EFFECT_ID + " = " + TABLE_MOVE_EFFECT_PROSE + "." + KEY_MOVE_EFFECT_ID +
                " AND " + TABLE_MOVES + "." + KEY_ID + " = " + move.getID() +
                " AND " + TABLE_MOVE_NAMES + "." + KEY_LOCAL_LANGUAGE_ID + " = " + _language_id +
                " AND " + TABLE_MOVE_EFFECT_PROSE + "." + KEY_LOCAL_LANGUAGE_ID + " = " + _language_id
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

    @Deprecated
    public Move getTMForMove(Move move) {
        SQLiteDatabase db = this.getWritableDatabase();
        int version_group_id = getVersionGroupIDByVersionID(_version_id);

        String selectQuery = "SELECT " + TABLE_MACHINES + "." + KEY_MACHINE_NUMBER +
                " FROM " + TABLE_MACHINES +
                " WHERE " + TABLE_MACHINES + "." + KEY_MOVE_ID + " = " + move.getID() +
                " AND " + TABLE_MACHINES + "." + KEY_VERSION_GROUP_ID + " = " + version_group_id
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                move.setTM(Integer.parseInt(cursor.getString(0)));
            }
            cursor.close();
        }

        return move;
    }
}
