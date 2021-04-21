package com.william.netflixremake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.william.netflixremake.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtDesc;
    private TextView txtCast;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        txtTitle = findViewById(R.id.text_view_title);
        txtDesc = findViewById(R.id.text_view_desc);
        txtCast = findViewById(R.id.text_view_cast);
        recyclerView = findViewById(R.id.recycler_view_similar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setTitle(null);

        LayerDrawable drawable = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.shadow);

        if(drawable != null){
            Drawable movieCover = ContextCompat.getDrawable(this, R.drawable.movie_4);
            drawable.setDrawableByLayerId(R.id.cover_drawable, movieCover);
            ((ImageView) findViewById(R.id.image_view_cover)).setImageDrawable(drawable);
        }

        txtTitle.setText("Batman Begins");
        txtDesc.setText("O jovem Bruce Wayne viaja para o Extremo Oriente, onde recebe treinamento em artes marciais do mestre Henri Ducard, um membro da misteriosa Liga das Sombras. Quando Ducard revela que a verdadeira proposta da Liga é a destruição completa da cidade de Gotham, Wayne retorna à sua cidade com o intuito de livrá-la de criminosos e assassinos. Com a ajuda do mordomo Alfred e do expert Lucius Fox, nasce Batman.");
        txtCast.setText(getString(R.string.cast,"Joao JCsi"+", Ijdiajs"+", naisdniasnna"+", jdaijdisajd"));

        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            Movie movie = new Movie();
//                movie.setCoverUrl(R.drawable.movie);
            movies.add(movie);
        }

        recyclerView.setAdapter(new MovieAdapter(movies));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

    }

    private static class MovieHolder extends RecyclerView.ViewHolder{

        final ImageView imageViewUrl;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUrl = itemView.findViewById(R.id.image_view_cover);
        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{

        private final List<Movie> movies;

        private MovieAdapter(List<Movie> movies){
            this.movies = movies;
        }

        @NonNull
        @Override
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MovieHolder(getLayoutInflater().inflate(R.layout.movie_item_similar, parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
            Movie movie = movies.get(position);
//            holder.imageViewUrl.setImageResource(movie.getCoverUrl());
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }

}