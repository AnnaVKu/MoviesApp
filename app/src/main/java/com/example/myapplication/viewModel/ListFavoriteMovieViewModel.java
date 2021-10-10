package com.example.myapplication.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.model.api.pojo.FavoriteMovie;
import com.example.myapplication.model.api.data.MovieDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListFavoriteMovieViewModel extends AndroidViewModel {

    private LiveData<List<FavoriteMovie>> favoriteMovies;
    private static MovieDatabase db;

    public ListFavoriteMovieViewModel(@NonNull @NotNull Application application) {
        super(application);
        db = MovieDatabase.getInstance(getApplication());
        favoriteMovies = db.getMovieDao().getAllFavoriteMoviesMovies();
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void insertFavoriteMovie(FavoriteMovie movie) {
        new InsertFavoriteMovieTask().execute(movie);
    }

    private static class InsertFavoriteMovieTask extends AsyncTask<FavoriteMovie, Void, Void> {
        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            if (movies != null && movies.length > 0) db.getMovieDao().insertFavoriteMovie(movies[0]);
            return null;
        }
    }

    public FavoriteMovie getFavoriteMovieById(int id) {
        try {
            return new GetFavoriteMovieByIdTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetFavoriteMovieByIdTask extends AsyncTask<Integer, Void, FavoriteMovie> {

        @Override
        protected FavoriteMovie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return db.getMovieDao().getFavoriteMovieById(integers[0]);
            }
            return null;
        }
    }

    public void deleteFavoriteMovie(FavoriteMovie movie) {
        new DeleteFavoriteMovieTask().execute(movie);
    }

    private static class DeleteFavoriteMovieTask extends AsyncTask<FavoriteMovie, Void, Void> {

        @Override
        protected Void doInBackground(FavoriteMovie... favoriteMovies) {
            if (favoriteMovies != null && favoriteMovies.length > 0) db.getMovieDao().deleteFavoriteMovie(favoriteMovies[0]);
            return null;
        }
    }
}
