package com.imedia.designercompanion.network_operations;

import com.imedia.designercompanion.network_operations.models.CustomerMearsurementModel;
import com.imedia.designercompanion.network_operations.models.CustomerMearsurementModelResponse;
import com.imedia.designercompanion.network_operations.models.CustomerModel;
import com.imedia.designercompanion.network_operations.models.CustomerModelResponse;
import com.imedia.designercompanion.network_operations.models.LoginUserResponseModel;
import com.imedia.designercompanion.network_operations.models.MyDesignsModel;
import com.imedia.designercompanion.network_operations.models.MyDesignsModelResponse;
import com.imedia.designercompanion.network_operations.models.RegisterUserModel;
import com.imedia.designercompanion.network_operations.models.RegisterUserModelResponse;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Rest_DB_Interface {

    @POST("rest/designer-companion-user")
    Call<RegisterUserModelResponse> registerUser(@Body RegisterUserModel registerUserModel);

    @GET("rest/designer-companion-user?")
    Call<ArrayList<LoginUserResponseModel>> loginUser(@Query("q")JSONObject jsonObject);

    @POST("rest/designer-companion-customers")
    Call<CustomerModelResponse> registerCustomer(@Body CustomerModel customerModel);

    @GET("rest/designer-companion-customers")
    Call<CustomerModelResponse> getCustomerWithId(@Query("q")JSONObject jsonObject);

    @PUT("rest/designer-companion-customers/{user_id}")
    Call<CustomerModelResponse> updateCustomerWithID(@Path(value = "user_id", encoded = true) String userId, @Body CustomerModel customerModel);

    @POST("rest/designer-companion-customers-mearsurements")
    Call<CustomerMearsurementModelResponse> postNewUserMeasurement(@Body CustomerMearsurementModel mearsurementModel);

    @PUT("rest/designer-companion-customers-mearsurements/{user_id}")
    Call<CustomerMearsurementModelResponse> updateUserMeasurementWithID(@Path(value = "user_id", encoded = true) String userId, @Body CustomerMearsurementModel mearsurementModel);

    @GET("rest/designer-companion-customers-mearsurements?")
    Call<CustomerMearsurementModelResponse> getCustomerMeasurementWithID(@Query("q")JSONObject jsonObject);

    @POST("rest/designer-companion-user-designs")
    Call<MyDesignsModelResponse> postDesign(@Body MyDesignsModel myDesignsModel);

}
