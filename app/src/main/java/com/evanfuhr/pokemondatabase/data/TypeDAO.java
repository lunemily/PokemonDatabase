package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.interfaces.TypeDataInterface;
import com.evanfuhr.pokemondatabase.models.Pokemon;
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
        SQLiteDatabase db = this.getWritableDatabase();

        List<Type> types = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(TYPES, ID)
                        ,field(TYPE_NAMES, NAME))
                .from(TYPES)
                .join(TYPE_NAMES)
                .on(field(TYPES, ID) + "=" + field(TYPE_NAMES, TYPE_ID))
                .where(field(TYPE_NAMES, LOCAL_LANGUAGE_ID)+ "=" + _language_id)
                .orderBy(field(TYPE_NAMES, NAME))
                .asc()
                .build();

        Cursor cursor = db.rawQuery(sql, null);

        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Type type = new Type();
                type.setId(Integer.parseInt(cursor.getString(0)));
                type.setName(cursor.getString(1));
                type.setColor(Type.getTypeColor(type.getId()));

                //add type to list
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
    public Type getType(Type type) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = SQLiteQueryBuilder
                .select(field(TYPES, ID)
                        ,field(TYPE_NAMES, NAME))
                .from(TYPES)
                .join(TYPE_NAMES)
                .on(field(TYPES, ID) + "=" + field(TYPE_NAMES, TYPE_ID))
                .where(field(TYPES, ID)+ "=" + type.getId())
                .and(field(TYPE_NAMES, LOCAL_LANGUAGE_ID)+ "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                type.setId(Integer.parseInt(cursor.getString(0)));
                type.setName(cursor.getString(1));
                type.setColor(Type.getTypeColor(type.getId()));
            }
            cursor.close();
        }

        return type;
    }

    /**
     * Returns a Type object with most of its non-list data
     *
     * @param   identifier  A String identifier used to find a single Type
     * @return              The modified input is returned
     * @see                 Type
     */
    public Type getType(String identifier) {
        SQLiteDatabase db = this.getWritableDatabase();
        Type type = new Type();

        String sql = SQLiteQueryBuilder
                .select(field(TYPES, ID)
                        ,field(TYPE_NAMES, NAME)
                        ,field(TYPES, IDENTIFIER))
                .from(TYPES)
                .join(TYPE_NAMES)
                .on(field(TYPES, ID) + "=" + field(TYPE_NAMES, TYPE_ID))
                .where(field(TYPES, IDENTIFIER)+ "=\"" + identifier + "\"")
                .and(field(TYPE_NAMES, LOCAL_LANGUAGE_ID)+ "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                type.setId(Integer.parseInt(cursor.getString(0)));
                type.setName(cursor.getString(1));
                type.setColor(Type.getTypeColor(type.getId()));
            }
            cursor.close();
        }

        return type;
    }

    /**
     * Adds types to the input pokemon and returns it
     *
     * @param   pokemon A pokemon object to be modified with additional data
     * @return          The modified input is returned
     * @see             Pokemon
     * @see             Type
     */
    public List<Type> getTypes(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Type> typesForPokemon = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(POKEMON_TYPES, SLOT)
                        , field(POKEMON_TYPES, TYPE_ID))
                .from(POKEMON_TYPES)
                .where(field(POKEMON_TYPES, POKEMON_ID) + "=" + pokemon.getId())
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Type type = new Type();
                type.setSlot(Integer.parseInt(cursor.getString(0)));
                type.setId(Integer.parseInt(cursor.getString(1)));
                type.setColor(Type.getTypeColor(type.getId()));
                //add type to list
                typesForPokemon.add(type);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return typesForPokemon;
    }

    /**
     * Returns the type input with lists of Type objects and their relative
     * effectiveness against or resistance to the input type
     *
     * @param   type    The type (of the given Pokemon)
     * @return          The modified input is returned
     * @see             Type
     */
    public List<Type> getSingleTypeEfficacy(Type type) {
        return getSingleTypeEfficacy(type, false);
    }

    public List<Type> getSingleTypeEfficacy(Type type, boolean forPokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Type> originatingTypes = new ArrayList<>();
        List<Type> targetTypes = new ArrayList<>();

        List<Type> efficacyTypes = new ArrayList<>();

        String selectQuery = "SELECT " + KEY_DAMAGE_TYPE_ID +
                ", " + KEY_TARGET_TYPE_ID +
                ", " + KEY_DAMAGE_FACTOR +
                " FROM " + TABLE_TYPE_EFFICACY +
                " WHERE " + TABLE_TYPE_EFFICACY + "." + KEY_DAMAGE_FACTOR + " != 100" +
                " AND (" + TABLE_TYPE_EFFICACY + "." + KEY_DAMAGE_TYPE_ID + " = " + type.getId() +
                " OR " + TABLE_TYPE_EFFICACY + "." + KEY_TARGET_TYPE_ID + " = " + type.getId() + ")" +
                " ORDER BY " + TABLE_TYPE_EFFICACY + "." + KEY_DAMAGE_FACTOR + " DESC"
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Type originatingType = new Type();
                Type targetType = new Type();

                targetType.setTarget(true);
                originatingType.setId(Integer.parseInt(cursor.getString(0)));
                targetType.setId(Integer.parseInt(cursor.getString(1)));
                int damageFactor = Integer.parseInt(cursor.getString(2));

                // Load the types
                originatingType = getType(originatingType);
                targetType = getType(targetType);

                if (type.getId() == originatingType.getId() && type.getId() == targetType.getId()) {
                    // Type is defender. Add attacker
                    originatingType.setEfficacy(damageFactor/100f);
                    originatingTypes.add(originatingType);
                    // Type is attacker. Add defender
                    targetType.setEfficacy(damageFactor/100f);
                    targetTypes.add(targetType);
                } else if (type.getId() == originatingType.getId()) {
                    // Type is attacker. Add defender
                    targetType.setEfficacy(damageFactor/100f);
                    targetTypes.add(targetType);
                } else {
                    // Type is defender. Add attacker
                    originatingType.setEfficacy(damageFactor/100f);
                    originatingTypes.add(originatingType);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        efficacyTypes.addAll(originatingTypes);
        if (!forPokemon) {
            efficacyTypes.addAll(targetTypes);
        }

        return efficacyTypes;
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
    public List<Type> getDualTypeEfficacy(Type type1, Type type2) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Type> attackingTypes = new ArrayList<>();

        String selectQuery = "SELECT " + KEY_DAMAGE_TYPE_ID +
                ", COUNT(" + KEY_DAMAGE_TYPE_ID + ") as dual_type" +
                ", SUM(" + KEY_DAMAGE_FACTOR + ") as combined_damage_factor" +
                " FROM " + TABLE_TYPE_EFFICACY +
                " WHERE " + KEY_DAMAGE_FACTOR + " != 100" +
                " AND " + KEY_TARGET_TYPE_ID + " in (" + type1.getId() + ", " + type2.getId() + ")" +
                " GROUP BY " + KEY_DAMAGE_TYPE_ID +
                " ORDER BY combined_damage_factor DESC"
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Type originatingType = new Type();

                originatingType.setId(Integer.parseInt(cursor.getString(0)));
                originatingType = getType(originatingType);

                boolean isDualType = (Integer.parseInt(cursor.getString(1)) == 2);
                int damageFactor = Integer.parseInt(cursor.getString(2));

                if (isDualType) {
                    switch (damageFactor) {
                        case 0: // both immune
                            originatingType.setEfficacy(0f);
                            break;
                        case 50: // one immune, one resist
                            originatingType.setEfficacy(0f);
                            break;
                        case 200: // one immune, one weak
                            originatingType.setEfficacy(0f);
                            break;
                        case 100: // both resist
                            originatingType.setEfficacy(0.25f);
                            break;
                        case 250: // one weak, one resist
                            continue;
                        case 400: // both weak
                            originatingType.setEfficacy(4f);
                            break;
                        default:
                            Log.e("Error", "Unexpected dual_type sum encountered");
                            break;
                    }
                } else {
                    originatingType.setEfficacy(damageFactor/100f);
                }

                attackingTypes.add(originatingType);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return attackingTypes;
    }
}
