package com.example.myapplication.view.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentMainBinding;
import com.example.myapplication.databinding.ItemMovieBinding;
import com.example.myapplication.model.api.pojo.Movie;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private static final int COUNT_MOVIES_ON_PAGE = 20;

    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    private static final String SMALL_POSTER_SIZE = "w185";

    private List<Movie> movies;
    private setOnPosterClickListener onPosterClickListener;
    private onReachEndListener onReachEndListener;

    public interface onReachEndListener {
        void onReachEnd();
    }

    public interface setOnPosterClickListener {
        void onPosterClick(int position);
    }

    public MovieAdapter() {
        this.movies = new ArrayList<>();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MovieAdapter.MovieViewHolder holder, int position) {
        if(movies.size() >= COUNT_MOVIES_ON_PAGE && position > movies.size() - 4 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }
        Movie movie = movies.get(position);
        Picasso.get().load(BASE_POSTER_URL + SMALL_POSTER_SIZE + movie.getPosterPath()).into(holder.binding.imageViewSmallPoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ItemMovieBinding binding;

        public MovieViewHolder(ItemMovieBinding b) {
            super(b.getRoot());
            binding = b;
            binding.imageViewSmallPoster.setOnClickListener(t -> {
                if(onPosterClickListener != null)
                    onPosterClickListener.onPosterClick(getAdapterPosition());
            });
        }
    }

    public void setOnReachEndListener(MovieAdapter.onReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setOnPosterClickListener(setOnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void addMovies(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public void clear() {
        this.movies.clear();
        notifyDataSetChanged();
    }
}
