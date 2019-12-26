package com.codingtive.sportapps.data.dao;

import com.codingtive.sportapps.data.model.Sport;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface SportDao {
    @Query("SELECT * FROM sport")
    List<Sport> getSportList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavorite(Sport sport);

    @Query("DELETE FROM sport WHERE idSport=:id")
    void removeFavorite(String id);

    @Query("SELECT * FROM sport WHERE idSport=:id")
    Sport getSport(String id);
}
