package com.app.mylibertarestaurant.network;


import com.app.mylibertarestaurant.activities.EditItemActivity;
import com.app.mylibertarestaurant.activities.ForgotPasswordActivity;
import com.app.mylibertarestaurant.activities.LoginActivity;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.activities.OrderDecriptionActivity;
import com.app.mylibertarestaurant.fragments.EarningFragment;
import com.app.mylibertarestaurant.fragments.FragmentOrders;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */
@Singleton
@Component(modules = {NetworkModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity model);
    void inject(ForgotPasswordActivity model);
    void inject(MainActivity model);
    void inject(OrderDecriptionActivity model);
    void inject(FragmentOrders model);
    void inject(EarningFragment model);
    void inject(EditItemActivity model);


}
