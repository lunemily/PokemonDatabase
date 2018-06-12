package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.interfaces.ItemDataInterface;
import com.evanfuhr.pokemondatabase.models.Item;

public class ItemDAO extends DataBaseHelper implements ItemDataInterface {

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public ItemDAO(Context context) {
        super(context);
    }

    public Item getItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = SQLiteQueryBuilder
                .select(field(ITEMS, ID)
                        ,field(ITEM_NAMES, NAME))
                .from(ITEMS)
                .join(ITEM_NAMES)
                .on(field(ITEMS, ID) + "=" + field(ITEM_NAMES, ITEM_ID))
                .where(field(ITEMS, ID) + "=" + item.getId())
                .and(field(ITEM_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
            }
            cursor.close();
        }

        return item;
    }
}
