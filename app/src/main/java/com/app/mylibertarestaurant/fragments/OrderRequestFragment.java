package com.app.mylibertarestaurant.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.activities.OrderDecriptionActivity;
import com.app.mylibertarestaurant.adapter.RequestOrderItemAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.FragmentOrderRequestBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */


public class OrderRequestFragment extends Fragment {
    FragmentOrderRequestBinding binder;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_order_request, container, false);

        binder.rvItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        binder.setAdp(new RequestOrderItemAdapter(new RecycleItemClickListener() {
            @Override
            public void onItemClickedWithTag(int position, Object data, int tag) {
                Intent mIntent = new Intent(getActivity(), OrderDecriptionActivity.class);
                mIntent.putExtra("tag", tag);
                startActivity(mIntent);
            }
        }, Constants.FROM_ORDER_REQUEST_TAG));
        View view = binder.getRoot();
        return view;
    }
}
