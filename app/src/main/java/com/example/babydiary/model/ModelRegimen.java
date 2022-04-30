package com.example.babydiary.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.babydiary.MyApplication;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ModelRegimen {

    public static final ModelRegimen instance = new ModelRegimen();
    Executor executor= Executors.newFixedThreadPool(1);
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());


    public enum RegimenListLoadingState{
        loading,
        loaded
    }
    MutableLiveData<ModelRegimen.RegimenListLoadingState> regimenListLoadingState=new MutableLiveData<RegimenListLoadingState>();
    public LiveData<ModelRegimen.RegimenListLoadingState> getRegimenListLoadingState(){
        return regimenListLoadingState;
    }


    ModelFirebaseReg modelFirebaseReg=new ModelFirebaseReg();

    private ModelRegimen(){
       regimenListLoadingState.setValue(ModelRegimen.RegimenListLoadingState.loaded);
    }

    MutableLiveData<List<Regimen>> regimenList=new MutableLiveData<List<Regimen>>();
    public LiveData<List<Regimen>> getAll(){
        if(regimenList.getValue()==null) {refreshRegimenList();};
        return regimenList;
    }

    public void refreshRegimenList() {
        regimenListLoadingState.setValue(RegimenListLoadingState.loading);

        //get last local update date
        Long lastUpdateDate= MyApplication.getContext().getSharedPreferences("TAG",Context.MODE_PRIVATE).getLong("RegimenUpdateDate",0);

        executor.execute(()->{
            List<Regimen> rgList=AppLocalDb.db.regimenDao().getAll();
            regimenList.postValue(rgList);
        });

        //firebase get all updates since lastLocalUpdateDate
        modelFirebaseReg.getAllRegimen(lastUpdateDate,new ModelFirebaseReg.GetAllRegimenListener(){

            @Override
            public void onComplete(List<Regimen> list) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Long lud=new Long(0);
                        Log.d("TAG","fb returned"+list.size());
                        for(Regimen regimen:list){
                            AppLocalDb.db.regimenDao().insertAll(regimen);
                            if(lud<regimen.getUpdateDate()){
                                lud=regimen.getUpdateDate();
                            }
                        }
                        //update last local update date
                        MyApplication.getContext()
                                .getSharedPreferences("TAG",Context.MODE_PRIVATE)
                                .edit()
                                .putLong("RegimenLastUpdateDate",lud)
                                .commit();
                        //return all date to caller
                        List<Regimen> rgList=AppLocalDb.db.regimenDao().getAll();
                        regimenList.postValue(rgList);
                        regimenListLoadingState.postValue(ModelRegimen.RegimenListLoadingState.loaded);
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



    public void addRegimen(Regimen regimen, AddRegimenListener listener) {

        modelFirebaseReg.addRegimen(regimen, () -> {
            listener.onComplete();
            refreshRegimenList();
        });
    }
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



    public interface GetRegimenById{
        void onComplete(Regimen regimen);
    }
    public Regimen getRegimenById(String regimenId, ModelRegimen.GetRegimenById listener) {
        modelFirebaseReg.getRegimenById(regimenId,listener);
        return null;
    }

    public interface AddRegimenListener {
        void onComplete();
    }


}