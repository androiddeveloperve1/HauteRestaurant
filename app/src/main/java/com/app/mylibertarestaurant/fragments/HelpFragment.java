package com.app.mylibertarestaurant.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.databinding.FragmentHelpBinding;
import com.app.mylibertarestaurant.network.APIInterface;

import javax.inject.Inject;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class HelpFragment extends Fragment {
    FragmentHelpBinding binder;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_help, container, false);
        binder.setClick(new Click());
        View view = binder.getRoot();
        return view;
    }

    public class Click {
        public void onHUmburgur(View v) {
            MainActivity act = (MainActivity) getActivity();
            act.navigationClick();
        }

    }


}

