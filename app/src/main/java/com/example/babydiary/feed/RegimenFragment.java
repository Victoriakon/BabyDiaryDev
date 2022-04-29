package com.example.babydiary.feed;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.babydiary.R;
import com.example.babydiary.modelRegimen.ModelRegimen;
import com.google.firebase.auth.internal.RecaptchaActivity;


public class RegimenFragment extends Fragment {

    RegimenListViewModel viewModel;
    BabyDetailsListRvFragment.MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        viewModel=new ViewModelProvider(this).get(RegimenListViewModel.class);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_regimen, container, false);
        swipeRefresh=view.findViewById(R.id.regimen_swiperefresh);
        swipeRefresh.setOnRefreshListener(()-> ModelRegimen.instance.refreshRegimenList());

        RecyclerView list=view.findViewById(R.id.regimen_rv);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        setHasOptionsMenu(true);
        viewModel.getData().observe(getViewLifecycleOwner(),list1->refresh());
        swipeRefresh.setRefreshing(ModelRegimen.instance.getRegimenListLoadingState().getValue()==ModelRegimen.RegimenListLoadingState.loading);
        ModelRegimen.instance.getRegimenListLoadingState().observe(getViewLifecycleOwner(),regimenListLoadingState -> {
            if(regimenListLoadingState==ModelRegimen.RegimenListLoadingState.loading){
                swipeRefresh.setRefreshing(true);
            }else{
                swipeRefresh.setRefreshing(false);
            }
        });

        return view;

    }

    private void refresh(){adapter.notifyDataSetChanged();}

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView recomendation;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
             = itemView.findViewById(R.id.listrow_name_tv);
            idTv = itemView.findViewById(R.id.listrow_id_tv);
            cb = itemView.findViewById(R.id.listrow_cb);
            avatarImv = itemView.findViewById(R.id.listrow_avatar_imv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(v,pos);
                }
            });
        }


    }
}
