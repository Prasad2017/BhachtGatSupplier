package com.graminvikreta_supplier.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.graminvikreta_supplier.Model.AssignResponse;
import com.graminvikreta_supplier.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssignToAdapter extends RecyclerView.Adapter<AssignToAdapter.MyViewHolder> {

    Context context;
    List<AssignResponse> assignResponseList;


    public AssignToAdapter(Context context, List<AssignResponse> assignResponseList) {

        this.context = context;
        this.assignResponseList = assignResponseList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.dialog_list_item,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.countryName.setText(assignResponseList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return assignResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.countryName)
        TextView countryName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
