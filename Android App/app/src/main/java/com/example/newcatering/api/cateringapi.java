package com.example.newcatering.api;

import com.example.newcatering.datapackages.Attendance;
import com.example.newcatering.datapackages.Menu;
import com.example.newcatering.datapackages.Post_Put_Response;
import com.example.newcatering.datapackages.Token;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface cateringapi {

    @POST("obtain-auth-token/")
    @FormUrlEncoded
    Call<Token> getToken(@Field("username") String username,
                         @Field("password") String password);

    @POST("signup/")
    @FormUrlEncoded
    Call<Post_Put_Response> signup(@Field("email") String email,
                                   @Field("password") String password);

    @POST("save-attendance/")
    @FormUrlEncoded
    Call<Post_Put_Response> save_attendance(
            @Header("Authorization") String token,
            @Field("date") String date,
            @Field("noon") String noon,
            @Field("night") String night);

    @GET("get-attendance/{month}/")
    Call<List<Attendance>> getAttendance(@Header("Authorization") String token, @Path("month") String month);

    @GET("get-menu/")
    Call<List<Menu>> getMenu();
}
