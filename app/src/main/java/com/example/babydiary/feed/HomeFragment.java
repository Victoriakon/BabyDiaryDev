package com.example.babydiary.feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.babydiary.MainActivity2;
import com.example.babydiary.R;
import com.example.babydiary.RegimenActivity;
import com.example.babydiary.model.User;
import com.example.babydiary.model.UserViewModel;
import com.squareup.picasso.Picasso;


public class HomeFragment extends Fragment{

    ImageButton desc;
    ImageButton regim;
    ImageButton gallery;

    ImageView profile;
    UserViewModel userViewModel;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        String user_id = HomeFragmentArgs.fromBundle(getArguments()).getUserId();

//        profile=view.findViewById(R.id.home_profile_imv);
        desc=view.findViewById(R.id.image_desc);
        gallery=view.findViewById(R.id.image_gallery);
        regim=view.findViewById(R.id.image_regim);
//        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//        userViewModel.getUserById(user_id, new UserViewModel.GetUserListener() {
//            @Override
//            public void onComplete(User user) {
//
//                if (user.getImageUrl() != null) {
//                    Picasso.get()
//                            .load(user.getImageUrl())
//                            .into(profile);
//                }
//            }
//        });

        desc.setOnClickListener(v ->{
            toFeedActivity();
        });
        regim.setOnClickListener(v->{
            toRegimActivity();
        });
        regim.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToRegimenActivity()));
        desc.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToBabydetailsNavGraph()));
        gallery.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToBabyGalleryFragment3(user_id)));
//        profile.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToUserProfileFragment(user_id)));

        return view;


//        String user_id = HomeFragmentArgs.fromBundle(getArguments()).getUserId();
//
//        profile=view.findViewById(R.id.home_profile_imv);
//        desc=view.findViewById(R.id.image_desc);
//        regim=view.findViewById(R.id.image_regim);
//        gallery=view.findViewById(R.id.image_gallery);
//
////        desc.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToBabyDetailsListRvFragment()));
////        regim.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToRegimenListRvFragment(user_id)));
//        gallery.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToBabyGalleryFragment3(user_id)));
//
////        desc.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToBabydetailsNavGraph()));
//
////        desc.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToBabyDetailsListRvFragment()));
////            desc.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToBabydetailsNavGraph()));
//
////        desc.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToBabydetailsNavGraph2()));
//
//
//        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//        userViewModel.getUserById(user_id, new UserViewModel.GetUserListener() {
//            @Override
//            public void onComplete(User user) {
//
//                if (user.getImageUrl() != null) {
//                    Picasso.get()
//                            .load(user.getImageUrl())
//                            .into(profile);
//                }
//            }
//        });
//
////        desc.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToBabyDetailsListRvFragment(user_id)));
//
//        profile.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToUserProfileFragment(user_id)));
//        return view;
//    }
//
//    @Override
//    public void onClick(View view) {

    }

    private void toRegimActivity() {
        Intent intent=new Intent(getContext(), RegimenActivity.class);
        getActivity().finish();
    }

    private void toFeedActivity() {
        Intent intent = new Intent(getContext(), MainActivity2.class);
        startActivity(intent);
        getActivity().finish();
    }
}