package com.example.babydiary.modelRegimen;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.babydiary.model.BabyDetails;
import com.example.babydiary.modelRegimen.Regimen;

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
