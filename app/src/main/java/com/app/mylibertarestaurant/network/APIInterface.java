package com.app.mylibertarestaurant.network;


import com.app.mylibertarestaurant.constants.UrlConstants;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.AttributeModel;
import com.app.mylibertarestaurant.model.CategoryModel;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.app.mylibertarestaurant.model.items.RestaurantDetail;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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


    @GET(UrlConstants.CATEGORY_LIST)
    Observable<ApiResponseModel<ArrayList<CategoryModel>>> getCategory();

    @DELETE(UrlConstants.DELETE_FOOD_ITEM + "{id}")
    Observable<ApiResponseModel> deleteFoodItem(@Path("id") String orderId);

    @Headers("Content-Type: application/json")
    @POST(UrlConstants.ADD_FOOD)
    Observable<ApiResponseModel> addFoodItem(@Body HashMap<String, String> body);

    @Headers("Content-Type: application/json")
    @POST(UrlConstants.EDIT_FOOD)
    Observable<ApiResponseModel> editFoodItem(@Body HashMap<String, String> body);


    @Headers("Content-Type: application/json")
    @POST(UrlConstants.ONLINE_OFFLINE_STATUS)
    Observable<ApiResponseModel> updateOnlineOfflineStatus(@Body HashMap<String, String> body);




    @GET(UrlConstants.RESTAURANT_DETAIL)
    Observable<ApiResponseModel<RestaurantDetail>> getDetail();
    @Multipart
    @PUT(UrlConstants.UPDATE_RESTAURANT)
    Observable<ApiResponseModel<RestaurantDetail>> updateProfile(@Part MultipartBody.Part image,
                                               @Part("name") RequestBody name,
                                               @Part("address") RequestBody address,
                                               @Part("pincode") RequestBody pincode,
                                               @Part("deliverykm") RequestBody deliverykm,
                                               @Part("restaurant_id") RequestBody restaurant_id, @Part("latitude") RequestBody latitude,
                                               @Part("longitude") RequestBody longitude);
}
