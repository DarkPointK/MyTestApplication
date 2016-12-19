package com.example.alphadog.mytestapplication.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alpha Dog on 2016/12/10.
 */

public class StringDataBase extends SQLiteOpenHelper {
    private static final String STRING_DATABASE_NAME = "stringDataBase";
    private static final String TABLE_NAME = "one";
    private static final String KEY_NAME = "str";
    private static final String CONTENT = "content";
    private static final String CREAT_TABLE = "CREAT TABLE IF NOT EXISTS " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + "INTEGER," + CONTENT + "INTEGER)";
    private static final String DROP_TABLE = "DROP TABLE IS EXISTS" + TABLE_NAME;

    public StringDataBase(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, STRING_DATABASE_NAME, factory, version);
    }

    public StringDataBase(Context context, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, STRING_DATABASE_NAME, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public long addString(ContentValues cv) {

        return getWritableDatabase().insert(TABLE_NAME, null, cv);
    }

    public int delString(String id) {

        return getWritableDatabase().delete(TABLE_NAME, "_id=?", new String[]{id});
    }

    public int updateString(String id, ContentValues cv) {

        return getWritableDatabase().update(TABLE_NAME, cv, "_id=?", new String[]{id});
    }

    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having) {

        return getWritableDatabase().query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, "_id");
    }
}
