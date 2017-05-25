package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.List;

public class TypeDAO extends DataBaseHelper {

    public TypeDAO(Context context) {
        super(context);
    }

    public Type getTypeByID(Type type) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT " + TABLE_TYPES + "." + KEY_ID +
                ", " + TABLE_TYPE_NAMES + "." + KEY_NAME +
                ", " + TABLE_TYPES + "." + KEY_COLOR +
                " FROM " + TABLE_TYPES +
                ", " + TABLE_TYPE_NAMES +
                " WHERE " + TABLE_TYPES + "." + KEY_ID + " = '" + type.getID() + "'" +
                " AND " + TABLE_TYPES + "." + KEY_ID + " = " + TABLE_TYPE_NAMES + "." + KEY_TYPE_ID +
                " AND " + TABLE_TYPE_NAMES + "." + KEY_LOCAL_LANGUAGE_ID + " = '" + _language_id + "'"
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                type.setID(Integer.parseInt(cursor.getString(0)));
                type.setName(cursor.getString(1));
                type.setColor(cursor.getString(2));
            }
            cursor.close();
        }

        return type;
    }

    public Type getEfficacy(Type type) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Integer> immuneTo = new ArrayList<>();
        List<Integer> resistantTo = new ArrayList<>();
        List<Integer> weakTo = new ArrayList<>();

        String selectQuery = "SELECT " + KEY_DAMAGE_TYPE_ID +
                ", " + KEY_TARGET_TYPE_ID +
                ", " + KEY_DAMAGE_FACTOR +
                " FROM " + TABLE_TYPE_EFFICACY +
                " WHERE " + TABLE_TYPE_EFFICACY + "." + KEY_DAMAGE_FACTOR + " != '100'" +
                " AND (" + TABLE_TYPE_EFFICACY + "." + KEY_DAMAGE_TYPE_ID + " = '" + type.getID() + "'" +
                " OR " + TABLE_TYPE_EFFICACY + "." + KEY_TARGET_TYPE_ID + " = '" + type.getID() + "')"
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                if (type.getID() == Integer.parseInt(cursor.getString(0))) {
                    //TODO do stuff
                } else {
                    switch (Integer.parseInt(cursor.getString(3))) {
                        case 0:
                            immuneTo.add(Integer.valueOf(cursor.getString(0)));
                            break;
                        case 50:
                            resistantTo.add(Integer.valueOf(cursor.getString(0)));
                            break;
                        case 200:
                            weakTo.add(Integer.valueOf(cursor.getString(0)));
                            break;
                        default:
                            break;
                    }
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return type;
    }
}
