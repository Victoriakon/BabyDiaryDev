package com.example.babydiary.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.babydiary.MyApplication;

@Database(entities = {BabyDetails.class,Regimen.class},version = 4)
abstract class AppLocalDbRepository extends RoomDatabase {
     public abstract BabyDetailsDao babydetailsDao();
     public abstract RegimenDao regimenDao();
}



public class AppLocalDb {
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.getContext(),
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}
