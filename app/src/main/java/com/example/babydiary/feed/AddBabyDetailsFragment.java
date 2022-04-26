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

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.babydiary.R;
import com.example.babydiary.model.BabyDetails;
import com.example.babydiary.model.Model;

public class AddBabyDetailsFragment extends Fragment {
    private static final int REQUEST_CAMERA=1;

    EditText descriptionEt;
    EditText month_idEt;
    Button saveBtn;
    Button cancelBtn;
    ProgressBar progressBar;
    Bitmap imageBitmap;
    ImageView avatarImv;
    ImageButton camBtn;
    ImageButton galleryBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_babydetails,container, false);
        descriptionEt = view.findViewById(R.id.main_desc_et);
        month_idEt = view.findViewById(R.id.main_month_et);
        saveBtn = view.findViewById(R.id.main_save_btn);
        cancelBtn = view.findViewById(R.id.main_cancel_btn);
        progressBar = view.findViewById(R.id.main_progressbar);
        progressBar.setVisibility(View.GONE);
        avatarImv=view.findViewById(R.id.main_avatar_imv);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
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
                imageBitmap=(Bitmap) extras.get("data");
                avatarImv.setImageBitmap(imageBitmap);
            }
        }
    }


    private void save() {
        progressBar.setVisibility(View.VISIBLE);
        saveBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        camBtn.setEnabled(false);
        galleryBtn.setEnabled(false);

        String month_id=month_idEt.getText().toString() ;
        String description=descriptionEt.getText().toString();
//        String uri="";

        Log.d("TAG","saved name:" + month_id + " id:" + description );
        BabyDetails babydetails = new BabyDetails(month_id,description);
        if(imageBitmap==null){
        Model.instance.addBabyDetails(babydetails,()->{
               Navigation.findNavController(descriptionEt).navigateUp();
        });
    }
        else {
            Model.instance.saveImage(imageBitmap, month_id + ".jpg", url -> {
                babydetails.setImage(url);
                Model.instance.addBabyDetails(babydetails, () -> {
                    Navigation.findNavController(month_idEt).navigateUp();
                });
            });
        }
        }
}