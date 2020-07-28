package com.bhachtgatsupplier.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhachtgatsupplier.R;

import butterknife.BindView;


public class ProductList extends Fragment {


    View view;
    @BindView(R.id.myCartsRecyclerView)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }
}