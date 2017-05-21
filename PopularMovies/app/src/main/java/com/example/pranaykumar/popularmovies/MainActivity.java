package com.example.pranaykumar.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Create an ArrayList of Movie Objects with Movie poster and title
    ArrayList<Movie> movies=new ArrayList<Movie>();
    movies.add(new Movie("Beauty and the Beast",R.drawable.img1));
    movies.add(new Movie("Fight Club",R.drawable.img2));
    movies.add(new Movie("Saving Private Ryan",R.drawable.img3));
    movies.add(new Movie("the silence of the lambs",R.drawable.img4));
    movies.add(new Movie("Moonlight",R.drawable.img5));
    movies.add(new Movie("The Grey",R.drawable.img6));
    movies.add(new Movie("Naya",R.drawable.img7));
    movies.add(new Movie("Pirates of the Caribbean",R.drawable.img8));
    movies.add(new Movie("Perfume",R.drawable.img9));
    MovieAdapter movieAdapter =new MovieAdapter(this,movies);

    GridView gridView=(GridView)findViewById(R.id.grid_view);
    gridView.setAdapter(movieAdapter);
  }
}
