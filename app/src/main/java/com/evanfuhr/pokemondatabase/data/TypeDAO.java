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

    public List<Type> getAllTypes(String nameSearchParam) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Type> types = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_TYPES + "." + KEY_ID +
                ", " + TABLE_TYPE_NAMES + "." + KEY_NAME +
                ", " + TABLE_TYPES + "." + KEY_COLOR +
                " FROM " + TABLE_TYPES +
                ", " + TABLE_TYPE_NAMES +
                " WHERE " + TABLE_TYPES + "." + KEY_ID + " = " + TABLE_TYPE_NAMES + "." + KEY_TYPE_ID +
                " AND " + TABLE_TYPE_NAMES + "." + KEY_LOCAL_LANGUAGE_ID + " = " + _language_id
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);


        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Type type = new Type();
                type.setID(Integer.parseInt(cursor.getString(0)));
                type.setName(cursor.getString(1));
                type.setColor(cursor.getString(2));
                //add pokemon to list
                types.add(type);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return types;
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

    public Type getSingleTypeEfficacy(Type type) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Type> attackingTypes = new ArrayList<>();
        List<Type> defendingTypes = new ArrayList<>();

        String selectQuery = "SELECT " + KEY_DAMAGE_TYPE_ID +
                ", " + KEY_TARGET_TYPE_ID +
                ", " + KEY_DAMAGE_FACTOR +
                " FROM " + TABLE_TYPE_EFFICACY +
                " WHERE " + TABLE_TYPE_EFFICACY + "." + KEY_DAMAGE_FACTOR + " != 100" +
                " AND (" + TABLE_TYPE_EFFICACY + "." + KEY_DAMAGE_TYPE_ID + " = " + type.getID() +
                " OR " + TABLE_TYPE_EFFICACY + "." + KEY_TARGET_TYPE_ID + " = " + type.getID() + ")" +
                " ORDER BY " + TABLE_TYPE_EFFICACY + "." + KEY_DAMAGE_FACTOR + " DESC"
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Type attackingType = new Type();
                Type defendingType = new Type();

                attackingType.setID(Integer.parseInt(cursor.getString(0)));
                defendingType.setID(Integer.parseInt(cursor.getString(1)));
                int damageFactor = Integer.parseInt(cursor.getString(2));

                if (type.getID() == attackingType.getID()) {
                    // Type is attacker. Add defender
                    defendingType.setEfficacy(damageFactor/(float)100);
                    defendingTypes.add(defendingType);
                } else {
                    // Type is defender. Add attacker
                    attackingType.setEfficacy(damageFactor/(float)100);
                    attackingTypes.add(attackingType);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        type.set_attackingTypes(attackingTypes);
        type.set_defendingTypes(defendingTypes);

        return type;
    }
}
