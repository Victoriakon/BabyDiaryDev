package com.example.babydiary.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.babydiary.MyApplication;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    public static final Model instance = new Model();
    Executor executor=Executors.newFixedThreadPool(1);
    Handler mainThread =HandlerCompat.createAsync(Looper.getMainLooper());


    public enum BabyDetailsListLoadingState{
        loading,
        loaded
    }
    MutableLiveData<BabyDetailsListLoadingState> babydetailsListLoadingState=new MutableLiveData<BabyDetailsListLoadingState>();
    public LiveData<BabyDetailsListLoadingState> getBabyDetailsListLoadingState(){
        return babydetailsListLoadingState;
    }


    ModelFirebase modelFirebase=new ModelFirebase();

    private Model(){
        babydetailsListLoadingState.setValue(BabyDetailsListLoadingState.loaded);
    }

    MutableLiveData<List<BabyDetails>> babydetailsList=new MutableLiveData<List<BabyDetails>>();
    public LiveData<List<BabyDetails>> getAll(){
        if(babydetailsList.getValue()==null) {refreshBabyDetailsList();};
        return babydetailsList;
    }

    public void refreshBabyDetailsList() {
        babydetailsListLoadingState.setValue(BabyDetailsListLoadingState.loading);

        //get last local update date
        Long lastUpdateDate= MyApplication.getContext().getSharedPreferences("TAG",Context.MODE_PRIVATE).getLong("BabyDetailsUpdateDate",0);

        //firebase get all updates since lastLocalUpdateDate
        modelFirebase.getAllBabyDetails(lastUpdateDate,new ModelFirebase.GetAllBabyDetailsListener(){

            @Override
            public void onComplete(List<BabyDetails> list) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Long lud=new Long(0);
                        Log.d("TAG","fb returned"+list.size());
                        for(BabyDetails babydetails:list){
                            AppLocalDb.db.babydetailsDao().insertAll(babydetails);
                            if(lud<babydetails.getUpdateDate()){
                                lud=babydetails.getUpdateDate();
                            }
                        }
                        //update last local update date
                        MyApplication.getContext()
                                .getSharedPreferences("TAG",Context.MODE_PRIVATE)
                                .edit()
                                .putLong("BabyDetailsLastUpdateDate",lud)
                                .commit();
                        //return all date to caller
                        List<BabyDetails> bdList=AppLocalDb.db.babydetailsDao().getAll();
                        babydetailsList.postValue(bdList);
                        babydetailsListLoadingState.postValue(BabyDetailsListLoadingState.loaded);
                    }
                });
            }
        });
    }


//     public void getAllBabyDetail(ModelFirebase.GetAllBabyDetailsListener listener){
//        modelFirebase.getAllBabyDetail(listener);
////        executor.execute(()->{
//            List<BabyDetails> list=AppLocalDb.db.babydetailsDao().getAll();
//            mainThread.post(()->{
//                listener.onComplete(list);
//            });
//        });



    public void addBabyDetails(BabyDetails babydetails,AddBabyDetailListener listener){

        modelFirebase.addBabyDetails(babydetails,()->{
            listener.onComplete();
            refreshBabyDetailsList();
                });
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

    public interface SaveImageListener{
        void onComplete(String url);
    }

    public void saveImage(Bitmap imageBitmap, String imageName,SaveImageListener listener){
        modelFirebase.saveImage(imageBitmap,imageName,listener);

    }
}
