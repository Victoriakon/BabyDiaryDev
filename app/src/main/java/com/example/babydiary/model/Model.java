package com.example.babydiary.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    public static final Model instance = new Model();
    Executor executor=Executors.newFixedThreadPool(1);
    Handler mainThread =HandlerCompat.createAsync(Looper.getMainLooper());

    private Model(){

    }
     public interface GetAllBabysListener{
        void onComplete(List<BabyDetails> list);
     }
     public void getAllBabyDetail(GetAllBabysListener listener){
        executor.execute(()->{
            List<BabyDetails> list=AppLocalDb.db.babydetailsDao().getAll();
            mainThread.post(()->{
                listener.onComplete(list);
            });
        });
     }


    public void addBabyDetails(BabyDetails babydetails,AddBabyDetailListener listener){
        executor.execute(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AppLocalDb.db.babydetailsDao().insertAll(babydetails);
            mainThread.post(()->{
                listener.onComplete();
            });
        });

    }

    public BabyDetails getBabyDetailsById(String babydetailstId) {
        return null;
    }

    public interface AddBabyDetailListener {
        void onComplete();
    }
}
