package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder;
import com.evanfuhr.pokemondatabase.R;

import org.jetbrains.annotations.Contract;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.Context.MODE_PRIVATE;

public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.evanfuhr.pokemondatabase/databases/";

    private static String DB_NAME = "pokedex.sqlite";

    private static final int DB_VERSION = 1;

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    int _language_id = 9;

    //tables
    static final String ABILITIES = "abilities";
    static final String ABILITY_NAMES = "ability_names";
    static final String ABILITY_PROSE = "ability_prose";
    static final String CONTEST_TYPE_NAMES = "contest_type_names";
    static final String CONTEST_TYPES = "contest_types";
    static final String EGG_GROUP_PROSE = "egg_group_prose";
    static final String MACHINES = "machines";
    static final String MOVE_EFFECT_PROSE = "move_effect_prose";
    static final String MOVE_NAMES = "move_names";
    static final String MOVES = "moves";
    static final String NATURE_NAMES = "nature_names";
    static final String NATURES = "natures";
    static final String POKEMON = "pokemon";
    static final String POKEMON_ABILITIES = "pokemon_abilities";
    static final String POKEMON_EGG_GROUPS = "pokemon_egg_groups";
    static final String POKEMON_MOVES = "pokemon_moves";
    static final String POKEMON_SPECIES = "pokemon_species";
    static final String TABLE_POKEMON_SPECIES_FLAVOR_TEXT = "pokemon_species_flavor_text";
    static final String POKEMON_SPECIES_NAMES = "pokemon_species_names";
    static final String POKEMON_TYPES = "pokemon_types";
    static final String STAT_NAMES = "stat_names";
    static final String STATS = "stats";
    static final String TYPES = "types";
    static final String TABLE_TYPE_EFFICACY = "type_efficacy";
    static final String TYPE_NAMES = "type_names";
    static final String VERSIONS = "versions";
    static final String VERSION_GROUPS = "version_groups";
    static final String VERSION_NAMES = "version_names";

    //common
    static final String ABILITY_ID = "ability_id";
    static final String DAMAGE_CLASS_ID = "damage_class_id";
    static final String EFFECT = "effect";
    static final String EGG_GROUP_ID = "egg_group_id";
    static final String ID = "id";
    static final String IDENTIFIER = "identifier";
    static final String IS_MAIN_SERIES = "is_main_series";
    static final String LOCAL_LANGUAGE_ID = "local_language_id";
    static final String MOVE_ID = "move_id";
    static final String NAME = "name";
    static final String POKEMON_ID = "pokemon_id";
    static final String SLOT = "slot";
    static final String TYPE_ID = "type_id";
    static final String VERSION_GROUP_ID = "version_group_id";

    //contest_type_names
    static final String CONTEST_TYPE_ID = "contest_type_id";
    static final String FLAVOR = "flavor";
    static final String COLOR = "color";

    //machines
    static final String MACHINE_NUMBER = "machine_number";

    //moves
    static final String ACCURACY = "accuracy";
    static final String EFFECT_ID = "effect_id";
    static final String POWER = "power";
    static final String PP = "pp";

    //move_effect_prose
    static final String MOVE_EFFECT_ID = "move_effect_id";
    static final String SHORT_EFFECT = "short_effect";

    //nature_names
    static final String NATURE_ID = "nature_id";

    //natures
    static final String DECREASED_STAT_ID = "decreased_stat_id";
    static final String INCREASED_STAT_ID = "increased_stat_id";
    static final String HATES_FLAVOR_ID = "hates_flavor_id";
    static final String LIKES_FLAVOR_ID = "likes_flavor_id";

    //pokemon
    static final String BASE_EXPERIENCE = "base_experience";
    static final String HEIGHT = "height";
    static final String IS_DEFAULT = "is_default";
    static final String ORDER = "order";
    static final String SPECIES_ID = "species_id";
    static final String WEIGHT = "weight";

    //pokemon_abilities
    static final String IS_HIDDEN = "is_hidden";

    //pokemon_moves
    static final String POKEMON_MOVE_METHOD_ID = "pokemon_move_method_id";
    static final String POKEMON_MOVE_LEVEL = "level";

    //pokemon_species
    static final String GENDER_RATE = "gender_rate";

    //pokemon_species_flavor_text
    static final String KEY_FLAVOR_TEXT = "flavor_text";

    //pokemon_species_names
    static final String POKEMON_SPECIES_ID = "pokemon_species_id";
    static final String GENUS = "genus";

    //stat_names
    static final String STAT_ID = "stat_id";

    //stats
    static final String IS_BATTLE_ONLY = "is_battle_only";
    static final String GAME_INDEX = "game_index";

    //type_efficacy
    static final String KEY_DAMAGE_FACTOR = "damage_factor";
    static final String KEY_DAMAGE_TYPE_ID = "damage_type_id";
    static final String KEY_TARGET_TYPE_ID = "target_type_id";

    //version_groups
    static final String GENERATION_ID = "generation_id";

    //version_names
    static final String VERSION_ID = "version_id";

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

    public int getVersionGroupIDByVersionID() {
        SQLiteDatabase db = this.getWritableDatabase();

        SharedPreferences settings = myContext.getSharedPreferences(String.valueOf(R.string.gameVersionID), MODE_PRIVATE);
        int version_id = settings.getInt(String.valueOf(R.string.gameVersionID), R.integer.game_version_id); // Default game is Moon

        int version_group_id = 1;

        String sql = SQLiteQueryBuilder
                .select(field(VERSIONS, VERSION_GROUP_ID))
                .from(VERSIONS)
                .where(field(VERSIONS, ID) + "=" + version_id)
                .build();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                version_group_id = Integer.parseInt(cursor.getString(0));
            }
            cursor.close();
        }

        return version_group_id;
    }

    @NonNull
    @Contract(pure = true)
    static String field(String table, String field) {
        return table + "." + field;
    }
}
