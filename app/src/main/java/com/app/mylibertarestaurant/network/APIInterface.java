package com.app.mylibertarestaurant.network;


import com.app.mylibertarestaurant.constants.UrlConstants;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.AttributeModel;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */
public interface APIInterface {
    @Headers("Content-Type: application/json")
    @POST(UrlConstants.LOGIN)
    Observable<ApiResponseModel<RestaurantDetailModel>> login(@Body HashMap<String, String> body);

    @GET(UrlConstants.UPCOMMING)
    Observable<ApiResponseModel<ArrayList<OrderDetailsModel>>> getUpcommingOrder();


    @GET(UrlConstants.ACCEPT_ORDER + "{id}")
    Observable<ApiResponseModel> acceptOrder(@Path("id") String orderId);

    @GET(UrlConstants.REJECT_ORDER + "{id}")
    Observable<ApiResponseModel> rejectOrder(@Path("id") String orderId);

    @GET(UrlConstants.ORDER_HISTORY)
    Observable<ApiResponseModel<ArrayList<OrderDetailsModel>>> orderHistory();


    @GET(UrlConstants.ATTRIBUTE_LIST)
    Observable<ApiResponseModel<ArrayList<AttributeModel>>> attribute();


}
