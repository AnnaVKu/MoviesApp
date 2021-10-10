package com.example.myapplication.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.api.ApiFactoryReviewTrailer;
import com.example.myapplication.model.api.ApiServiceReviewTrailer;
import com.example.myapplication.model.api.pojo.Review;
import com.example.myapplication.model.api.pojo.ReviewResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ListReviewViewModel extends AndroidViewModel {

    private MutableLiveData<List<Review>> reviews;
    private CompositeDisposable compositeDisposable;

    private static final String API_KEY = "1aadecb3fa81909c3e5949f8b15ced97";
    private static String lang;

    public ListReviewViewModel(@NonNull @NotNull Application application) {
        super(application);
        reviews = new MutableLiveData<>();
        lang = Locale.getDefault().getLanguage();
        compositeDisposable = new CompositeDisposable();
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    public void loadData(int movieId) {
        ApiFactoryReviewTrailer apiFactory = ApiFactoryReviewTrailer.getInstance();
        ApiServiceReviewTrailer apiService = apiFactory.getApiService();
        Disposable disposable = apiService.getReviews(movieId, API_KEY, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ReviewResponse>() {
                    @Override
                    public void accept(ReviewResponse reviewResponse) throws Exception {
                        reviews.setValue(reviewResponse.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("Result error", throwable.getMessage());
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
