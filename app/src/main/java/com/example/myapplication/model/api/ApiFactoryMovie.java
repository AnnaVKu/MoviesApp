package com.example.myapplication.model.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactoryMovie {

    public static final String PARAM_API_KEY = "api_key";
    public static final String PARAM_LANGUAGE = "language";
    public static final String PARAM_SORT_BY = "sort_by";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_VOTE_COUNT = "vote_count.gte";

    private static ApiFactoryMovie apiFactoryMovie;
    private static Retrofit retrofit;
    private final static String BASE_URL = "https://api.themoviedb.org/3/discover/";

    private ApiFactoryMovie() {
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static ApiFactoryMovie getInstance() {
        if(apiFactoryMovie == null) apiFactoryMovie = new ApiFactoryMovie();
        return apiFactoryMovie;
    }

    public ApiServiceMovie getApiService() {
        return  retrofit.create(ApiServiceMovie.class);
    }
}
