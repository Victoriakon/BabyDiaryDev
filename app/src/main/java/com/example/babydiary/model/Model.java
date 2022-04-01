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

    private Model(){
        for(int i=0;i<100;i++){
            BabyDetails bd = new BabyDetails("name",""+i,"");
            data.add(bd);
        }
    }

    List<BabyDetails> data = new LinkedList<BabyDetails>();

    public List<BabyDetails> getAllBabyDetails(){
        return data;
    }

    public void addBabyDetails(BabyDetails babydetails){
        data.add(babydetails);
    }

    public BabyDetails getBabyDetailsById(String babydetailstId) {
        for (BabyDetails bd:data
        ) {
            if (bd.getMonth_id().equals(babydetailstId)){
                return bd;
            }
        }
        return null;
    }

}
