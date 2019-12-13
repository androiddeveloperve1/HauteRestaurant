package com.app.mylibertarestaurant.network;


import com.app.mylibertarestaurant.activities.ActivityAddEditCategory;
import com.app.mylibertarestaurant.activities.ActivityCategoryItem;
import com.app.mylibertarestaurant.activities.ActivitySearchItem;
import com.app.mylibertarestaurant.activities.CopyItemActivity;
import com.app.mylibertarestaurant.activities.EditItemActivity;
import com.app.mylibertarestaurant.activities.EditProfileActivity;
import com.app.mylibertarestaurant.activities.EditServiceDaysActivity;
import com.app.mylibertarestaurant.activities.ForgotPasswordActivity;
import com.app.mylibertarestaurant.activities.ItemDescriptionActivity;
import com.app.mylibertarestaurant.activities.ItemModificationActivity;
import com.app.mylibertarestaurant.activities.LoginActivity;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.activities.OrderDecriptionActivity;
import com.app.mylibertarestaurant.fragments.EarningFragment;
import com.app.mylibertarestaurant.fragments.FragmentRestaurantCategory;
import com.app.mylibertarestaurant.fragments.FragmentOrders;
import com.app.mylibertarestaurant.fragments.InventoryFragment;
import com.app.mylibertarestaurant.fragments.ProfileFragment;

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
    void inject(CopyItemActivity model);
    void inject(ItemDescriptionActivity model);
    void inject(EditProfileActivity model);
    void inject(ProfileFragment model);
    void inject(InventoryFragment model);
    void inject(EditServiceDaysActivity model);
    void inject(FragmentRestaurantCategory model);
    void inject(ItemModificationActivity model);
    void inject(ActivitySearchItem model);
    void inject(ActivityCategoryItem model);
    void inject(ActivityAddEditCategory model);


}
