package com.codingtive.sportapps.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.codingtive.sportapps.data.database.RoomClient;

import java.util.Objects;

public class AppProvider extends ContentProvider {
    private static final int KEY_CODE_SPORT = 1;
    private static final String AUTHORITY = "com.codingtive.sportapps.provider";
    private static final String TABLE_NAME = "sport";
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME, KEY_CODE_SPORT);
    }

    public AppProvider() {
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return RoomClient.getInstance(getContext())
                .getSportDatabase()
                .getSportDao()
                .getSportsCursor();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
