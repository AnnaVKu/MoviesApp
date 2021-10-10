package com.example.myapplication.model.api;

import com.example.myapplication.model.api.pojo.ReviewResponse;
import com.example.myapplication.model.api.pojo.TrailerResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServiceReviewTrailer {

    @GET("{movie_id}/reviews")
    Observable<ReviewResponse> getReviews(@Path("movie_id") int movieId,
                                          @Query(ApiFactoryReviewTrailer.PARAM_API_KEY) String apiKey,
                                          @Query(ApiFactoryReviewTrailer.PARAM_LANGUAGE) String language);

    @GET("{movie_id}/videos")
    Observable<TrailerResponse> getTrailers(@Path("movie_id") int movieId,
                                            @Query(ApiFactoryReviewTrailer.PARAM_API_KEY) String apiKey,
                                            @Query(ApiFactoryReviewTrailer.PARAM_LANGUAGE) String language);
}
