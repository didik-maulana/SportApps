package com.codingtive.sportapps.data.database;

import android.content.Context;

import androidx.room.Room;

public class RoomClient {
    private static final String DB_NAME = "sport_db";
    private static RoomClient INSTANCE;
    private SportDatabase sportDatabase;

    private RoomClient(Context context) {
        sportDatabase = Room.databaseBuilder(
                context.getApplicationContext(),
                SportDatabase.class,
                DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public static synchronized RoomClient getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new RoomClient(context);
        }
        return INSTANCE;
    }

    public SportDatabase getSportDatabase() {
        return sportDatabase;
    }
}
