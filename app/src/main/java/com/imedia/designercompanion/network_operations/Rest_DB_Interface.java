package com.imedia.designercompanion.network_operations;

import com.imedia.designercompanion.network_operations.models.CustomerMearsurementModel;
import com.imedia.designercompanion.network_operations.models.CustomerMearsurementModelResponse;
import com.imedia.designercompanion.network_operations.models.CustomerModel;
import com.imedia.designercompanion.network_operations.models.CustomerModelResponse;
import com.imedia.designercompanion.network_operations.models.LoginUserResponseModel;
import com.imedia.designercompanion.network_operations.models.RegisterUserModel;
import com.imedia.designercompanion.network_operations.models.RegisterUserModelResponse;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Rest_DB_Interface {

    @POST("designer-companion-user")
    Call<RegisterUserModelResponse> registerUser(@Body RegisterUserModel registerUserModel);

    @GET("designer-companion-user?")
    Call<ArrayList<LoginUserResponseModel>> loginUser(@Query("q")JSONObject jsonObject);

    @POST("designer-companion-customers")
    Call<CustomerModelResponse> registerCustomer(@Body CustomerModel customerModel);

    @GET("designer-companion-customers")
    Call<CustomerModelResponse> getCustomerWithId(@Query("q")JSONObject jsonObject);

    @PUT("designer-companion-customers/{user_id}")
    Call<CustomerModelResponse> updateCustomerWithID(@Path(value = "user_id", encoded = true) String userId, @Body CustomerModel customerModel);

    @POST("designer-companion-customers-mearsurements")
    Call<CustomerMearsurementModelResponse> postNewUserMeasurement(@Body CustomerMearsurementModel mearsurementModel);

    @PUT("designer-companion-customers-mearsurements/{user_id}")
    Call<CustomerMearsurementModelResponse> updateUserMeasurementWithID(@Path(value = "user_id", encoded = true) String userId, @Body CustomerMearsurementModel mearsurementModel);

    @GET("designer-companion-customers-mearsurements?")
    Call<CustomerMearsurementModelResponse> getCustomerMeasurementWithID(@Query("q")JSONObject jsonObject);
}
