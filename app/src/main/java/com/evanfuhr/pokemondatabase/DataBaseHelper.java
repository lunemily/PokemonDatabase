package com.evanfuhr.pokemondatabase;

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
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.evanfuhr.pokemondatabase/databases/";

    private static String DB_NAME = "pokemon.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    //tables
    private static final String TABLE_MOVES = "moves";
    private static final String TABLE_POKEMON = "pokemon";
    private static final String TABLE_POKEMON_MOVES = "pokemon_moves";
    private static final String TABLE_POKEMON_TYPES = "pokemon_types";
    private static final String TABLE_TYPES = "types";

    //common
    private static final String KEY_ID = "id";
    private static final String KEY_IDENTIFIER = "identifier";
    private static final String KEY_MOVE_ID = "move_id";
    private static final String KEY_POKEMON_ID = "pokemon_id";
    private static final String KEY_TYPE_ID = "type_id";
    private static final String KEY_VERSION_GROUP_ID = "version_group_id";

    //pokemon
    private static final String KEY_BASE_EXPERIENCE = "base_experience";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_IS_DEFAUT = "is_default";
    private static final String KEY_ORDER = "order";
    private static final String KEY_SPECIES_ID = "species_id";
    private static final String KEY_WEIGHT = "weight";

    //pokemon_types
    private static final String KEY_SLOT = "slot";

    //types
    private static final String KEY_COLOR = "color";

    //moves

    //pokemon_moves
    private static final String KEY_POKEMON_MOVE_METHOD_ID = "pokemon_move_method_id";

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

        String selectQuery = "SELECT " + TABLE_POKEMON + "." + KEY_ID +
                ", " + TABLE_POKEMON + "." + KEY_IDENTIFIER +
                " FROM " + TABLE_POKEMON;

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
     * @param pokemon_id
     */
    public Pokemon getSinglePokemonByID(int pokemon_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Pokemon pokemon = new Pokemon();

        String selectQuery = "SELECT " + TABLE_POKEMON + "." + KEY_IDENTIFIER +
                ", " + TABLE_POKEMON + "." + KEY_SPECIES_ID +
                ", " + TABLE_POKEMON + "." + KEY_HEIGHT +
                ", " + TABLE_POKEMON + "." + KEY_WEIGHT +
                ", " + TABLE_POKEMON + "." + KEY_BASE_EXPERIENCE +
                ", " + TABLE_POKEMON + "." + KEY_ORDER +
                ", " + TABLE_POKEMON + "." + KEY_IS_DEFAUT +
                " FROM " + TABLE_POKEMON +
                " WHERE " + TABLE_POKEMON + "." + KEY_ID + " = " + pokemon_id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        pokemon.setID(pokemon_id);
        pokemon.setName(cursor.getString(0));
        cursor.close();

        return pokemon;
    }

    /**
     * Getter
     * Returns types for pokemon
     *
     * @param pokemon_id
     */
    public List<Type> getTypesForPokemon(int pokemon_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Type> typesForPokemon = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_POKEMON_TYPES + "." + KEY_SLOT +
                ", " + TABLE_POKEMON_TYPES + "." + KEY_TYPE_ID +
                ", " + TABLE_TYPES + "." + KEY_COLOR +
                " FROM " + TABLE_POKEMON_TYPES +
                ", " + TABLE_TYPES +
                " WHERE " + TABLE_POKEMON_TYPES + "." + KEY_TYPE_ID + " = " + TABLE_TYPES + "." + KEY_ID +
                " AND " + TABLE_POKEMON_TYPES + "." + KEY_POKEMON_ID + " = " + pokemon_id;

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
     * @param pokemon_id
     */
    public List<Move> getAllMovesForPokemonByGame(int pokemon_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Move> movesForPokemon = new ArrayList<>();
        int version_group_id = 10;

        String selectQuery = "SELECT " + TABLE_POKEMON_MOVES + "." + KEY_MOVE_ID +
                ", " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_MOVE_METHOD_ID +
                " FROM " + TABLE_POKEMON_MOVES +
                " WHERE " + TABLE_POKEMON_MOVES + "." + KEY_POKEMON_ID + " = '" + pokemon_id + "'" +
                " AND " + TABLE_POKEMON_MOVES + "." + KEY_VERSION_GROUP_ID + " = '" + version_group_id + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        //Loop through rows and add each to list
        if (cursor.moveToFirst()) {
            do {
                Move move = new Move();
                move.setID(Integer.parseInt(cursor.getString(0)));
                move.setMethodID(Integer.parseInt(cursor.getString(1)));
                //add move to list
                movesForPokemon.add(move);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return movesForPokemon;
    }

    //TODO: Unfinished. Needs type_id, PP, power, and accuracy
    public Move getMoveByID(int move_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Move move = new Move();

        String selectQuery = "SELECT " + TABLE_MOVES + "." + KEY_IDENTIFIER +
                ", " + TABLE_MOVES + "." + KEY_TYPE_ID +
                " FROM " + TABLE_MOVES +
                " WHERE " + TABLE_MOVES + "." + KEY_ID + " = " + move_id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                move.setName(cursor.getString(0));
                //TODO: Convert to type object
                move.setType(Integer.parseInt(cursor.getString(1)));
            }
            cursor.close();
        }

        return move;
    }
}
