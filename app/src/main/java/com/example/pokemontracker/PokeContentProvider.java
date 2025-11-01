package com.example.pokemontracker;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class PokeContentProvider extends ContentProvider {

    public static final String TABLE_NAME = "PokeTable";
    public static final String COL_NATNUM = "NATIONAL_NUMBER";
    public static final String COL_NAME = "NAME";
    public static final String COL_SPECIES = "SPECIES";
    public static final String COL_GENDER = "GENDER";
    public static final String COL_HEIGHT = "HEIGHT";
    public static final String COL_WEIGHT = "WEIGHT";
    public static final String COL_LEVEL = "LEVEL";
    public static final String COL_HP = "HP";
    public static final String COL_ATTACK = "ATTACK";
    public static final String COL_DEFENSE = "DEFENSE";
    public static final String DB_NAME = "PokeInfo";


    public final static String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            "_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NATNUM + " INTEGER NOT NULL, " +
            COL_NAME + " TEXT NOT NULL, " +
            COL_SPECIES + " TEXT NOT NULL, " +
            COL_GENDER + " TEXT NOT NULL, " +
            COL_HEIGHT + " REAL NOT NULL, " +
            COL_WEIGHT + " REAL NOT NULL, " +
            COL_LEVEL + " INTEGER NOT NULL, " +
            COL_HP + " INTEGER NOT NULL, " +
            COL_ATTACK + " INTEGER NOT NULL, " +
            COL_DEFENSE + " INTEGER NOT NULL )";

    public static final Uri CONTENT_URI = Uri.parse("content://com.example.pokemontracker/" + TABLE_NAME);

    MainDataBaseHelper mHelper;
    protected final class MainDataBaseHelper extends SQLiteOpenHelper {

        public MainDataBaseHelper(Context context) {
            super(context, DB_NAME, null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


    public PokeContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDelete = mHelper.getWritableDatabase().delete(TABLE_NAME, selection, selectionArgs);
        return rowsDelete;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = mHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
        return Uri.withAppendedPath(uri, id + "");
    };

    @Override
    public boolean onCreate() {
        mHelper = new MainDataBaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String[] newProjection = new String[]{
                "_ID as _id",
                COL_NATNUM,
                COL_NAME,
                COL_SPECIES,
                COL_GENDER,
                COL_HEIGHT,
                COL_WEIGHT,
                COL_LEVEL,
                COL_HP,
                COL_ATTACK,
                COL_DEFENSE
        };

        Cursor c = mHelper.getReadableDatabase().query(TABLE_NAME, newProjection, selection,
                selectionArgs, null, null, sortOrder);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int rowsUpdate = mHelper.getWritableDatabase().update(TABLE_NAME, values, selection,
                selectionArgs);
        return rowsUpdate;
    }
}