package com.example.babydiary.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BabyDetailsDao {
    @Query("select * from BabyDetails")
    List<BabyDetails> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(BabyDetails... babydetails);
    @Delete
    void delete(BabyDetails babydetails);
}

