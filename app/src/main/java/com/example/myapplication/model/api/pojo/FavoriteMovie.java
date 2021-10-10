package com.example.myapplication.model.api.pojo;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.example.myapplication.model.api.pojo.Movie;


@Entity(tableName = "favorite_movies")
public class FavoriteMovie extends Movie {

    public FavoriteMovie(int key, double popularity, int voteCount, boolean video, String posterPath, int id, boolean adult, String backdropPath, String originalLanguage, String originalTitle, String title, double voteAverage, String overview, String releaseDate) {
        super(key, popularity, voteCount, video, posterPath, id, adult, backdropPath, originalLanguage, originalTitle, title, voteAverage, overview, releaseDate);
    }

    @Ignore
    public FavoriteMovie(Movie movie) {
        super(movie.getKey(), movie.getPopularity(), movie.getVoteCount(), movie.isVideo(), movie.getPosterPath(), movie.getId(), movie.isAdult(), movie.getBackdropPath(), movie.getOriginalLanguage(), movie.getOriginalTitle(), movie.getTitle(), movie.getVoteAverage(), movie.getOverview(), movie.getReleaseDate());
    }
}
