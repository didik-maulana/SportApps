package com.codingtive.sportapps.data.api;

import com.codingtive.sportapps.data.response.EventResponse;
import com.codingtive.sportapps.data.response.SportResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SportApi {
    @GET("all_sports.php")
    Call<SportResponse> getSports();

    @GET("eventsnextleague.php")
    Call<EventResponse> getEvents(@Query("id") String id);
}
