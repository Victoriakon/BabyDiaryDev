package com.example.babydiary.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class BabyDetails {
      public static final String COLLECTION_NAME = "babydetails";
      @PrimaryKey
@NonNull
      String month_id="" ;
      String description="";



      //      String uri="";
//      Long updateDate=new Long(0);

      public BabyDetails(){}
      public BabyDetails(String month_id,String description){
            this.month_id=month_id;
            this.description=description;
//            this.uri=uri;

      }

      public static BabyDetails create(Map<String, Object> json) {
            String month_id = (String) json.get("month_id");
            String description = (String) json.get("description");

            BabyDetails babydetails=new BabyDetails(month_id,description);
            return babydetails;
      }


      public String getMonth_id() {
            return month_id;
      }

      public void setMonth_id(String month_id) {
            this.month_id = month_id;
      }

      public String getDescription() {
            return description;
      }

      public void setDescription(String description) {
            this.description = description;
      }

      public Map<String, Object> toJson() {
            Map<String,Object> json = new HashMap<String,Object>();
            json.put("month_id", month_id);
            json.put("description", description);
            return json;
      }

//      public String getUri() {
//            return uri;
//      }

//      public void setUri(String uri) {
//            this.uri = uri;
//      }

//      public Long getUpdateDate() {
//            return updateDate;
//      }

//      public void setUpdateDate(Long updateDate) {
//            this.updateDate = updateDate;
//      }
}
