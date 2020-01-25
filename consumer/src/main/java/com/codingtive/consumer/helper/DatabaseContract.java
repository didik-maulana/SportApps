package com.codingtive.consumer.helper;

import android.net.Uri;

public class DatabaseContract {
    private static final String AUTHORITY = "com.codingtive.sportapps.provider";
    private static final String SCHEME = "content";

    public static final class AppDatabase {
        static final String TABLE_NAME = "sport";

        static final String SPORT_TITLE = "strSport";
        static final String SPORT_IMAGE = "strSportThumb";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
