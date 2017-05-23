package com.example.pranaykumar.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Create an ArrayList of Movie Objects with Movie poster and title
    final ArrayList<Movie> movies=new ArrayList<Movie>();
    movies.add(new Movie("Beauty and the Beast",R.drawable.img1,"nicdfffffffffe Movie",4,"22-5-2016"));
    movies.add(new Movie("Fight Club",R.drawable.img2,"nice Mosfffffffffffffffffffffvie",5,"22-5-2016"));
    movies.add(new Movie("Saving Private Ryan",R.drawable.img3,"nice Movsaaaaaaaaie",4,"22-5-2016"));
    movies.add(new Movie("the silence of the lambs",R.drawable.img4,"nicessssssssssssssssssdd Movie",4,"22-5-2016"));
    movies.add(new Movie("Moonlight",R.drawable.img5,"nice Movisssddddddddddde",4,"22-5-2016"));
    movies.add(new Movie("The Grey",R.drawable.img6,"nice Movidcdccccccccccccccccccccsssssssssssssssssffffffffffffe",4,"22-5-2016"));
    movies.add(new Movie("Naya",R.drawable.img7,"nice Movdvddfsdfffffffffffffffffffffie",4,"22-5-2016"));
    movies.add(new Movie("Pirates of the Caribbean",R.drawable.img8,"nice Movieasasssssssssss",4,"22-5-2016"));
    movies.add(new Movie("Perfume",R.drawable.img9,"nice Movieadssdddddddddddasss",4,"22-5-2016"));
    MovieAdapter movieAdapter =new MovieAdapter(this,movies);


    GridView gridView=(GridView)findViewById(R.id.grid_view);
    gridView.setAdapter(movieAdapter);

    //Setup the item click listener
    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(MainActivity.this,MovieDetails.class);
        intent.putExtra("movie",movies.get(position));
        startActivity(intent);

      }}
    );
  }
}
