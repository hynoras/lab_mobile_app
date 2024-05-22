package com.example.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView posterImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.movie_title);
            posterImageView = itemView.findViewById(R.id.movie_poster);
        }
    }
}

