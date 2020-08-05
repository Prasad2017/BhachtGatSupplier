package com.graminvikreta_supplier.Retrofit;

import com.graminvikreta_supplier.Model.LoginResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/androidApp/Supplier/Login.php")
    Call<LoginResponse> Login(@Field("mobile") String mobile);


    @FormUrlEncoded
    @POST("/androidApp/Supplier/Registration.php")
    Call<LoginResponse> Registration(@Field("profilePhoto") String profilePhoto,
                                     @Field("firstName") String firstName,
                                     @Field("lastName") String lastName,
                                     @Field("mobileNumber")  String mobileNumber,
                                     @Field("address") String address,
                                     @Field("aadharCard")  String aadharCard,
                                     @Field("panCard")  String panCard,
                                     @Field("planterArea")  String planterArea,
                                     @Field("dryArea") String dryArea,
                                     @Field("emailId") String emailId,
                                     @Field("password") String password,
                                     @Field("bankName") String bankName,
                                     @Field("accountNumber") String accountNumber,
                                     @Field("branchNme") String branchNme,
                                     @Field("iFSC") String iFSC);


    @GET("/androidApp/Supplier/sendSMS.php")
    Call<JSONObject> sendSMS(@Query("number") String mobileNumber,
                             @Query("message") String message);


}