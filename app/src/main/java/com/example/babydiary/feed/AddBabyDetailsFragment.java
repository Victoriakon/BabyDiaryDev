package com.example.babydiary.feed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.babydiary.R;
import com.example.babydiary.model.BabyDetails;
import com.example.babydiary.model.Model;

public class AddBabyDetailsFragment extends Fragment {

    EditText descriptionEt;
    EditText month_idEt;
    Button saveBtn;
    Button cancelBtn;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_babydetails,container, false);
        descriptionEt = view.findViewById(R.id.main_desc_et);
        month_idEt = view.findViewById(R.id.main_month_et);
        saveBtn = view.findViewById(R.id.main_save_btn);
        cancelBtn = view.findViewById(R.id.main_cancel_btn);
//        progressBar = view.findViewById(R.id.main_progressbar);
//        progressBar.setVisibility(View.GONE);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        return view;
    }


    private void save() {
        progressBar.setVisibility(View.VISIBLE);
        saveBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        String month_id=month_idEt.getText().toString() ;
        String description=descriptionEt.getText().toString();
        String uri="";

        Log.d("TAG","saved name:" + month_id + " id:" + description );
        BabyDetails babydetails = new BabyDetails(month_id,description,uri);
        Model.instance.addBabyDetails(babydetails,()->{
        });
        Navigation.findNavController(month_idEt).navigateUp();

    }
}