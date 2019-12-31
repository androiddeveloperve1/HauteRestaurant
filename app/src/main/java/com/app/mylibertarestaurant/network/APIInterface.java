package com.app.mylibertarestaurant.network;


import com.app.mylibertarestaurant.constants.UrlConstants;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.AttributeModel;
import com.app.mylibertarestaurant.model.CategoryModel;
import com.app.mylibertarestaurant.model.InventoryResponseModel;
import com.app.mylibertarestaurant.model.TimeSlotUpdate;
import com.app.mylibertarestaurant.model.inventorynew.InventoryModelNew;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.app.mylibertarestaurant.model.items.RestaurantDetail;
import com.app.mylibertarestaurant.model.newP.DietryLabelModel;
import com.app.mylibertarestaurant.model.newP.MealAvailabilityModel;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryItemModel;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryModel;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    @POST(UrlConstants.UPCOMMING)
    Observable<ApiResponseModel<ArrayList<OrderDetailsModel>>> getUpcommingOrder(@Body HashMap<String, String> body);


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
                                                                 @Part("longitude") RequestBody longitude, @Part("maxdeliverytime") RequestBody maxdeliverytime
            , @Part("deliveryfees") RequestBody deliveryfees);

    @Multipart
    @POST(UrlConstants.ADD_UPDATE_MENU_ITEM)
    Observable<ApiResponseModel> addFoodItem(@Part MultipartBody.Part image,
                                             @Part("name") RequestBody name,
                                             @Part("restaurent_id") RequestBody restaurent_id,
                                             @Part("category_id") RequestBody category_id,
                                             @Part("price") RequestBody price,
                                             @Part("isActive") RequestBody isActive,
                                             @Part("mealAvailability") RequestBody mealAvailability,
                                             @Part("minQuantity") RequestBody minQuantity,
                                             @Part("maxQuantity") RequestBody maxQuantity,
                                             @Part("dietaryLabels") RequestBody dietaryLabels,
                                             @Part("daysOfWeek") RequestBody daysOfWeek,
                                             @Part("hiddenDate") RequestBody availableDate,
                                             @Part("isHidden") RequestBody isHidden,
                                             @Part("description") RequestBody description);

    @Multipart
    @POST(UrlConstants.ADD_UPDATE_MENU_ITEM)
    Observable<ApiResponseModel> editMenuItem(@Part MultipartBody.Part image,
                                              @Part("name") RequestBody name,
                                              @Part("item_id") RequestBody item_id,
                                              @Part("is_update") RequestBody is_update,
                                              @Part("restaurent_id") RequestBody restaurent_id,
                                              @Part("category_id") RequestBody category_id,
                                              @Part("price") RequestBody price,
                                              @Part("isActive") RequestBody isActive,
                                              @Part("mealAvailability") RequestBody mealAvailability,
                                              @Part("minQuantity") RequestBody minQuantity,
                                              @Part("maxQuantity") RequestBody maxQuantity,
                                              @Part("dietaryLabels") RequestBody dietaryLabels,
                                              @Part("daysOfWeek") RequestBody daysOfWeek,
                                              @Part("hiddenDate") RequestBody availableDate,
                                              @Part("isHidden") RequestBody isHidden,
                                              @Part("isImageRemove") RequestBody isImageRemove,
                                              @Part("description") RequestBody description
    );


    @Headers("Content-Type: application/json")
    @PUT(UrlConstants.READY_FOR_PICKUP)
    Observable<ApiResponseModel> readyForPickup(@Body HashMap<String, String> body);

    @GET(UrlConstants.ORDER_DETAIL + "{id}")
    Observable<ApiResponseModel<OrderDetailsModel>> getOrderDetail(@Path("id") String orderId);


    @GET(UrlConstants.INVENTORY)
    Observable<ApiResponseModel<ArrayList<InventoryResponseModel>>> getInventory();

    @GET(UrlConstants.LOGOUT)
    Observable<ApiResponseModel> logOut();

    @Headers("Content-Type: application/json")
    @PUT(UrlConstants.UPDATE_TIME_SLOT)
    Observable<ApiResponseModel> updateSlot(@Body TimeSlotUpdate body);

    @Headers("Content-Type: application/json")
    @POST(UrlConstants.FORGOT_PASSWORD)
    Observable<ApiResponseModel> forGotPass(@Body HashMap<String, String> body);


    @Headers("Content-Type: application/json")
    @PUT(UrlConstants.READY_FOR_PICKUP_VERIFY)
    Observable<ApiResponseModel> readyForPickupVerify(@Body HashMap<String, String> body);

    @Headers("Content-Type: application/json")
    @POST(UrlConstants.INVENTORY)
    Observable<ApiResponseModel<ArrayList<InventoryModelNew>>> getInventoryNew(@Body HashMap<String, String> body);
    /*------------------------------------------------------------------------------------------------------------*/


    @GET(UrlConstants.RESTAURANT_CATEGORY + "{id}")
    Observable<ApiResponseModel<ArrayList<RestaurantCategoryModel>>> restaurantCategory(@Path("id") String id);

    @GET(UrlConstants.RESTAURANT_CATEGORY_ITEM + "{catId}")
    Observable<ApiResponseModel<ArrayList<RestaurantCategoryItemModel>>> restaurantCategoryItemById(@Path("catId") String id);

    @Multipart
    @POST(UrlConstants.ADD_EDIT_RESTAURANT_CATEGORY)
    Observable<ApiResponseModel> saveCategory(@Part MultipartBody.Part image,
                                              @Part("name") RequestBody name,
                                              @Part("description") RequestBody description,
                                              @Part("location") RequestBody location,
                                              @Part("restaurent_id") RequestBody restaurent_id);

    @Multipart
    @POST(UrlConstants.ADD_EDIT_RESTAURANT_CATEGORY)
    Observable<ApiResponseModel> updateCategory(@Part MultipartBody.Part image,
                                                @Part("name") RequestBody name,
                                                @Part("description") RequestBody description,
                                                @Part("location") RequestBody location,
                                                @Part("restaurent_id") RequestBody restaurent_id,
                                                @Part("isActive") RequestBody isActive,
                                                @Part("isImageRemove") RequestBody isImageRemove,
                                                @Part("category_id") RequestBody category_id,
                                                @Part("is_update") RequestBody is_update);

    @GET(UrlConstants.MENU_ITEM_DETAIL + "{id}")
    Observable<ApiResponseModel<ArrayList<RestaurantCategoryItemModel>>> getMenuDetail(@Path("id") String id);


    @Headers("Content-Type: application/json")
    @POST(UrlConstants.UPDATE_MENU_STATUS)
    Observable<ApiResponseModel> updateMenuStatus(@Body HashMap<String, String> body);


    @DELETE(UrlConstants.DELETE_MENU + "{id}")
    Observable<ApiResponseModel> delMenu(@Path("id") String id);


    @DELETE(UrlConstants.DELETE_OPTION + "{id}")
    Observable<ApiResponseModel> delOption(@Path("id") String id);


    @DELETE(UrlConstants.DELETE_SUB_OPTION + "{id}")
    Observable<ApiResponseModel> delSubOption(@Path("id") String id);

    @Headers("Content-Type: application/json")
    @POST(UrlConstants.ADD_UPDATE_OPTION)
    Observable<ApiResponseModel> addUpdateOption(@Body HashMap<String, String> body);


    @Headers("Content-Type: application/json")
    @POST(UrlConstants.ADD_UPDATE_SUB_OPTION)
    Observable<ApiResponseModel> addUpdateSubOption(@Body HashMap<String, Object> body);


    @DELETE(UrlConstants.DELETE_MENU_ITEM + "{id}")
    Observable<ApiResponseModel> delMenuItem(@Path("id") String id);


    @GET(UrlConstants.GET_FOOD_AVAILABILITY)
    Observable<ApiResponseModel<ArrayList<MealAvailabilityModel>>> getFoodAvailability();


    @GET(UrlConstants.GET_DIETARY_LIST)
    Observable<ApiResponseModel<ArrayList<DietryLabelModel>>> getDietary();


}
