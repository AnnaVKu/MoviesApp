package com.example.myapplication.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemReviewBinding;
import com.example.myapplication.model.api.pojo.Review;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews;

    public ReviewAdapter() {
        this.reviews = new ArrayList<>();
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ReviewViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReviewAdapter.ReviewViewHolder holder, int position) {
        holder.binding.textViewAuthor.setText(reviews.get(position).getAuthor());
        holder.binding.textViewContent.setText(reviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        private ItemReviewBinding binding;

        public ReviewViewHolder(ItemReviewBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
}
