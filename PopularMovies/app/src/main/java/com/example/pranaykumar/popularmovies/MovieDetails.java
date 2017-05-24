package com.example.pranaykumar.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    setTitle(R.string.Movie_details);

    Intent intent=getIntent();
    Bundle b=intent.getExtras();
    Movie currentMovie=b.getParcelable("movie");
    String movieTitle=currentMovie.getmMovieTitle();
    String posterId=currentMovie.getmImageResourceID();
    String movieOverView=currentMovie.getmOverView();
    String movieRating= String.valueOf(currentMovie.getmRating());
    String movieReleaseDate=currentMovie.getmDate();

    //Formatting date of this form("23-05-2017") to this form ("May 23,2017")
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    try {
      date = dt.parse(movieReleaseDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    SimpleDateFormat dt1 = new SimpleDateFormat("LLL dd,yyyy");
    mDate.setText(dt1.format(date));
    mTitle.setText(movieTitle);

    //Base URL for poster Url
    String basePosterUrl="http://image.tmdb.org/t/p//w185/";
    //Final URL to be passed into Picasso
    String finalPosterUrl=basePosterUrl+posterId;

    Context context=this;
    Picasso.with(context).load(finalPosterUrl).into(mPoster);

    mOverView.setText(movieOverView);
    mRating.setText(movieRating);
  }
}
