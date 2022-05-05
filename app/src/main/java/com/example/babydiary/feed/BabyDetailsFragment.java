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
import com.squareup.picasso.Picasso;

public class BabyDetailsFragment extends Fragment {

    String user_id;

    TextView descTv;
    TextView monthTv;
    ImageView avatarImv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_babydetails, container, false);

//        user_id=BabyDetailsFragmentArgs.fromBundle(getArguments()).getUserId();


        String babyId=BabyDetailsFragmentArgs.fromBundle(getArguments()).getBabyDet();
//        BabyDetails babydetails = Model.instance.getBabyDetailsById(babyId);
        Model.instance.getBabyDetailsById(babyId,new Model.GetBabyDetailsById(){

            @Override
            public void onComplete(BabyDetails babydetails) {

                monthTv.setText(babydetails.getMonth_id());
                descTv.setText(babydetails.getDescription());
                if(babydetails.getImage()!=null){
                    Picasso.get().load(babydetails.getImage()).into(avatarImv);
                }

            }
        });

        monthTv = view.findViewById(R.id.details_month_tv);
        descTv = view.findViewById(R.id.details_desc_tv);
        avatarImv=view.findViewById(R.id.details_avatar_img);


        Button backBtn = view.findViewById(R.id.details_back_btn);
        backBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigateUp();
        });
        return view;
    }
}
