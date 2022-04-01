package com.example.babydiary.model;

public class BabyDetails {

      String month_id="" ;
      String description="";
      String uri="";
      Long updateDate=new Long(0);

      public BabyDetails(){}
      public BabyDetails(String month_id,String description,String uri){
            this.month_id=month_id;
            this.description=description;
            this.uri=uri;
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

      public String getUri() {
            return uri;
      }

      public void setUri(String uri) {
            this.uri = uri;
      }

      public Long getUpdateDate() {
            return updateDate;
      }

      public void setUpdateDate(Long updateDate) {
            this.updateDate = updateDate;
      }
}
