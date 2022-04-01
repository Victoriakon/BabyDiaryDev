package com.example.babydiary.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.babydiary.R;
import com.example.babydiary.model.BabyDetails;
import com.example.babydiary.model.Model;

public class BabyDetailsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_babydetails, container, false);



        String babyId=BabyDetailsFragmentArgs.fromBundle(getArguments()).getBabyDet();
        BabyDetails babydetails = Model.instance.getBabyDetailsById(babyId);

        TextView description = view.findViewById(R.id.details_desc_tv);
        TextView month_id = view.findViewById(R.id.details_month_tv);

        description.setText(babydetails.getDescription());
        month_id.setText(babydetails.getMonth_id());

        Button backBtn = view.findViewById(R.id.details_back_btn);
        backBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigateUp();
        });
        return view;
    }
    }
