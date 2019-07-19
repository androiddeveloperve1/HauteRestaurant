package com.app.mylibertarestaurant.network;


import com.app.mylibertarestaurant.constants.UrlConstants;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;

import java.util.HashMap;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */
public interface APIInterface {
    @Headers("Content-Type: application/json")
    @POST(UrlConstants.LOGIN)
    Observable<ApiResponseModel<RestaurantDetailModel>> login(@Body HashMap<String, String> body);


}
