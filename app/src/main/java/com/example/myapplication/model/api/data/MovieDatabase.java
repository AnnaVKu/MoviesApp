package com.example.myapplication.model.api.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.model.api.pojo.FavoriteMovie;
import com.example.myapplication.model.api.pojo.Movie;

@Database(entities = {Movie.class, FavoriteMovie.class}, version = 7, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase db;
    private static final String DB_NAME = "movieDatabase";

    public static synchronized MovieDatabase getInstance(Context context) {
            if(db == null) db = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration().build();
        return db;
    }

    public abstract MovieDao getMovieDao();
}
