package com.example.babydiary.modelRegimen;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.babydiary.MyApplication;
import com.example.babydiary.model.BabyDetails;
import com.example.babydiary.model.BabyDetailsDao;

@Database(entities = {Regimen.class},version = 3)
abstract class AppLocalDbRepository extends RoomDatabase {
        public abstract RegimenDao regimenDao();
}


public class AppLocalDbReg {
    static public com.example.babydiary.modelRegimen.AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.getContext(),
                    com.example.babydiary.modelRegimen.AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}
