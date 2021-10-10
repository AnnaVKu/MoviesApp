package com.example.myapplication.view.adapters.screens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.MovieIdSharedViewModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDetailBinding;
import com.example.myapplication.model.api.pojo.Review;
import com.example.myapplication.model.api.pojo.Trailer;
import com.example.myapplication.view.adapters.ReviewAdapter;
import com.example.myapplication.view.adapters.TrailerAdapter;
import com.example.myapplication.model.api.pojo.FavoriteMovie;
import com.example.myapplication.model.api.pojo.Movie;
import com.example.myapplication.viewModel.ListFavoriteMovieViewModel;
import com.example.myapplication.viewModel.ListMovieViewModel;
import com.example.myapplication.viewModel.ListReviewViewModel;
import com.example.myapplication.viewModel.ListTrailerViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;

    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    private static final String BIG_IMAGE_SIZE = "w780";

    private FavoriteMovie favoriteMovie;
    private Movie movie;

    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;

    private ListMovieViewModel listMovieViewModel;
    private ListFavoriteMovieViewModel listFavoriteMovieViewModel;
    private ListReviewViewModel listReviewViewModel;
    private ListTrailerViewModel listTrailerViewModel;

    private MovieIdSharedViewModel movieIdSharedViewModel;

    private int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        listMovieViewModel = new ViewModelProvider(this).get(ListMovieViewModel.class);
        listFavoriteMovieViewModel = new ViewModelProvider(this).get(ListFavoriteMovieViewModel.class);

        movieIdSharedViewModel = new ViewModelProvider(requireActivity()).get(MovieIdSharedViewModel.class);
        movieIdSharedViewModel.getSelectedMovie().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                id = integer;
                if (listMovieViewModel.getMovieById(id) != null) {
                    movie = listMovieViewModel.getMovieById(id);
                } else {
                    movie = listFavoriteMovieViewModel.getFavoriteMovieById(id);
                }
                setMovieInfo(movie);
                setToFavorite();
                listReviewViewModel.loadData(id);
                listTrailerViewModel.loadData(id);
            }
        });

        binding.imageViewAddToFavorite.setOnClickListener(t ->
        {
            if (favoriteMovie == null)
                listFavoriteMovieViewModel.insertFavoriteMovie(new FavoriteMovie(movie));
            else listFavoriteMovieViewModel.deleteFavoriteMovie(favoriteMovie);

            setToFavorite();
        });

        listReviewViewModel = new ViewModelProvider(this).get(ListReviewViewModel.class);
        listTrailerViewModel = new ViewModelProvider(this).get(ListTrailerViewModel.class);

        reviewAdapter = new ReviewAdapter();
        trailerAdapter = new TrailerAdapter();

        trailerAdapter.setOnTrailerClickListener(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(String url) {
                Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intentToTrailer);
            }
        });

        binding.movieInfo.recyclerviewReviews.setAdapter(reviewAdapter);
        binding.movieInfo.recyclerviewTrailers.setAdapter(trailerAdapter);
        binding.movieInfo.recyclerviewReviews.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.movieInfo.recyclerviewTrailers.setLayoutManager(new LinearLayoutManager(requireActivity()));

        listReviewViewModel.getReviews().observe(getViewLifecycleOwner(), new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter.setReviews(reviews);
            }
        });

        listTrailerViewModel.getTrailers().observe(getViewLifecycleOwner(), new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailerAdapter.setTrailers(trailers);
            }
        });
        return view;
    }

    public void setMovieInfo(Movie movie) {
        Picasso.get().load(BASE_IMAGE_URL + BIG_IMAGE_SIZE + movie.getPosterPath()).into(binding.imageViewBigPoster);
        binding.movieInfo.textViewTitle.setText(movie.getTitle());
        binding.movieInfo.textViewOriginTitle.setText(movie.getOriginalTitle());
        binding.movieInfo.textViewRating.setText(Double.toString(movie.getVoteAverage()));
        binding.movieInfo.textViewDateOfRelease.setText(movie.getReleaseDate());
        binding.movieInfo.textViewOverview.setText(movie.getOverview());
    }

    public void setToFavorite() {
        favoriteMovie = listFavoriteMovieViewModel.getFavoriteMovieById(id);
        if (favoriteMovie == null)
            binding.imageViewAddToFavorite.setImageResource(R.drawable.favourite_add_to);
        else binding.imageViewAddToFavorite.setImageResource(R.drawable.favourite_remove);
    }

    public static DetailFragment getDetailFragment() {
        return new DetailFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}