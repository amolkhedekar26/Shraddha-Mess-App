package com.example.newcatering.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Constant {
    public static final String MY_PREF_NAME="CATERING";
    public static final String USER_TOKEN = "TOKEN";
    public static final String SERVER_NAME = "http://192.168.43.50:8000/api/";
    public static final String BASE_NAME="http://192.168.43.50:8000/api/";
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVER_NAME)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static final String MONTH_NAME = "MONTH";
}
