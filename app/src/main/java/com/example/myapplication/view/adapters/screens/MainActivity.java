package com.example.myapplication.view.adapters.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public static final String KEY_FOR_POSITION = "KEY_POSITION";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemMain:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.your_placeholder, MainFragment.getMainFragment(), null)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.itemFavorite:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.your_placeholder, FavoriteFragment.getFavoriteFragment(), null)
                        .addToBackStack(null)
                        .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.your_placeholder, MainFragment.getMainFragment(), null)
                    .addToBackStack(null)
                    .commit();
        }
    }
}