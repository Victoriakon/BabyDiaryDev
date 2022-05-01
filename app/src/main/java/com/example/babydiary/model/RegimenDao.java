package com.example.babydiary.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RegimenDao {
    @Query("select * from Regimen")
    List<Regimen> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Regimen... regimen);

    @Delete
    void delete(Regimen regimen);
}
