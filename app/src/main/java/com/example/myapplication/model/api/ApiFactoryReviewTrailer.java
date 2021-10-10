package com.example.myapplication.model.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactoryReviewTrailer {

    private static ApiFactoryReviewTrailer apiFactoryReviewTrailer;
    private static Retrofit retrofit;

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String PARAM_API_KEY = "api_key";
    public static final String PARAM_LANGUAGE = "language";

    private ApiFactoryReviewTrailer() {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static ApiFactoryReviewTrailer getInstance() {
        if(apiFactoryReviewTrailer == null) {
            apiFactoryReviewTrailer = new ApiFactoryReviewTrailer();
        }
        return apiFactoryReviewTrailer;
    }

    public ApiServiceReviewTrailer getApiService(){
        return retrofit.create(ApiServiceReviewTrailer.class);
    }
}
