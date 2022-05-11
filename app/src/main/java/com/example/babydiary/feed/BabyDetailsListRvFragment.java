package com.example.babydiary.feed;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.babydiary.R;
import com.example.babydiary.model.BabyDetails;
import com.example.babydiary.model.Model;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BabyDetailsListRvFragment extends Fragment {

    BabyDetailsListRvViewModel viewModel;
    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    //    String user_id;
    String selected_menu;

    ImageView imagev;

    @Override
    public  void onAttach(@NonNull Context context){
        super.onAttach(context);
        viewModel=new ViewModelProvider(this).get(BabyDetailsListRvViewModel.class);
         }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_babydetails_list_rv, container, false);

//        user_id=BabyDetailsFragmentArgs.fromBundle(getArguments()).getUserId();

        swipeRefresh = view.findViewById(R.id.babydetails_swiperefresh);
        swipeRefresh.setOnRefreshListener(() -> Model.instance.refreshBabyDetailsList());
//        progressBar=view.findViewById(R.id.babydetails_progressbar);
//        progressBar.setVisibility(View.GONE);

        RecyclerView list = view.findViewById(R.id.babydetails_rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyAdapter();
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                String babyId = viewModel.getData().getValue().get(position).getMonth_id();
//                String babyId=viewModel.getData().getValue().get(position).getMonth_id();
                String babyId=viewModel.getData().getValue().get(position).getMonth_id();
//                Navigation.findNavController(v).navigate(BabyDetailsListRvFragmentDirections.actionBabyDetailsListRvFragmentToBabyDetailsFragment(babyId));
//                Navigation.findNavController(v).navigate(BabyDetailsListRvFragmentDirections.actionBabyDetailsListRvFragmentToBabyDetailsFragment(babyId));

                Navigation.findNavController(v).navigate(BabyDetailsListRvFragmentDirections.actionBabyDetailsListRvFragmentToBabyDetailsFragment(babyId));
            }
        });

        setHasOptionsMenu(true);
        viewModel.getData().observe(getViewLifecycleOwner(),list1 -> refresh());
        swipeRefresh.setRefreshing(Model.instance.getBabyDetailsListLoadingState().getValue()==Model.BabyDetailsListLoadingState.loading);
        Model.instance.getBabyDetailsListLoadingState().observe(getViewLifecycleOwner(),babyDetailsListLoadingState -> {
            if(babyDetailsListLoadingState==Model.BabyDetailsListLoadingState.loading){
                swipeRefresh.setRefreshing(true);
            }
            else{
                swipeRefresh.setRefreshing(false);
            }
        });
        Button homeBtn = view.findViewById(R.id.home_btn);
        homeBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigateUp();
        });
        return view;
    }

//

    private void refresh() {
//
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
//
    }

    //
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView descTv;
        TextView monthTv;
        ImageView avatarImv;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            avatarImv = itemView.findViewById(R.id.listrow_avatar_imv);
//            descTv = itemView.findViewById(R.id.listrow_desc_tv);
            monthTv = itemView.findViewById(R.id.listrow_month_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(v,pos);
                }
            });
        }

        void bind(BabyDetails babydetails){
            monthTv.setText(babydetails.getMonth_id());
            avatarImv.setImageResource(R.drawable.baby);
            if(babydetails.getImage()!=null){
                Picasso.get()
                        .load(babydetails.getImage())
                        .into(avatarImv);
            }
        }
    }

    interface OnItemClickListener{
        void onItemClick(View v,int position);
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        OnItemClickListener listener;
        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.babydetails_list_row,parent,false);
            MyViewHolder holder = new MyViewHolder(view,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            BabyDetails babydetails= viewModel.getData().getValue().get(position);
            holder.bind(babydetails);
//            BabyDetails babydetails = data.get(position);
//            holder.descTv.setText(babydetails.getDescription());
//            holder.monthTv.setText(babydetails.getMonth_id());
        }

        @Override
        public int getItemCount() {
            if(viewModel.getData().getValue()==null){
                return  0;
            }
            return  viewModel.getData().getValue().size();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.babydetails_list_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addBabyDetailsFragment){
            Log.d("TAG","ADD...");
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}