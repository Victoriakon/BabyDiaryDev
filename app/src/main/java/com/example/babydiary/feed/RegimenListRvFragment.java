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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.babydiary.R;
import com.example.babydiary.model.ModelRegimen;
import com.example.babydiary.model.Regimen;


public class RegimenListRvFragment extends Fragment {

    RegimenListViewModel viewModel;
    BabyDetailsListRvFragment.MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
//    String user_id;

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        viewModel=new ViewModelProvider(this).get(RegimenListViewModel.class);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_regimen_list_rv, container, false);

//        user_id=RegimenListRvFragmentArgs.fromBundle(getArguments()).getUserId();
        swipeRefresh=view.findViewById(R.id.regimen_swiperefresh);
        swipeRefresh.setOnRefreshListener(()-> ModelRegimen.instance.refreshRegimenList());

        RecyclerView list=view.findViewById(R.id.regimen_rv);
        list.setLayoutManager(new LinearLayoutManager(getContext()));



        list.setLayoutManager(new LinearLayoutManager(getContext()));

//        adapter = new RegimenListRvFragment().MyAdapter();
//        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new BabyDetailsListRvFragment.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String regId = viewModel.getData().getValue().get(position).getTime();
//                Navigation.findNavController(v).navigate(BabyDetailsListRvFragmentDirections.actionBabyDetailsListRvFragmentToBabyDetailsFragment(babyId));
//                Navigation.findNavController(v).navigate(BabyDetailsListRvFragmentDirections.actionBabyDetailsListRvFragmentToBabyDetailsFragment(regId));

                Navigation.findNavController(v).navigate(RegimenListRvFragmentDirections.actionRegimenListRvFragmentToRegimenFragment(regId));
            }
        });


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

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView recomendation;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            recomendation = itemView.findViewById(R.id.recomendation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(v, pos);
                }
            });
        }

        void bind(Regimen regimen) {
            time.setText(regimen.getTime());
            recomendation.setText(regimen.getRecomendation());
        }
    }

    interface OnItemClickListener{
            void onItemClick(View v, int position);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        OnItemClickListener listener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.regimen_list_row, parent, false);
            MyViewHolder holder = new MyViewHolder(view, listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Regimen regimen = viewModel.getData().getValue().get(position);
            holder.bind(regimen);
        }

        @Override
        public int getItemCount() {
            if (viewModel.getData().getValue() == null) {
                return 0;
            }
            return viewModel.getData().getValue().size();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.regimen_list_menu,menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addRegimenDetailsFragment){
            Log.d("TAG","ADD...");
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

}
