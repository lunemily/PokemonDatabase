package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.evanfuhr.pokemondatabase/databases/";

    private static String DB_NAME = "pokedex.sqlite";

    private static final int DB_VERSION = 1;

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    int _version_id = 26;

    int _language_id = 9;

    //tables
    static final String TABLE_ABILITIES = "abilities";
    static final String TABLE_ABILITY_NAMES = "ability_names";
    static final String TABLE_EGG_GROUP_PROSE = "egg_group_prose";
    static final String TABLE_MACHINES = "machines";
    static final String TABLE_MOVE_NAMES = "move_names";
    static final String TABLE_MOVES = "moves";
    static final String TABLE_POKEMON = "pokemon";
    static final String TABLE_POKEMON_ABILITIES = "pokemon_abilities";
    static final String TABLE_POKEMON_EGG_GROUPS = "pokemon_egg_groups";
    static final String TABLE_POKEMON_MOVES = "pokemon_moves";
    static final String TABLE_POKEMON_SPECIES = "pokemon_species";
    static final String TABLE_POKEMON_SPECIES_FLAVOR_TEXT = "pokemon_species_flavor_text";
    static final String TABLE_POKEMON_SPECIES_NAMES = "pokemon_species_names";
    static final String TABLE_POKEMON_TYPES = "pokemon_types";
    static final String TABLE_TYPES = "types";
    static final String TABLE_TYPE_EFFICACY = "type_efficacy";
    static final String TABLE_TYPE_NAMES = "type_names";
    static final String TABLE_VERSIONS = "versions";
    static final String TABLE_VERSION_GROUPS = "version_groups";

    //common
    static final String KEY_EGG_GROUP_ID = "egg_group_id";
    static final String KEY_ID = "id";
    static final String KEY_IDENTIFIER = "identifier";
    static final String KEY_LOCAL_LANGUAGE_ID = "local_language_id";
    static final String KEY_MOVE_ID = "move_id";
    static final String KEY_NAME = "name";
    static final String KEY_POKEMON_ID = "pokemon_id";
    static final String KEY_SLOT = "slot";
    static final String KEY_TYPE_ID = "type_id";
    static final String KEY_VERSION_GROUP_ID = "version_group_id";

    //machines
    static final String KEY_MACHINE_NUMBER = "machine_number";

    //moves
    static final String KEY_POWER = "power";
    static final String KEY_PP = "pp";
    static final String KEY_ACCURACY = "accuracy";

    //pokemon
    static final String KEY_BASE_EXPERIENCE = "base_experience";
    static final String KEY_HEIGHT = "height";
    static final String KEY_IS_DEFAULT = "is_default";
    static final String KEY_ORDER = "order";
    static final String KEY_SPECIES_ID = "species_id";
    static final String KEY_WEIGHT = "weight";

    //pokemon_abilities
    static final String KEY_ABILITY_ID = "ability_id";
    static final String KEY_IS_HIDDEN = "is_hidden";

    //pokemon_moves
    static final String KEY_POKEMON_MOVE_METHOD_ID = "pokemon_move_method_id";
    static final String KEY_POKEMON_MOVE_LEVEL = "level";

    //pokemon_species
    static final String KEY_GENDER_RATE = "gender_rate";

    //pokemon_species_flavor_text
    static final String KEY_FLAVOR_TEXT = "flavor_text";

    //pokemon_species_names
    static final String KEY_POKEMON_SPECIES_ID = "pokemon_species_id";

    //type_efficacy
    static final String KEY_DAMAGE_FACTOR = "damage_factor";
    static final String KEY_DAMAGE_TYPE_ID = "damage_type_id";
    static final String KEY_TARGET_TYPE_ID = "target_type_id";

    //types
    static final String KEY_COLOR = "color";

    //version_groups
    static final String KEY_GENERATION_ID = "generation_id";

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (!dbExist) {
            //do nothing if database already exists

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            //database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


// Add your public helper methods to access and get content from the database.
// You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
// to you to create adapters for your views.

    public int getVersionGroupIDByVersionID(int version_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int version_group_id = 1;

        String selectQuery = "SELECT " + TABLE_VERSIONS + "." + KEY_VERSION_GROUP_ID +
                " FROM " + TABLE_VERSIONS +
                " WHERE " + TABLE_VERSIONS + "." + KEY_ID + " = '" + version_id + "'"
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                version_group_id = Integer.parseInt(cursor.getString(0));
            }
            cursor.close();
        }

        return version_group_id;
    }

    public int getGenerationIDByVersionID(int version_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int generation_id = 1;

        int version_group_id = getVersionGroupIDByVersionID(version_id);

        String selectQuery = "SELECT " + TABLE_VERSION_GROUPS + "." + KEY_GENERATION_ID +
                " FROM " + TABLE_VERSION_GROUPS +
                " WHERE " + TABLE_VERSION_GROUPS + "." + KEY_VERSION_GROUP_ID + " = '" + version_group_id + "'"
                ;

        return generation_id;
    }
}
