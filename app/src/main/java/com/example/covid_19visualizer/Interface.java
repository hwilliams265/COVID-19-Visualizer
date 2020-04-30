package com.example.covid_19visualizer;

import com.example.covid_19visualizer.parameter.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Interface {
    @GET("top-headlines")
    Call<Headlines> getHeadlines(
        @Query("country") String country,
                @Query("category") String category,
                    @Query("apiKey") String apiKey
        );
}
