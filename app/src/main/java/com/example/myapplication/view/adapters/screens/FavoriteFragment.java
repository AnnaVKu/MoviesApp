package com.example.myapplication.view.adapters.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.MovieIdSharedViewModel;
import com.example.myapplication.databinding.FragmentFavoriteBinding;
import com.example.myapplication.model.api.pojo.FavoriteMovie;
import com.example.myapplication.view.adapters.MovieAdapter;
import com.example.myapplication.R;
import com.example.myapplication.model.api.pojo.Movie;
import com.example.myapplication.viewModel.ListFavoriteMovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private MovieAdapter adapter;
    private ListFavoriteMovieViewModel listFavoriteMovieViewModel;
    private MovieIdSharedViewModel movieIdSharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new MovieAdapter();
        binding.recyclerViewFavoriteMovies.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        binding.recyclerViewFavoriteMovies.setAdapter(adapter);
        listFavoriteMovieViewModel = new ViewModelProvider(this).get(ListFavoriteMovieViewModel.class);
        movieIdSharedViewModel = new ViewModelProvider(requireActivity()).get(MovieIdSharedViewModel.class);

        listFavoriteMovieViewModel.getFavoriteMovies().observe(getViewLifecycleOwner(), new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(List<FavoriteMovie> favoriteMovies) {
                List<Movie> movies = new ArrayList<>();
                if(favoriteMovies != null) {
                    movies.addAll(favoriteMovies);
                    adapter.setMovies(movies);
                }
            }
        });

        adapter.setOnPosterClickListener(new MovieAdapter.setOnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Log.i("Result onPosterClick", Integer.toString(position));
                Movie movie = adapter.getMovies().get(position);
                movieIdSharedViewModel.selectMovie(movie.getId());
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.your_placeholder, DetailFragment.getDetailFragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    public static FavoriteFragment getFavoriteFragment() {
        return new FavoriteFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}