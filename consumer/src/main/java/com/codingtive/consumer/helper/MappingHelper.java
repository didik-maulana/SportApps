package com.codingtive.consumer.helper;

import android.database.Cursor;

import com.codingtive.consumer.model.Sport;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Sport> mapCursorToArrayList(Cursor favoritesCursor) {
        ArrayList<Sport> favorites = new ArrayList<>();
        while (favoritesCursor != null && favoritesCursor.moveToNext()) {
            String title = favoritesCursor.getString(favoritesCursor.getColumnIndexOrThrow(DatabaseContract.AppDatabase.SPORT_TITLE));
            String image = favoritesCursor.getString(favoritesCursor.getColumnIndexOrThrow(DatabaseContract.AppDatabase.SPORT_IMAGE));
            favorites.add(new Sport(title, image));
        }
        return favorites;
    }
}
