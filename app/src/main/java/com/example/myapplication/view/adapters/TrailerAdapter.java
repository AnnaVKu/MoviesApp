package com.example.myapplication.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.api.pojo.Trailer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private static final String BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private List<Trailer> trailers;
    private OnTrailerClickListener onTrailerClickListener;

    public TrailerAdapter() {
        this.trailers = new ArrayList<>();
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public interface OnTrailerClickListener {
        void onTrailerClick(String url);
    }

    public void setOnTrailerClickListener(OnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TrailerAdapter.TrailerViewHolder holder, int position) {
        holder.textViewNameOfVideo.setText(trailers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNameOfVideo;

        public TrailerViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            textViewNameOfVideo = itemView.findViewById(R.id.textViewNameOfVideo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onTrailerClickListener != null) {
                        onTrailerClickListener.onTrailerClick(BASE_YOUTUBE_URL + trailers.get(getAdapterPosition()).getKey());
                    }
                }
            });
        }
    }
}
