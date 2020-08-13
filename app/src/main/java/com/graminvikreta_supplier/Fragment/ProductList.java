package com.graminvikreta_supplier.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.graminvikreta_supplier.Activity.MainPage;
import com.graminvikreta_supplier.Extra.DetectConnection;
import com.graminvikreta_supplier.Model.AllList;
import com.graminvikreta_supplier.Model.ProductResponse;
import com.graminvikreta_supplier.R;
import com.graminvikreta_supplier.Retrofit.Api;
import com.graminvikreta_supplier.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductList extends Fragment {


    View view;
    @BindView(R.id.productRecyclerView)
    RecyclerView recyclerView;
    List<ProductResponse> productResponseList = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_list, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");

        return view;

    }

    @OnClick({R.id.addProduct})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addProduct:
                ((MainPage) getActivity()).loadFragment(new AddProduct(), true);
                break;
        }
    }

    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getProductList();
        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }

    private void getProductList() {


        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getProductList(MainPage.userId);
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                productResponseList = allList.getProductResponseList();

                if (productResponseList.size()==0){
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "No Product Found", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();




                }

            }

            @Override
            public void onFailure(Call<AllList> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("productError", ""+t.getMessage());
            }
        });

    }
}