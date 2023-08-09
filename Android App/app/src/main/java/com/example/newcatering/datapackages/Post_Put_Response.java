package com.example.newcatering.datapackages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post_Put_Response {


    @SerializedName("Result")
    @Expose
    private String result;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
