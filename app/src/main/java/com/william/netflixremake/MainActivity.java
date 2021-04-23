package com.william.netflixremake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.william.netflixremake.model.Category;
import com.william.netflixremake.model.Movie;
import com.william.netflixremake.model.MovieDetail;
import com.william.netflixremake.util.CategoryTask;
import com.william.netflixremake.util.ImageDownloaderTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryTask.CategoryLoader {

    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv);

        List<Category> categories = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            Category category = new Category();
            category.setName("cat " + j);

            List<Movie> movies = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                Movie movie = new Movie();
//                movie.setCoverUrl(R.drawable.movie);
                movies.add(movie);
            }

            category.setMovies(movies);
            categories.add(category);
        }

        mainAdapter = new MainAdapter(categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(mainAdapter);

        CategoryTask categoryTask = new CategoryTask(this);
        categoryTask.setCategoryLoader(this);
        categoryTask.execute("https://tiagoaguiar.co/api/netflix/home");
    }

    @Override
    public void onResult(List<Category> categories) {
        mainAdapter.setCategories(categories);
        mainAdapter.notifyDataSetChanged();
    }

    private static class MovieHolder extends RecyclerView.ViewHolder {

        final ImageView imageViewUrl;

        public MovieHolder(@NonNull View itemView, final OnItemClickListner onItemClickListner) {
            super(itemView);
            imageViewUrl = itemView.findViewById(R.id.image_view_cover);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onClick(getAdapterPosition());
                }
            });
        }
    }

    private static class CategoryHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        RecyclerView recyclerViewMovies;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.txt_view_title);
            recyclerViewMovies = itemView.findViewById(R.id.recycler_view_movie);
        }
    }

    private class MainAdapter extends RecyclerView.Adapter<CategoryHolder> {

        private List<Category> categories;

        private MainAdapter(List<Category> categories) {
            this.categories = categories;
        }

        @NonNull
        @Override
        public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CategoryHolder(getLayoutInflater().inflate(R.layout.category_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
            Category category = categories.get(position);
            holder.textViewTitle.setText(category.getName());
            holder.recyclerViewMovies.setAdapter(new MovieAdapter(category.getMovies()));
            holder.recyclerViewMovies.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.HORIZONTAL, false));
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

        void setCategories(List<Category> categories) {
            this.categories.clear();
            this.categories.addAll(categories);
        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> implements OnItemClickListner {

        private final List<Movie> movies;

        private MovieAdapter(List<Movie> movies) {
            this.movies = movies;
        }

        @NonNull
        @Override
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.movie_item, parent, false);
            return new MovieHolder(view, this);
        }

        @Override
        public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
            Movie movie = movies.get(position);
            new ImageDownloaderTask(holder.imageViewUrl).execute(movie.getCoverUrl());
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }

        @Override
        public void onClick(int position) {
            if (movies.get(position).getId() <= 3) {
                Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                intent.putExtra("id", movies.get(position).getId());
                startActivity(intent);
            }
        }
    }

    private interface OnItemClickListner {
        void onClick(int position);
    }
}