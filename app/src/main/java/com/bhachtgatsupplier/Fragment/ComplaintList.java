package com.bhachtgatsupplier.Fragment;

import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhachtgatsupplier.Activity.MainPage;
import com.bhachtgatsupplier.Extra.DetectConnection;
import com.bhachtgatsupplier.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


public class ComplaintList extends Fragment {

    View view;
    @BindView(R.id.complaintRecyclerView)
    RecyclerView recyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_complaint_list, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");

        return view;

    }

    @OnClick({R.id.addComplaint})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addComplaint:
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

        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }
}