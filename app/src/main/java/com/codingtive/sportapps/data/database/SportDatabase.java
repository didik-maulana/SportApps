package com.codingtive.sportapps.data.database;

import com.codingtive.sportapps.data.dao.SportDao;
import com.codingtive.sportapps.data.model.Sport;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Sport.class, exportSchema = false, version = 1)
public abstract class SportDatabase extends RoomDatabase {
    public abstract SportDao getSportDao();
}
