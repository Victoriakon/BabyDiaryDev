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
import com.example.babydiary.modelRegimen.ModelRegimen;
import com.example.babydiary.modelRegimen.Regimen;


public class RegimenDetailsFragment extends Fragment {

    EditText time;
    EditText recomendation;
    Button saveBtn;
    Button cancelBtn;
    ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_babydetails,container, false);
        time=view.findViewById(R.id.time);
        recomendation=view.findViewById(R.id.recomendation);
        saveBtn = view.findViewById(R.id.main_save_btn);
        cancelBtn = view.findViewById(R.id.main_cancel_btn);
        progressBar = view.findViewById(R.id.main_progressbar);
        progressBar.setVisibility(View.GONE);

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

        String timeId=time.getText().toString() ;
        String recom=recomendation.getText().toString();

        Log.d("TAG","saved time:" + timeId + " recomendtion:" + recom );
        Regimen regimen = new Regimen(timeId,recom);
        ModelRegimen.instance.addRegimen(regimen,()->{
        });
        Navigation.findNavController(time).navigateUp();
    }
}