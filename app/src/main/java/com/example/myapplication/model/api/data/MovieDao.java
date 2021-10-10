package com.example.myapplication.model.api.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.model.api.pojo.FavoriteMovie;
import com.example.myapplication.model.api.pojo.Movie;

import java.util.List;
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

    @Insert
    void insertMovies(List<Movie> movies);

    @Query("SELECT * FROM movies WHERE id == :movieId")
    Movie getMovieById(int movieId);

    @Query("DELETE FROM movies")
    void deleteAllMovies();

    @Delete
    void deleteMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Query("SELECT * FROM favorite_movies WHERE id == :movieId")
    FavoriteMovie getFavoriteMovieById(int movieId);

    @Query("SELECT * FROM favorite_movies")
    LiveData<List<FavoriteMovie>> getAllFavoriteMoviesMovies();

    @Insert
    void insertFavoriteMovie(FavoriteMovie movie);

    @Delete
    void deleteFavoriteMovie(FavoriteMovie movie);
}
