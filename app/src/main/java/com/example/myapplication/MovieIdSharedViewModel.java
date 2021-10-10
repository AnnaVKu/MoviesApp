package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieIdSharedViewModel extends ViewModel {

    private final MutableLiveData<Integer> selectedMovie = new MutableLiveData<>();

    public void selectMovie(int movieId) {
        selectedMovie.setValue(movieId);
    }

    public LiveData<Integer> getSelectedMovie() {
        return selectedMovie;
    }
}
