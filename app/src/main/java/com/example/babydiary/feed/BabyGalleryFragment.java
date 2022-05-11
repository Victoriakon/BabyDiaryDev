package com.example.babydiary.feed;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.babydiary.R;
import com.example.babydiary.model.BabyDetails;
import com.example.babydiary.model.Model;
import com.example.babydiary.model.User;

public class BabyGalleryFragment extends Fragment {
    private static final int REQUEST_CAMERA=1;

    Button saveBtn;
    ImageButton camBtn;
    ImageButton galleryBtn;
    ImageView babyImg;
    String user_id;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baby_gallery,container, false);
        saveBtn=view.findViewById(R.id.save_btn);
        camBtn=view.findViewById(R.id.main_cam_btn);
        galleryBtn=view.findViewById(R.id.main_gallery_btn);
        babyImg=view.findViewById(R.id.baby_img);
        babyImg.setTag("");

//        user_id=HomeFragmentArgs.fromBundle(getArguments()).getUserId();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Navigation.findNavController(v).navigateUp();
            }
        });

        camBtn=view.findViewById(R.id.main_cam_btn);
        galleryBtn=view.findViewById(R.id.main_gallery_btn);
        camBtn.setOnClickListener(v->{
            openCam();
        });
        galleryBtn.setOnClickListener(v->{
            openGallery();
        });
        return view;
    }
    private void openGallery() {
    }

    private void openCam() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CAMERA);
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQUEST_CAMERA){
            if(resultCode== Activity.RESULT_OK){
                Bundle extras=data.getExtras();

            }
        }
    }


    private void save() {

        saveBtn.setEnabled(false);
//        cancelBtn.setEnabled(false);
        camBtn.setEnabled(false);
        galleryBtn.setEnabled(false);

    }
}