package com.example.babydiary.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.babydiary.R;
import com.example.babydiary.model.User;
import com.example.babydiary.model.UserViewModel;
import com.squareup.picasso.Picasso;


public class HomeFragment extends Fragment {

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
        profile=view.findViewById(R.id.home_profile_imv);
        desc=view.findViewById(R.id.image_desc);
        regim=view.findViewById(R.id.image_regim);
        gallery=view.findViewById(R.id.image_gallery);

        desc.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToBabyDetailsListRvFragment(user_id)));
        regim.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToRegimenListRvFragment(user_id)));
//        gallery.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToBabyGalleryFragment2(()));

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserById(user_id, new UserViewModel.GetUserListener() {
            @Override
            public void onComplete(User user) {

                if (user.getImageUrl() != null) {
                    Picasso.get()
                            .load(user.getImageUrl())
                            .into(profile);
                }
            }
        });

        profile.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToUserProfileFragment(user_id)));
        return view;
    }
}