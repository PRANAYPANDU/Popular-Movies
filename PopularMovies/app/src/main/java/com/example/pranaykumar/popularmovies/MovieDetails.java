package com.example.pranaykumar.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

  private TextView mTitle;
  private ImageView mPoster;
  private TextView mDate;
  private TextView mRating;
  private TextView mOverView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_details);

    mTitle=(TextView)findViewById(R.id.titleTextView);
    mPoster=(ImageView)findViewById(R.id.posterImageView);
    mDate=(TextView)findViewById(R.id.dateTextView);
    mRating=(TextView)findViewById(R.id.ratingTextView);
    mOverView=(TextView)findViewById(R.id.overviewTextView);

    Intent intent=getIntent();
    Bundle b=intent.getExtras();
    Movie currentMovie=b.getParcelable("movie");
    String movieTitle=currentMovie.getmMovieTitle();
    String posterId=currentMovie.getmImageResourceID();
    String movieOverView=currentMovie.getmOverView();
    String movieRating= String.valueOf(currentMovie.getmRating());
    String movieReleaseDate=currentMovie.getmDate();
    mTitle.setText(movieTitle);
    //mPoster.setImageResource(posterId);
    String basePosterUrl="http://image.tmdb.org/t/p//w185/";
    String finalPosterUrl=basePosterUrl+posterId;
    Context context=this;
    Picasso.with(context).load(finalPosterUrl).into(mPoster);

    mOverView.setText(movieOverView);
    mRating.setText(movieRating);
    mDate.setText(movieReleaseDate);



  }
}
