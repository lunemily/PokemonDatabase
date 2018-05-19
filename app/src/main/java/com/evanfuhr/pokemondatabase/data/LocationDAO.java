package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.interfaces.LocationDataInterface;
import com.evanfuhr.pokemondatabase.models.Location;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Region;

import java.util.ArrayList;
import java.util.List;

public class LocationDAO extends DataBaseHelper implements LocationDataInterface {

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public LocationDAO(Context context) {
        super(context);
    }

    @Override
    public List<Location> getAllLocations() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Location> locations = new ArrayList<>();

        String sql = SQLiteQueryBuilder
                .select(field(REGIONS, ID)
                        , field(REGION_NAMES, NAME)
                        , field(LOCATIONS, ID)
                        , field(LOCATION_NAMES, NAME))
                .from(LOCATIONS)
                .join(LOCATION_NAMES)
                .on(field(LOCATIONS, ID) + "=" + field(LOCATION_NAMES, LOCATION_ID))
                .join(REGIONS)
                .on(field(LOCATIONS, REGION_ID) + "=" + field(REGIONS, ID))
                .join(REGION_NAMES)
                .on(field(REGIONS, ID) + "=" + field(REGION_NAMES, REGION_ID))
                .where(field(LOCATION_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .and(field(REGION_NAMES, LOCAL_LANGUAGE_ID) + "=" + _language_id)
                .orderBy(field(REGIONS, ID))
                .asc()
                .build();
        Cursor cursor = db.rawQuery(sql, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Location location = new Location();
                Region region = new Region();
                region.setId(Integer.parseInt(cursor.getString(0)));
                region.setName(cursor.getString(1));
                location.setId(Integer.parseInt(cursor.getString(2)));
                location.setName(cursor.getString(3));
                location.setRegion(region);
                //add to list
                locations.add(location);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return locations;
    }

    @Override
    public List<Pokemon> getPokemonByLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Pokemon> pokemon = new ArrayList<>();

//        String sql = SQLiteQueryBuilder
//                .select()
        return null;
    }
}
