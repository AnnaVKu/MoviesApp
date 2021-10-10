package com.example.myapplication.viewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.api.ApiFactoryMovie;
import com.example.myapplication.model.api.ApiServiceMovie;
import com.example.myapplication.model.api.data.MovieDatabase;
import com.example.myapplication.model.api.pojo.Movie;
import com.example.myapplication.model.api.pojo.MovieResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ListMovieViewModel extends AndroidViewModel {

    private static final String API_KEY = "1aadecb3fa81909c3e5949f8b15ced97";
    private static final String SORT_BY_POPULARITY = "popularity.desc";
    private static final String SORT_BY_RATING = "vote_average.desc";
    private static final int MIN_VOTE_COUNT = 1000;

    private static int page;
    private static String methodOfSort;
    private static String lang;

    private CompositeDisposable compositeDisposable;

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<Boolean> isLoading() {
        return this.isLoading;
    }

    private LiveData<List<Movie>> movies;
    private static MovieDatabase db;

    public ListMovieViewModel(@NonNull @NotNull Application application) {
        super(application);
        db = MovieDatabase.getInstance(getApplication());
        movies = db.getMovieDao().getAllMovies();
        lang = Locale.getDefault().getLanguage();
        compositeDisposable = new CompositeDisposable();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public void startLoadDataPopularity() {
        page = 1;
        methodOfSort = SORT_BY_POPULARITY;
        loadData();
    }

    public void startLoadDataRating() {
        page = 1;
        methodOfSort = SORT_BY_RATING;
        loadData();
    }

    public void continueLoadData() {
        loadData();
    }

    public Movie getMovieById(int id) {
        Log.i("Result", "viewModel " + id);
        try {
            return new GetMovieByIdTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetMovieByIdTask extends AsyncTask<Integer, Void, Movie> {

        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return db.getMovieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    private void deleteAllMovies() {
        new DeleteAllMoviesTask().execute();
    }

    private static class DeleteAllMoviesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            db.getMovieDao().deleteAllMovies();
            return null;
        }
    }

    private void deleteMovie(Movie movie) {
        new DeleteMovieTask().execute(movie);
    }

    private static class DeleteMovieTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) db.getMovieDao().deleteMovie(movies[0]);
            return null;
        }
    }

    private void insertMovie(Movie movie) {
        new InsertMovieTask().execute(movie);
    }

    private static class InsertMovieTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) db.getMovieDao().insertMovie(movies[0]);
            return null;
        }
    }

    private void insertMovies(List<Movie> movies) {
        new InsertMoviesTask().execute(movies);
    }

    private static class InsertMoviesTask extends AsyncTask<List<Movie>, Void, Void> {

        @Override
        protected Void doInBackground(List<Movie>... lists) {
            if(lists != null && lists.length > 0) {
                db.getMovieDao().insertMovies(lists[0]);
            }
            return null;
        }
    }

    public void loadData() {
        Boolean loading = isLoading.getValue();
        if(loading != null && loading) {
            return;
        }
        ApiFactoryMovie apiFactoryMovie = ApiFactoryMovie.getInstance();
        ApiServiceMovie apiServiceMovie = apiFactoryMovie.getApiService();
        isLoading.postValue(true);
        Disposable disposable = apiServiceMovie.getMovies(API_KEY,
                lang, methodOfSort, MIN_VOTE_COUNT, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {
                        isLoading.postValue(false);
                        if(page == 1) {
                            deleteAllMovies();
                        }
                        insertMovies(movieResponse.getResults());
                        page ++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isLoading.postValue(false);
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        if(compositeDisposable != null) compositeDisposable.dispose();
        super.onCleared();
    }
}
