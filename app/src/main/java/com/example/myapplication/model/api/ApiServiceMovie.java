package com.example.myapplication.model.api;

import com.example.myapplication.model.api.pojo.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceMovie {

    @GET("movie")
    Observable<MovieResponse> getMovies(@Query(ApiFactoryMovie.PARAM_API_KEY) String apiKey,
                                              @Query(ApiFactoryMovie.PARAM_LANGUAGE) String language,
                                              @Query(ApiFactoryMovie.PARAM_SORT_BY) String methodOfSort,
                                              @Query(ApiFactoryMovie.PARAM_VOTE_COUNT) int minVoteCount,
                                              @Query(ApiFactoryMovie.PARAM_PAGE) int page);
}
