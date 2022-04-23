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
    Executor executor=Executors.newFixedThreadPool(2);
    Handler mainThread =HandlerCompat.createAsync(Looper.getMainLooper());

    ModelFirebase modelFirebase=new ModelFirebase();

    private Model(){

    }
     public interface GetAllBabysListener{
        void onComplete(List<BabyDetails> list);
     }
     public void getAllBabyDetail(GetAllBabysListener listener){
        modelFirebase.getAllBabyDetail(listener);
//        executor.execute(()->{
//            List<BabyDetails> list=AppLocalDb.db.babydetailsDao().getAll();
//            mainThread.post(()->{
//                listener.onComplete(list);
//            });
//        });
     }


    public void addBabyDetails(BabyDetails babydetails,AddBabyDetailListener listener){

        modelFirebase.addBabyDetails(babydetails,listener);
//        executor.execute(()->{
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            AppLocalDb.db.babydetailsDao().insertAll(babydetails);
//            mainThread.post(()->{
//                listener.onComplete();
//            });
//        });

    }

    public interface GetBabyDetailsById{
        void onComplete(BabyDetails babydetails);
    }
    public BabyDetails getBabyDetailsById(String babydetailsId,GetBabyDetailsById listener) {
        modelFirebase.getBabyDetailsById(babydetailsId,listener);
        return null;
    }

    public interface AddBabyDetailListener {
        void onComplete();
    }
}
