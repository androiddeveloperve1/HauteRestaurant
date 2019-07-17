package com.app.mylibertarestaurant.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.adapter.EarnAdapter;
import com.app.mylibertarestaurant.databinding.FragmentTodayEarningBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class EarningFragment extends Fragment {
    FragmentTodayEarningBinding binder;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_today_earning, container, false);
        binder.rvItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        binder.setAdp(new EarnAdapter(new RecycleItemClickListener() {
            @Override
            public void onItemClicked(int position, Object data) {

            }
        }));
        binder.toolbarManu.setOnClickListener((v) -> {
            MainActivity act = (MainActivity) getActivity();
            act.navigationClick();
        });
        View view = binder.getRoot();
        return view;
    }
}
