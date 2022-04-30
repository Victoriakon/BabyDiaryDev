package com.example.babydiary.feed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.babydiary.model.ModelRegimen;
import com.example.babydiary.model.Regimen;

import java.util.List;

public class RegimenListViewModel extends ViewModel {
    LiveData<List<Regimen>> data;

    public RegimenListViewModel(){
        data = ModelRegimen.instance.getAll();
    }
    public LiveData<List<Regimen>> getData() {
        return data;
    }
}