package com.example.babydiary.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.babydiary.R;
import com.example.babydiary.model.BabyDetails;
import com.example.babydiary.model.Model;
import com.example.babydiary.model.ModelRegimen;
import com.example.babydiary.model.Regimen;
import com.squareup.picasso.Picasso;


public class RegimenFragment extends Fragment {

//    String user_id;

    TextView time;
    TextView recomendation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_regimen, container, false);

//        user_id=RegimenFragmentArgs.fromBundle(getArguments()).getUserId();


//        String regId=RegimenFragmentArgs.fromBundle(getArguments()).getRegDet();
        String regId=RegimenFragmentArgs.fromBundle(getArguments()).getRegDet();
        ModelRegimen.instance.getRegimenById(regId, new ModelRegimen.GetRegimenById() {
            @Override
            public void onComplete(Regimen regimen) {

                time.setText(regimen.getTime());
                recomendation.setText(regimen.getRecomendation());


            }
        });

        time = view.findViewById(R.id.time);
        recomendation = view.findViewById(R.id.recomendation);


        Button backBtn = view.findViewById(R.id.details_back_btn);
        backBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigateUp();
        });
        return view;
    }
}


