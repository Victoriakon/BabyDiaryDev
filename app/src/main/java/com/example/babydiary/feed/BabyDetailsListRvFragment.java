package com.example.babydiary.feed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.babydiary.R;
import com.example.babydiary.model.BabyDetails;
import com.example.babydiary.model.Model;

import java.util.List;

public class BabyDetailsListRvFragment extends Fragment {

    List<BabyDetails> data;
    MyAdapter adapter;
//    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefresh;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_babydetails_list_rv, container, false);

        swipeRefresh = view.findViewById(R.id.babydetails_swiperefresh);
        swipeRefresh.setOnRefreshListener(() -> refresh());
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
                String babyId = data.get(position).getMonth_id();
//                Navigation.findNavController(v).navigate(BabyDetailsListRvFragmentDirections.actionBabyDetailsListRvFragmentToBabyDetailsFragment(babyId));
                  Navigation.findNavController(v).navigate(BabyDetailsListRvFragmentDirections.actionBabyDetailsListRvFragmentToBabyDetailsFragment(babyId));

            }
        });

        setHasOptionsMenu(true);
        refresh();
        return view;
    }

//        ImageButton add = view.findViewById(R.id.babydetails_add_btn);
////        add.setOnClickListener((v)->{
////            Navigation.findNavController(v).navigate(R.id.action_studentListRvFragment_to_studentDetailsFragment);
////        });
//        add.setOnClickListener(Navigation.createNavigateOnClickListener(BabyDetailsListRvFragmentDirections.actionGlobalAboutFragment()));
//        setHasOptionsMenu(true);
//        return view;
//    }

    private void refresh() {
        swipeRefresh.setRefreshing(true);
        Model.instance.getAllBabyDetail((list)->{
            data=list;
            adapter.notifyDataSetChanged();
            swipeRefresh.setRefreshing(false);
        });
    }

//    private void refresh(){
//        progressBar.setVisibility(View.VISIBLE);
//        Model.instance.getAllBabyDetail((list)->{
//            data=list;
//            adapter.notifyDataSetChanged();
//            progressBar.setVisibility(View.GONE);
//        });
//    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView descTv;
        TextView monthTv;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
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
            BabyDetails babydetails = data.get(position);
//            holder.descTv.setText(babydetails.getDescription());
            holder.monthTv.setText(babydetails.getMonth_id());
        }

        @Override
        public int getItemCount() {
            if(data==null){
                return 0;
            }
            return data.size();
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