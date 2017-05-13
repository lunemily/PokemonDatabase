package com.evanfuhr.pokemondatabase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Type;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.evanfuhr.pokemondatabase/databases/";

    private static String DB_NAME = "pokemon.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    private int _version_id = 26;

    private int _language_id = 9;

    //tables
    private static final String TABLE_ABILITIES = "abilities";
    private static final String TABLE_ABILITY_NAMES = "ability_names";
    private static final String TABLE_MOVE_NAMES = "move_names";
    private static final String TABLE_MOVES = "moves";
    private static final String TABLE_POKEMON = "pokemon";
    private static final String TABLE_POKEMON_ABILITIES = "pokemon_abilities";
    private static final String TABLE_POKEMON_MOVES = "pokemon_moves";
    private static final String TABLE_POKEMON_SPECIES = "pokemon_species";
    private static final String TABLE_POKEMON_SPECIES_NAMES = "pokemon_species_names";
    private static final String TABLE_POKEMON_TYPES = "pokemon_types";
    private static final String TABLE_TYPES = "types";
    private static final String TABLE_TYPE_NAMES = "type_names";
    private static final String TABLE_VERSIONS = "versions";
    private static final String TABLE_VERSION_GROUPS = "version_groups";

    //common
    private static final String KEY_ID = "id";
    private static final String KEY_IDENTIFIER = "identifier";
    private static final String KEY_LOCAL_LANGUAGE_ID = "local_language_id";
    private static final String KEY_MOVE_ID = "move_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_POKEMON_ID = "pokemon_id";
    private static final String KEY_SLOT = "slot";
    private static final String KEY_TYPE_ID = "type_id";
    private static final String KEY_VERSION_GROUP_ID = "version_group_id";

    //moves
    private static final String KEY_POWER = "power";
    private static final String KEY_PP = "pp";
    private static final String KEY_ACCURACY = "accuracy";

    //pokemon
    private static final String KEY_BASE_EXPERIENCE = "base_experience";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_IS_DEFAULT = "is_default";
    private static final String KEY_ORDER = "order";
    private static final String KEY_SPECIES_ID = "species_id";
    private static final String KEY_WEIGHT = "weight";

    //pokemon_abilities
    private static final String KEY_ABILITY_ID = "ability_id";
    private static final String KEY_IS_HIDDEN = "is_hidden";

    //pokemon_moves
    private static final String KEY_POKEMON_MOVE_METHOD_ID = "pokemon_move_method_id";
    private static final String KEY_POKEMON_MOVE_LEVEL = "level";

    //pokemon_species
    private static final String KEY_GENDER_RATE = "gender_rate";

    //pokemon_species_names
    private static final String KEY_POKEMON_SPECIES_ID = "pokemon_species_id";

    //types
    private static final String KEY_COLOR = "color";

    //version_groups
    private static final String KEY_GENERATION_ID = "generation_id";

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
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

    }


// Add your public helper methods to access and get content from the database.
// You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
// to you to create adapters for your views.

    /**
     * Getter
     * Returns all pokemon
     */
    public List<Pokemon> getAllPokemon() {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Pokemon> pokemonList = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_POKEMON_SPECIES + "." + KEY_ID +
                ", " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_NAME +
                " FROM " + TABLE_POKEMON_SPECIES +
                ", " + TABLE_POKEMON_SPECIES_NAMES +
                " WHERE " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_POKEMON_SPECIES_ID +
                " AND " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_LOCAL_LANGUAGE_ID + " = '" + _language_id + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Pokemon pokemon = new Pokemon();
                pokemon.setID(Integer.parseInt(cursor.getString(0)));
                pokemon.setName(cursor.getString(1));
                //add pokemon to list
                pokemonList.add(pokemon);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return pokemonList;
    }

    /**
     * Getter
     * Returns a fully loaded pokemon including name, height, and weight
     *
     * @param pokemon
     */
    public Pokemon getSinglePokemonByID(Pokemon pokemon) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT " + TABLE_POKEMON_SPECIES + "." + KEY_ID +
                ", " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_NAME +
                ", " + TABLE_POKEMON + "." + KEY_HEIGHT +
                ", " + TABLE_POKEMON + "." + KEY_WEIGHT +
                ", " + TABLE_POKEMON + "." + KEY_BASE_EXPERIENCE +
                ", " + TABLE_POKEMON + ".'" + KEY_ORDER + "'" +
                ", " + TABLE_POKEMON + "." + KEY_IS_DEFAULT +
                ", " + TABLE_POKEMON_SPECIES + "." + KEY_GENDER_RATE +
                " FROM " + TABLE_POKEMON_SPECIES +
                ", " + TABLE_POKEMON_SPECIES_NAMES +
                ", " + TABLE_POKEMON +
                " WHERE " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = '" + pokemon.getID() + "'" +
                " AND " + TABLE_POKEMON + "." + KEY_SPECIES_ID + " = " + TABLE_POKEMON_SPECIES + "." + KEY_ID +
                " AND " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_POKEMON_SPECIES_ID +
                " AND " + TABLE_POKEMON_SPECIES_NAMES + "." + KEY_LOCAL_LANGUAGE_ID + " = '" + _language_id + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                //pokemon.setID(pokemon.getID(0));
                pokemon.setName(cursor.getString(1));
                pokemon.setHeight(Integer.parseInt(cursor.getString(2)));
                pokemon.setWeight(Integer.parseInt(cursor.getString(3)));
                pokemon.setBaseExperience(Integer.parseInt(cursor.getString(4)));
                //pokemon.setOrder(Integer.parseInt(cursor.getString(5)));
                //pokemon.setIsDefault(Boolean.parseBoolean(cursor.getString(6)));
                //pokemon.setGenderRatio(Integer.parseInt(cursor.getString(7)));
            }
            cursor.close();
        }

        return pokemon;
    }

    /**
     * Getter
     * Returns types for pokemon
     *
     * @param pokemon
     */
    public List<Type> getTypesForPokemon(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Type> typesForPokemon = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_POKEMON_TYPES + "." + KEY_SLOT +
                ", " + TABLE_POKEMON_TYPES + "." + KEY_TYPE_ID +
                ", " + TABLE_TYPES + "." + KEY_COLOR +
                " FROM " + TABLE_POKEMON_TYPES +
                ", " + TABLE_TYPES +
                " WHERE " + TABLE_POKEMON_TYPES + "." + KEY_TYPE_ID + " = " + TABLE_TYPES + "." + KEY_ID +
                " AND " + TABLE_POKEMON_TYPES + "." + KEY_POKEMON_ID + " = " + pokemon.getID();

        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Type type = new Type();
                type.setSlot(Integer.parseInt(cursor.getString(0)));
                type.setID(Integer.parseInt(cursor.getString(1)));
                type.setColor(cursor.getString(2));
                //add type to list
                typesForPokemon.add(type);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return typesForPokemon;
    }

    /**
     * Getter
     * Returns all moves for a pokemon in a given game
     *
     * Currently returning just a list of move_ids.
     * In the future, will return a list of move objects with more data.
     *
     * @param pokemon
     */
    public List<Move> getAllMovesForPokemonByGame(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Move> movesForPokemon = new ArrayList<>();
        int version_group_id = getVersionGroupIDByVersionID(_version_id);

        String selectQuery = "SELECT " + TABLE_POKEMON_MOVES + "." + KEY_MOVE_ID +
                ", " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_MOVE_METHOD_ID +
                ", " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_MOVE_LEVEL +
                " FROM " + TABLE_POKEMON_MOVES +
                " WHERE " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_ID + " = '" + pokemon.getID() + "'" +
                " AND " + TABLE_POKEMON_MOVES + "." + KEY_VERSION_GROUP_ID + " = '" + version_group_id + "'" +
                " ORDER BY " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_MOVE_LEVEL + " ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Move move = new Move();
                move.setID(Integer.parseInt(cursor.getString(0)));
                move.setMethodID(Integer.parseInt(cursor.getString(1)));
                move.setLevel(Integer.parseInt(cursor.getString(2)));
                //add move to list
                movesForPokemon.add(move);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return movesForPokemon;
    }

    public Move getMoveByID(Move move) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT " + TABLE_MOVES + "." + KEY_ID +
                ", " + TABLE_MOVE_NAMES + "." + KEY_NAME +
                ", " + TABLE_MOVES + "." + KEY_TYPE_ID +
                ", " + TABLE_MOVES + "." + KEY_POWER +
                ", " + TABLE_MOVES + "." + KEY_PP +
                ", " + TABLE_MOVES + "." + KEY_ACCURACY +
                " FROM " + TABLE_MOVES +
                ", " + TABLE_MOVE_NAMES +
                " WHERE " + TABLE_MOVES + "." + KEY_ID + " = " + TABLE_MOVE_NAMES + "." + KEY_MOVE_ID +
                " AND " + TABLE_MOVES + "." + KEY_ID + " = " + move.getID() +
                " AND " + TABLE_MOVE_NAMES + "." + KEY_LOCAL_LANGUAGE_ID + " = '" + _language_id + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                move.setID(Integer.parseInt(cursor.getString(0)));
                move.setName(cursor.getString(1));
                int type_id = Integer.parseInt(cursor.getString(2));
                Type type = new Type();
                type.setID(type_id);
                move.setType(type);
                if (cursor.getString(3).length() != 0) {
                    move.setPower(Integer.parseInt(cursor.getString(3)));
                }
                move.setPP(Integer.parseInt(cursor.getString(4)));
                if (cursor.getString(5).length() != 0) {
                    move.setAccuracy(Integer.parseInt(cursor.getString(5)));
                }
            }
            cursor.close();
        }

        return move;
    }

    public Move getMoveLevelForPokemonByGame(Move move, Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        int version_group_id = getVersionGroupIDByVersionID(_version_id);

        String selectQuery = "SELECT " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_MOVE_LEVEL +
                " FROM " + TABLE_POKEMON_MOVES +
                " WHERE " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_ID + " = '" + pokemon.getID() + "'" +
                " AND " + TABLE_POKEMON_MOVES + "." + KEY_MOVE_ID + " = '" + move.getID() + "'" +
                " AND " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_MOVE_METHOD_ID + " = '" + "1" + "'" +
                " AND " + TABLE_POKEMON_MOVES + "." + KEY_VERSION_GROUP_ID + " = '" + version_group_id + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                move.setLevel(Integer.parseInt(cursor.getString(0)));
            }
            cursor.close();
        }

        return move;
    }

    public Type getTypeByID(Type type) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT " + TABLE_TYPES + "." + KEY_ID +
                ", " + TABLE_TYPE_NAMES + "." + KEY_NAME +
                ", " + TABLE_TYPES + "." + KEY_COLOR +
                " FROM " + TABLE_TYPES +
                ", " + TABLE_TYPE_NAMES +
                " WHERE " + TABLE_TYPES + "." + KEY_ID + " = '" + type.getID() + "'" +
                " AND " + TABLE_TYPE_NAMES + "." + KEY_LOCAL_LANGUAGE_ID + " = '" + _language_id + "'";

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

    public int getVersionGroupIDByVersionID(int version_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int version_group_id = 1;

        String selectQuery = "SELECT " + TABLE_VERSIONS + "." + KEY_VERSION_GROUP_ID +
                " FROM " + TABLE_VERSIONS +
                " WHERE " + TABLE_VERSIONS + "." + KEY_ID + " = '" + version_id + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                version_group_id = Integer.parseInt(cursor.getString(0));
            }
            cursor.close();
        }

        return version_group_id;
    }

    public List<Ability> getAbilitiesForPokemon(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Ability> abilitiesForPokemon = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_ABILITIES + "." + KEY_ID +
                ", " + TABLE_ABILITY_NAMES + "." + KEY_NAME +
                ", " + TABLE_POKEMON_ABILITIES + "." + KEY_SLOT +
                ", " + TABLE_POKEMON_ABILITIES + "." + KEY_IS_HIDDEN +
                " FROM " + TABLE_ABILITIES +
                ", " + TABLE_ABILITY_NAMES +
                ", " + TABLE_POKEMON_ABILITIES +
                ", " + TABLE_POKEMON_SPECIES +
                " WHERE " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = '" + pokemon.getID() + "'" +
                " AND " + TABLE_ABILITIES + "." + KEY_ID + " = " + TABLE_POKEMON_ABILITIES + "." + KEY_ABILITY_ID +
                " AND " + TABLE_ABILITIES + "." + KEY_ID + " = " + TABLE_ABILITY_NAMES + "." + KEY_ABILITY_ID +
                " AND " + TABLE_POKEMON_SPECIES + "." + KEY_ID + " = " + TABLE_POKEMON_ABILITIES + "." + KEY_POKEMON_ID +
                " AND " + TABLE_ABILITY_NAMES + "." + KEY_LOCAL_LANGUAGE_ID + " = '" + _language_id + "'" +
                " ORDER BY " + TABLE_POKEMON_ABILITIES + "." + KEY_SLOT + " ASC";


        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                //Move move = new Move();
                Ability ability = new Ability();
                ability.setID(Integer.parseInt(cursor.getString(0)));
                ability.setName(cursor.getString(1));
                ability.setSlot(Integer.parseInt(cursor.getString(2)));
                ability.setIsHidden("1".equals(cursor.getString(3)));
                //add move to list
                abilitiesForPokemon.add(ability);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return abilitiesForPokemon;
    }

    public int getGenerationIDByVersionID(int version_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int generation_id = 1;

        int version_group_id = getVersionGroupIDByVersionID(version_id);

        String selectQuery = "SELECT " + TABLE_VERSION_GROUPS + "." + KEY_GENERATION_ID +
                " FROM " + TABLE_VERSION_GROUPS +
                " WHERE " + TABLE_VERSION_GROUPS + "." + KEY_VERSION_GROUP_ID + " = '" + version_group_id + "'";

        return generation_id;
    }
}
