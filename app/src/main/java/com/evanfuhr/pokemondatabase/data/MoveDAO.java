package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.interfaces.MoveDataInterface;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.DamageClass;
import com.evanfuhr.pokemondatabase.models.Pokemon;
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

        String sql = SQLiteQueryBuilder
                .select(field(MOVES, ID)
                        ,field(MOVE_NAMES, NAME)
                        ,field(MOVES, TYPE_ID))
                .from(MOVES)
                .join(MOVE_NAMES)
                .on(field(MOVES, ID) + "=" + field(MOVE_NAMES, MOVE_ID))
                .where(field(MOVE_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .orderBy(field(MOVE_NAMES, NAME))
                .asc()
                .build();

        Cursor cursor = db.rawQuery(sql, null);


        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Move move = new Move();
                Type type = new Type();
                move.setId(Integer.parseInt(cursor.getString(0)));
                move.setName(cursor.getString(1));
                type.setId(Integer.parseInt(cursor.getString(2)));
                type.setColor(Type.getTypeColor(type.getId()));
                move.setType(type);
                //add pokemon to list
                moves.add(move);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

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

        String sql = SQLiteQueryBuilder
                .select(field(MOVES, ID)
                        ,field(MOVE_NAMES, NAME)
                        ,field(MOVES, TYPE_ID)
                        ,field(MOVES, POWER)
                        ,field(MOVES, PP)
                        ,field(MOVES, ACCURACY)
                        ,field(MOVES, DAMAGE_CLASS_ID)
                        ,field(MOVE_EFFECT_PROSE, SHORT_EFFECT))
                .from(MOVES)
                .join(MOVE_NAMES)
                .on(field(MOVES, ID) + "=" + field(MOVE_NAMES, MOVE_ID))
                .join(MOVE_EFFECT_PROSE)
                .on(field(MOVES, EFFECT_ID) + "=" + field(MOVE_EFFECT_PROSE, MOVE_EFFECT_ID))
                .where(field(MOVES, ID) + "=" + move.getId())
                .and(field(MOVE_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .and(field(MOVE_EFFECT_PROSE, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                move.setId(Integer.parseInt(cursor.getString(0)));
                move.setName(cursor.getString(1));
                int type_id = Integer.parseInt(cursor.getString(2));
                Type type = new Type();
                type.setId(type_id);
                move.setType(type);
                if (!cursor.isNull(3)) {
                    move.setPower(Integer.parseInt(cursor.getString(3)));
                }
                move.setPP(Integer.parseInt(cursor.getString(4)));
                if (!cursor.isNull(5)) {
                    move.setAccuracy(Integer.parseInt(cursor.getString(5)));
                }
                move.setCategory(DamageClass.get(Integer.parseInt(cursor.getString(6))));
                move.setEffect(cursor.getString(7));
            }
            cursor.close();
        }
        db.close();

        return move;
    }

    /**
     * Returns a Move object with most of its non-list data
     *
     * @param   identifier  A Move object to be modified with additional data
     * @return              The modified input is returned
     * @see                 Move
     */
    public Move getMoveByIdentifier(String identifier) {
        SQLiteDatabase db = this.getWritableDatabase();
        Move move = new Move();

        String sql = SQLiteQueryBuilder
                .select(field(MOVES, ID)
                        ,field(MOVE_NAMES, NAME)
                        ,field(MOVES, TYPE_ID)
                        ,field(MOVES, POWER)
                        ,field(MOVES, PP)
                        ,field(MOVES, ACCURACY)
                        ,field(MOVES, DAMAGE_CLASS_ID)
                        ,field(MOVE_EFFECT_PROSE, SHORT_EFFECT)
                        ,field(MOVES, IDENTIFIER))
                .from(MOVES)
                .join(MOVE_NAMES)
                .on(field(MOVES, ID) + "=" + field(MOVE_NAMES, MOVE_ID))
                .join(MOVE_EFFECT_PROSE)
                .on(field(MOVES, EFFECT_ID) + "=" + field(MOVE_EFFECT_PROSE, MOVE_EFFECT_ID))
                .where(field(MOVES, IDENTIFIER) + "=\"" + identifier + "\"")
                .and(field(MOVE_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .and(field(MOVE_EFFECT_PROSE, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                move.setId(Integer.parseInt(cursor.getString(0)));
                move.setName(cursor.getString(1));
                int type_id = Integer.parseInt(cursor.getString(2));
                Type type = new Type();
                type.setId(type_id);
                move.setType(type);
                if (!cursor.isNull(3)) {
                    move.setPower(Integer.parseInt(cursor.getString(3)));
                }
                move.setPP(Integer.parseInt(cursor.getString(4)));
                if (!cursor.isNull(5)) {
                    move.setAccuracy(Integer.parseInt(cursor.getString(5)));
                }
                move.setCategory(DamageClass.get(Integer.parseInt(cursor.getString(6))));
                move.setEffect(cursor.getString(7));
            }
            cursor.close();
        }
        db.close();

        return move;
    }

    /**
     * Returns a Move object with its metadata, primarily its effect chances
     *
     * @param   move  A Move object to be modified with additional data
     * @return              The modified input is returned
     * @see                 Move
     */
    public Move getMoveMetaById(Move move) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = SQLiteQueryBuilder
                .select(field(MOVES, ID)
                        ,field(MOVE_META, CRIT_RATE)
                        ,field(MOVE_META, AILMENT_CHANCE)
                        ,field(MOVE_META, FLINCH_CHANCE)
                        ,field(MOVE_META, STAT_CHANCE))
                .from(MOVES)
                .join(MOVE_META)
                .on(field(MOVES, ID) + "=" + field(MOVE_META, MOVE_ID))
                .where(field(MOVES, ID) + "=" + move.getId())
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                move.setCritRate(Integer.parseInt(cursor.getString(1)));
                // Loop through last columns since we want the non-zero value
                for (int i = 2; i < 5; i++) {
                    if (!cursor.getString(i).equals("0")) {
                        move.setEffectChance(Integer.parseInt(cursor.getString(i)));
                    }
                }
            }
            cursor.close();
        }
        db.close();

        return move;
    }

    // Get filtered moves

    public List<Move> getMovesByType(Type type) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Move> moves = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(MOVES, ID)
                        ,field(MOVE_NAMES, NAME)
                        ,field(MOVES, TYPE_ID))
                .from(MOVES)
                .join(MOVE_NAMES)
                .on(field(MOVES, ID) + "=" + field(MOVE_NAMES, MOVE_ID))
                .where(field(MOVE_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .and(field(MOVES, TYPE_ID) + "=" + type.getId())
                .orderBy(field(MOVE_NAMES, NAME))
                .asc()
                .build();

        Cursor cursor = db.rawQuery(sql, null);


        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Move move = new Move();
                move.setId(Integer.parseInt(cursor.getString(0)));
                move.setName(cursor.getString(1));
                type.setColor(Type.getTypeColor(type.getId()));
                move.setType(type);
                //add pokemon to list
                moves.add(move);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return moves;
    }
}
