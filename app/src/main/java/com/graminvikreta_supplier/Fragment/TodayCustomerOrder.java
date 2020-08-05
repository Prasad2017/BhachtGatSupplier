package com.graminvikreta_supplier.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graminvikreta_supplier.Activity.MainPage;
import com.graminvikreta_supplier.Extra.DetectConnection;
import com.graminvikreta_supplier.R;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class TodayCustomerOrder extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update_product, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");


        return view;

    }

    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())) {

        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }
}