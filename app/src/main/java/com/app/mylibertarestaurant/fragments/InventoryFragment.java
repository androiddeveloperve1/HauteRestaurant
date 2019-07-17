package com.app.mylibertarestaurant.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.databinding.FragmentInventoryBinding;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class InventoryFragment extends Fragment {
    FragmentInventoryBinding binder;
    FragmentInventoryListChild fragmentInventoryListChild;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_inventory, container, false);
        binder.setClick(new Click());
        fragmentInventoryListChild = new FragmentInventoryListChild();
        setFragmnet(fragmentInventoryListChild);
        View view = binder.getRoot();
        return view;
    }


    void setFragmnet(Fragment fragment) {
        String Tag = fragment.getClass().getName();
        boolean fragmentPopped = getChildFragmentManager().popBackStackImmediate(Tag, 0);
        if (!fragmentPopped && getChildFragmentManager().findFragmentByTag(Tag) == null) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.child_container, fragment, Tag);
            ft.addToBackStack(Tag);
            ft.commit();
        }

    }

    public class Click {
        public void onNavigationMenu(View v) {
            MainActivity act = (MainActivity) getActivity();
            act.navigationClick();
        }

        public void onSearch(View v) {

        }

        public void onAdd(View v) {

        }
    }
}
