package com.example.myapplication.view.adapters.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MovieIdSharedViewModel;
import com.example.myapplication.databinding.FragmentMainBinding;
import com.example.myapplication.view.adapters.MovieAdapter;
import com.example.myapplication.R;
import com.example.myapplication.model.api.pojo.Movie;
import com.example.myapplication.viewModel.ListMovieViewModel;

import java.util.List;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private MovieAdapter adapter;
    private ListMovieViewModel listMovieViewModel;
    private boolean isLoading = false;
    private MovieIdSharedViewModel movieIdSharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new MovieAdapter();
        binding.recyclerviewMovies.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        binding.recyclerviewMovies.setAdapter(adapter);

        listMovieViewModel = new ViewModelProvider(requireActivity()).get(ListMovieViewModel.class);
        movieIdSharedViewModel = new ViewModelProvider(requireActivity()).get(MovieIdSharedViewModel.class);

        setMethodOfSort(false);
        binding.switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setMethodOfSort(isChecked);
            }
        });
        binding.switchSort.setChecked(false);

        binding.textViewSortByPopularity.setOnClickListener(t -> {
            setMethodOfSort(false);
            binding.switchSort.setChecked(false);
        });
        binding.textViewSortByRating.setOnClickListener(t -> {
            setMethodOfSort(true);
            binding.switchSort.setChecked(true);
        });

        adapter.setOnPosterClickListener(new MovieAdapter.setOnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Movie movie = adapter.getMovies().get(position);
                movieIdSharedViewModel.selectMovie(movie.getId());
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.your_placeholder, DetailFragment.getDetailFragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        adapter.setOnReachEndListener(new MovieAdapter.onReachEndListener() {
            @Override
            public void onReachEnd() {
                if (!isLoading) listMovieViewModel.continueLoadData();
            }
        });

        listMovieViewModel.getMovies().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter.setMovies(movies);
            }
        });

        listMovieViewModel.isLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                setLoading(aBoolean);
            }
        });

        return view;
    }

    private void setMethodOfSort(boolean isTopRated) {
        adapter.clear();
        if (isTopRated) {
            binding.textViewSortByRating.setTextColor(getResources().getColor(R.color.design_default_color_primary));
            binding.textViewSortByPopularity.setTextColor(getResources().getColor(R.color.white));
            listMovieViewModel.startLoadDataRating();
        } else {
            binding.textViewSortByRating.setTextColor(getResources().getColor(R.color.white));
            binding.textViewSortByPopularity.setTextColor(getResources().getColor(R.color.design_default_color_primary));
            binding.switchSort.setChecked(false);
            listMovieViewModel.startLoadDataPopularity();
        }
    }

    private void setLoading(Boolean isLoading) {
        this.isLoading = isLoading;
        if (isLoading && binding.progressBarLoading.getVisibility() == View.INVISIBLE)
            binding.progressBarLoading.setVisibility(View.VISIBLE);

        if (!isLoading && binding.progressBarLoading.getVisibility() == View.VISIBLE)
            binding.progressBarLoading.setVisibility(View.INVISIBLE);

    }


    public static MainFragment getMainFragment() {
        return new MainFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}