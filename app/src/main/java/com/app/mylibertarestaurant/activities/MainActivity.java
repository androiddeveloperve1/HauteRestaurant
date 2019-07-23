package com.app.mylibertarestaurant.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.fragments.EarningFragment;
import com.app.mylibertarestaurant.fragments.FragmentOrders;
import com.app.mylibertarestaurant.fragments.InventoryFragment;
import com.app.mylibertarestaurant.fragments.ProfileFragment;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.FragmentTransactionUtils;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private boolean isReatsurantOnline = false;
    private DrawerLayout drawer;
    private NavigationView nav_view;
    private FragmentOrders fragmentOrders;
    private ProfileFragment profileFragment;
    private EarningFragment earningFragment;
    private InventoryFragment inventoryFragment;
    private RestaurantDetailModel restaurantDetailModel;

    private ArrayList<OrderDetailsModel> newOrderRequest = new ArrayList<>();
    private ArrayList<OrderDetailsModel> onGoingOrder = new ArrayList<>();
    private ArrayList<OrderDetailsModel> readyForPickupOrder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentOrders = new FragmentOrders();
        profileFragment = new ProfileFragment();
        earningFragment = new EarningFragment();
        inventoryFragment = new InventoryFragment();
        restaurantDetailModel = MySharedPreference.getInstance(MainActivity.this).getUser();
        initView();


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            backAlert();
        }
    }

    void initView() {
        drawer = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);
        navigationIteminitializer();
    }


    public void navigationClick() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    void backAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Are you sure, want to exit from app ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finishAffinity();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }


    void navigationIteminitializer() {
        View navView = nav_view.getHeaderView(0);

        final ImageView iv_on_off_line = navView.findViewById(R.id.iv_on_off_line);
        final ImageView img_restaurant = navView.findViewById(R.id.img_restaurant);
        final TextView tv_on_off_line = navView.findViewById(R.id.tv_on_off_line);
        final TextView tv_name_restaurant = navView.findViewById(R.id.tv_name_restaurant);
        Picasso.with(MainActivity.this).load(restaurantDetailModel.getAvatar()).placeholder(R.drawable.rect_border).into(img_restaurant);
        final TextView tv_order = navView.findViewById(R.id.tv_order);
        final TextView tv_inventory = navView.findViewById(R.id.tv_inventory);
        final TextView tv_earn = navView.findViewById(R.id.tv_earn);
        final TextView tv_profile = navView.findViewById(R.id.tv_profile);
        final TextView tv_help = navView.findViewById(R.id.tv_help);

        final ImageView iv_order = navView.findViewById(R.id.iv_order);
        final ImageView iv_inventory = navView.findViewById(R.id.iv_inventory);
        final ImageView iv_earn = navView.findViewById(R.id.iv_earn);
        final ImageView iv_profile = navView.findViewById(R.id.iv_profile);
        final ImageView iv_help = navView.findViewById(R.id.iv_help);
        tv_name_restaurant.setText(restaurantDetailModel.getFname() + restaurantDetailModel.getLname());

        iv_on_off_line.setOnClickListener((v) -> {
            if (isReatsurantOnline) {
                tv_on_off_line.setTextColor(getResources().getColor(R.color.yellow));
                tv_on_off_line.setText("Offline");
                iv_on_off_line.setImageResource(R.drawable.ic_offline_toggle);
                isReatsurantOnline = false;
            } else {
                tv_on_off_line.setTextColor(getResources().getColor(R.color.greencolor));
                tv_on_off_line.setText("Online");
                iv_on_off_line.setImageResource(R.drawable.ic_online_toggle);
                isReatsurantOnline = true;
            }
        });


        tv_order.setOnClickListener((v) -> {
            tv_order.setTextColor(getResources().getColor(R.color.black));
            tv_inventory.setTextColor(getResources().getColor(R.color.gray_text));
            tv_earn.setTextColor(getResources().getColor(R.color.gray_text));
            tv_profile.setTextColor(getResources().getColor(R.color.gray_text));
            tv_help.setTextColor(getResources().getColor(R.color.gray_text));

            iv_order.setImageResource(R.drawable.ic_new_order_on);
            iv_inventory.setImageResource(R.drawable.ic_manage_order_off);
            iv_earn.setImageResource(R.drawable.ic_earning_off);
            iv_profile.setImageResource(R.drawable.ic_my_profile_off);
            iv_help.setImageResource(R.drawable.ic_support_off);
            FragmentTransactionUtils.replaceFragmnet(MainActivity.this, R.id.container, fragmentOrders);
            drawer.closeDrawer(GravityCompat.START);
        });
        tv_inventory.setOnClickListener((v) -> {
            tv_order.setTextColor(getResources().getColor(R.color.gray_text));
            tv_inventory.setTextColor(getResources().getColor(R.color.black));
            tv_earn.setTextColor(getResources().getColor(R.color.gray_text));
            tv_profile.setTextColor(getResources().getColor(R.color.gray_text));
            tv_help.setTextColor(getResources().getColor(R.color.gray_text));
            iv_order.setImageResource(R.drawable.ic_new_order_off);
            iv_inventory.setImageResource(R.drawable.ic_manage_order_on);
            iv_earn.setImageResource(R.drawable.ic_earning_off);
            iv_profile.setImageResource(R.drawable.ic_my_profile_off);
            iv_help.setImageResource(R.drawable.ic_support_off);
            FragmentTransactionUtils.replaceFragmnet(MainActivity.this, R.id.container, inventoryFragment);
            drawer.closeDrawer(GravityCompat.START);
        });

        tv_earn.setOnClickListener((v) -> {
            tv_order.setTextColor(getResources().getColor(R.color.gray_text));
            tv_inventory.setTextColor(getResources().getColor(R.color.gray_text));
            tv_earn.setTextColor(getResources().getColor(R.color.black));
            tv_profile.setTextColor(getResources().getColor(R.color.gray_text));
            tv_help.setTextColor(getResources().getColor(R.color.gray_text));
            iv_order.setImageResource(R.drawable.ic_new_order_off);
            iv_inventory.setImageResource(R.drawable.ic_manage_order_off);
            iv_earn.setImageResource(R.drawable.ic_earning_on);
            iv_profile.setImageResource(R.drawable.ic_my_profile_off);
            iv_help.setImageResource(R.drawable.ic_support_off);
            FragmentTransactionUtils.replaceFragmnet(MainActivity.this, R.id.container, earningFragment);
            drawer.closeDrawer(GravityCompat.START);
        });

        tv_profile.setOnClickListener((v) -> {
            tv_order.setTextColor(getResources().getColor(R.color.gray_text));
            tv_inventory.setTextColor(getResources().getColor(R.color.gray_text));
            tv_earn.setTextColor(getResources().getColor(R.color.gray_text));
            tv_profile.setTextColor(getResources().getColor(R.color.black));
            tv_help.setTextColor(getResources().getColor(R.color.gray_text));
            iv_order.setImageResource(R.drawable.ic_new_order_off);
            iv_inventory.setImageResource(R.drawable.ic_manage_order_off);
            iv_earn.setImageResource(R.drawable.ic_earning_off);
            iv_profile.setImageResource(R.drawable.ic_my_profile_on);
            iv_help.setImageResource(R.drawable.ic_support_off);
            drawer.closeDrawer(GravityCompat.START);
            FragmentTransactionUtils.replaceFragmnet(MainActivity.this, R.id.container, profileFragment);
        });

        tv_help.setOnClickListener((v) -> {
            tv_order.setTextColor(getResources().getColor(R.color.gray_text));
            tv_inventory.setTextColor(getResources().getColor(R.color.gray_text));
            tv_earn.setTextColor(getResources().getColor(R.color.gray_text));
            tv_profile.setTextColor(getResources().getColor(R.color.gray_text));
            tv_help.setTextColor(getResources().getColor(R.color.black));
            iv_order.setImageResource(R.drawable.ic_new_order_off);
            iv_inventory.setImageResource(R.drawable.ic_manage_order_off);
            iv_earn.setImageResource(R.drawable.ic_earning_off);
            iv_profile.setImageResource(R.drawable.ic_my_profile_off);
            iv_help.setImageResource(R.drawable.ic_support_on);
            drawer.closeDrawer(GravityCompat.START);
        });
        FragmentTransactionUtils.replaceFragmnet(MainActivity.this, R.id.container, fragmentOrders);
    }

}
