package com.example.babydiary.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity

public class Regimen {
    public static final String COLLECTION_NAME = "regimen";

    @PrimaryKey
    @NonNull
    String time="" ;
    String recomendation="";
    Long updateDate=new Long(0);

    public Long getUpdateDate() {
        return updateDate;
    }
    public  void setUpdateDate(Long updateDate){ this.updateDate=updateDate;}


    public Regimen(@NonNull String time, String recomendation) {
        this.time = time;
        this.recomendation = recomendation;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }

    public String getRecomendation() {
        return recomendation;
    }

    public void setRecomendation(String recomendation) {
        this.recomendation = recomendation;
    }



    public Map<String, Object> toJson() {
        Map<String,Object> json = new HashMap<String,Object>();
        json.put("time", time);
        json.put("recomendation", recomendation);
        json.put("updateDate", FieldValue.serverTimestamp());

        return json;
    }

    public static Regimen create(Map<String, Object> json) {
        String time = (String) json.get("time");
        String recomendation = (String) json.get("recomendation");
        Timestamp ts=(Timestamp)json.get("updateDate");
        Long updateDate=ts.getSeconds();

        Regimen regimen=new Regimen(time,recomendation);
        regimen.setUpdateDate(updateDate);
        return regimen;
    }

    public String getId() {return time;}
}
