package com.example.babydiary.feed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.babydiary.model.BabyDetails;
import com.example.babydiary.model.Model;

import java.util.List;

public class BabyDetailsListRvViewModel extends ViewModel {
    LiveData<List<BabyDetails>> data;


    public  BabyDetailsListRvViewModel(){data= Model.instance.getAll(); }
    public  LiveData<List<BabyDetails>> getData(){return data;}
}
