package com.codingtive.sportapps.data.api;

import com.codingtive.sportapps.data.response.SportResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SportApi {
    @GET("all_sports.php")
    Call<SportResponse> getSports();
}
