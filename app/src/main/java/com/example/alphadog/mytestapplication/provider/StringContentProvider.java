package com.example.alphadog.mytestapplication.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class StringContentProvider extends ContentProvider {
    public static final String PROVIDER_NAME = "com.example.alphadog.mytestapplication";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/string");
    private static final int STR_TYPE = 1;
    private static final int STRS_TYPE = 2;
    private StringDataBase sdb = null;

    private static int VERSION = 1;

    private static final UriMatcher um = getUriMatcher();

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        sdb = new StringDataBase(getContext(), null, VERSION);
        return true;
    }

    private static UriMatcher getUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(PROVIDER_NAME, "string", STR_TYPE);
        matcher.addURI(PROVIDER_NAME, "string/#", STRS_TYPE);
        return matcher;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (um.match(uri)) {
            case STR_TYPE:
                return "vnd.android.cursor.item/vnd.com.alphadog.mycontentprovider.string";
            case STRS_TYPE:
                return "vnd.android.cursor.item/vnd.com.alphadog.mycontentprovider.string/#";
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        return ContentUris.withAppendedId(uri, sdb.addString(values));
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        return sdb.delString(selection);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        return sdb.updateString(selection,values);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        return sdb.query(projection,selection,selectionArgs,null,null);
    }
}
