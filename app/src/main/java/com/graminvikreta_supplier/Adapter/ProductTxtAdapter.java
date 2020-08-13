package com.graminvikreta_supplier.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.graminvikreta_supplier.Fragment.AddProduct;
import com.graminvikreta_supplier.Model.CategoryResponse;
import com.graminvikreta_supplier.Model.ProductResponse;
import com.graminvikreta_supplier.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductTxtAdapter extends RecyclerView.Adapter<ProductTxtAdapter.MyViewHolder> {

    Context context;
    List<ProductResponse> countryResponseList;

    public ProductTxtAdapter(Context context, List<ProductResponse> countryResponseList) {

        this.context = context;
        this.countryResponseList = countryResponseList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_list_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ProductResponse categoryResponse = countryResponseList.get(position);

        holder.countryName.setText(countryResponseList.get(position).getProductName());

        holder.countryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddProduct.subCategoryTxt.setText(countryResponseList.get(position).getProductName());
                AddProduct.productId = countryResponseList.get(position).getProductId();
                AddProduct.dialog.dismiss();

            }
        });
    }

    @Override
    public int getItemCount() {
        return countryResponseList.size();
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

