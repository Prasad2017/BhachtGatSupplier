package com.bhachtgatsupplier.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhachtgatsupplier.Activity.MainPage;
import com.bhachtgatsupplier.Adapter.TabViewAdapter;
import com.bhachtgatsupplier.R;
import com.google.android.material.tabs.TabLayout;

import butterknife.ButterKnife;

public class Orders extends Fragment {

    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabViewAdapter adapter;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");

        initComponent();

        return view;
    }


    private void initComponent() {

        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Today's Customer Order"));
        tabLayout.addTab(tabLayout.newTab().setText("Today's Pickup Order"));
        tabLayout.addTab(tabLayout.newTab().setText("Delivered Order"));
        tabLayout.addTab(tabLayout.newTab().setText("Benefit Order"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.white), ContextCompat.getColor(getActivity(), R.color.white));

        Fragment[] tabs = new Fragment[4];
        tabs[0] = new TodayCustomerOrder();
        tabs[1] = new TodayPickupOrder();
        tabs[2] = new DeliveredOrder();
        tabs[3] = new BenefitOrder();
        adapter = new TabViewAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount(), tabs, 1);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.forwardActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


}

