package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.evanfuhr.pokemondatabase.interfaces.TypeDataInterface;
import com.evanfuhr.pokemondatabase.models.Type;

import java.util.ArrayList;
import java.util.List;

public class TypeDAO extends DataBaseHelper implements TypeDataInterface {

    public TypeDAO(Context context) {
        super(context);
    }

    /**
     * Returns a list of all types
     *
     * @return      An unfiltered list of Type objects
     * @see         Type
     */
    public List<Type> getAllTypes() {
        return getAllTypes("%");
    }

    /**
     * Returns a list of all types that contain nameSearchParam
     *
     * @param   nameSearchParam A substring to filter Type names with
     * @return                  A filtered list of Type objects
     * @see                     Type
     */
    public List<Type> getAllTypes(String nameSearchParam) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Type> types = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_TYPES + "." + KEY_ID +
                ", " + TABLE_TYPE_NAMES + "." + KEY_NAME +
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
                type.setColor(Type.getTypeColor(type.getID()));

                //add pokemon to list
                types.add(type);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return types;
    }

    /**
     * Returns a Type object with most of its non-list data
     *
     * @param   type     A Type object to be modified with additional data
     * @return          The modified input is returned
     * @see             Type
     */
    public Type getTypeByID(Type type) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT " + TABLE_TYPES + "." + KEY_ID +
                ", " + TABLE_TYPE_NAMES + "." + KEY_NAME +
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
                type.setColor(Type.getTypeColor(type.getID()));
            }
            cursor.close();
        }

        return type;
    }

    /**
     * Returns the type input with lists of Type objects and their relative
     * effectiveness against or resistance to the input type
     *
     * @param   type    The type (of the given Pokemon)
     * @return          The modified input is returned
     * @see             Type
     */
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

                if (type.getID() == attackingType.getID() && type.getID() == defendingType.getID()) {
                    // Type is defender. Add attacker
                    attackingType.setEfficacy(damageFactor/100f);
                    attackingTypes.add(attackingType);
                    // Type is attacker. Add defender
                    defendingType.setEfficacy(damageFactor/100f);
                    defendingTypes.add(defendingType);
                } else if (type.getID() == attackingType.getID()) {
                    // Type is attacker. Add defender
                    defendingType.setEfficacy(damageFactor/100f);
                    defendingTypes.add(defendingType);
                } else {
                    // Type is defender. Add attacker
                    attackingType.setEfficacy(damageFactor/100f);
                    attackingTypes.add(attackingType);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        type.set_attackingTypes(attackingTypes);
        type.set_defendingTypes(defendingTypes);

        return type;
    }

    /**
     * Consolidates both type inputs with lists of Type objects and their relative
     * effectiveness against or resistance to the input types
     *
     * @param   type1   The first type (of the given Pokemon)
     * @param   type2   The second type (of the given Pokemon)
     * @return          The consolidated input is returned
     * @see             Type
     */
    public Type getDualTypeEfficacy(Type type1, Type type2) {
        // Only need to return single type since this function returns combined effectiveness
        Type dualType = new Type();
        SQLiteDatabase db = this.getWritableDatabase();

        List<Type> attackingTypes = new ArrayList<>();

        String selectQuery = "SELECT " + KEY_DAMAGE_TYPE_ID +
                ", COUNT(" + KEY_DAMAGE_TYPE_ID + ") as dual_type" +
                ", SUM(" + KEY_DAMAGE_FACTOR + ") as combined_damage_factor" +
                " FROM " + TABLE_TYPE_EFFICACY +
                " WHERE " + KEY_DAMAGE_FACTOR + " != 100" +
                " AND " + KEY_TARGET_TYPE_ID + " in (" + type1.getID() + ", " + type2.getID() + ")" +
                " GROUP BY " + KEY_DAMAGE_TYPE_ID +
                " ORDER BY combined_damage_factor DESC"
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Type attackingType = new Type();

                attackingType.setID(Integer.parseInt(cursor.getString(0)));
                boolean isDaulType = (Integer.parseInt(cursor.getString(1)) == 2);
                int damageFactor = Integer.parseInt(cursor.getString(2));

                if (isDaulType) {
                    switch (damageFactor) {
                        case 0: // both immune
                            attackingType.setEfficacy(0f);
                            break;
                        case 50: // one immune, one resist
                            attackingType.setEfficacy(0f);
                            break;
                        case 200: // one immune, one weak
                            attackingType.setEfficacy(0f);
                            break;
                        case 100: // both resist
                            attackingType.setEfficacy(0.25f);
                            break;
                        case 250: // one weak, one resist
                            continue;
                        case 400: // both weak
                            attackingType.setEfficacy(4f);
                            break;
                        default:
                            Log.e("Error", "Unexpected dual_type sum encountered");
                            break;
                    }
                } else {
                    attackingType.setEfficacy(damageFactor/100f);
                }

                attackingTypes.add(attackingType);

            } while (cursor.moveToNext());
        }
        cursor.close();

        dualType.set_attackingTypes(attackingTypes);

        return dualType;
    }
}
