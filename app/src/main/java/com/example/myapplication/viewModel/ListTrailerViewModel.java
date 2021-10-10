package com.example.myapplication.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.api.ApiFactoryReviewTrailer;
import com.example.myapplication.model.api.ApiServiceReviewTrailer;
import com.example.myapplication.model.api.pojo.Trailer;
import com.example.myapplication.model.api.pojo.TrailerResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ListTrailerViewModel extends AndroidViewModel {

    private MutableLiveData<List<Trailer>> trailers;
    private CompositeDisposable compositeDisposable;

    private static final String API_KEY = "1aadecb3fa81909c3e5949f8b15ced97";
    private static String lang;

    public ListTrailerViewModel(@NonNull @NotNull Application application) {
        super(application);
        trailers = new MutableLiveData<>();
        lang = Locale.getDefault().getLanguage();
        compositeDisposable = new CompositeDisposable();
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public void loadData(int movieId) {
        ApiFactoryReviewTrailer apiFactory = ApiFactoryReviewTrailer.getInstance();
        ApiServiceReviewTrailer apiService = apiFactory.getApiService();
        Disposable disposable = apiService.getTrailers(movieId, API_KEY, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TrailerResponse>() {
                    @Override
                    public void accept(TrailerResponse trailerResponse) throws Exception {
                        trailers.setValue(trailerResponse.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

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
