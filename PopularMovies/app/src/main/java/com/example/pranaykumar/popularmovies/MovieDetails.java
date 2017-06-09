package com.example.pranaykumar.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<ArrayList<String >>> {
  private final String LOG_TAG="O_MY_GOD:";
  private String moviesDbURL="http://api.themoviedb.org/3/";
  private String apiKey="?api_key=857710a9c17b11d80aa32f98d00aa936";
  private static final int LOADER_ID = 1;
  private TextView mTitle;
  private ImageView mPoster;
  private TextView mDate;
  private TextView mRating;
  private TextView mReviews;
  private TextView mOverView;
  private TextView mTrailer1;
  private TextView mTrailer2;
  ArrayList<String> sTrailers;
  ArrayList<String> sReviews;
  String videosURL;
  String reviewsURL;
  String movieTitle;
  String posterId;
  String movieOverView;
  String movieRating;
  String movieReleaseDate;
  String id;
  String ReviewsTotal="";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_details);

    mTitle=(TextView)findViewById(R.id.titleTextView);
    mPoster=(ImageView)findViewById(R.id.posterImageView);
    mDate=(TextView)findViewById(R.id.dateTextView);
    mRating=(TextView)findViewById(R.id.ratingTextView);
    mOverView=(TextView)findViewById(R.id.overviewTextView);
    mReviews=(TextView)findViewById(R.id.text_view_reviews);
    mTrailer1=(TextView)findViewById(R.id.textViewTrailer1);
    mTrailer2=(TextView)findViewById(R.id.textViewTrailer2) ;
    setTitle(R.string.Movie_details);
    PlayVideoOnClickListener clickListener=new PlayVideoOnClickListener();
    mTrailer1.setOnClickListener(clickListener);
    mTrailer2.setOnClickListener(clickListener);
    findViewById(R.id.playVideoBtn1).setOnClickListener(clickListener);
    findViewById(R.id.playVideoBtn2).setOnClickListener(clickListener);

    Intent intent=getIntent();
    Bundle b=intent.getExtras();
    Movie currentMovie=b.getParcelable("movie");
    movieTitle=currentMovie.getmMovieTitle();
    posterId=currentMovie.getmImageResourceID();
    movieOverView=currentMovie.getmOverView();
    movieRating= String.valueOf(currentMovie.getmRating());
    movieReleaseDate=currentMovie.getmDate();
    id=currentMovie.getmId();
    videosURL=moviesDbURL+"movie/"+id+"/videos"+apiKey;
    reviewsURL=moviesDbURL+"movie/"+id+"/reviews"+apiKey;

    SimpleDateFormat dt1 = new SimpleDateFormat("LLL dd,yyyy");
    //Formatting date of this form("23-05-2017") to this form ("May 23,2017")
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    try {
      date = dt.parse(movieReleaseDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    //Base URL for poster Url
    String basePosterUrl = "http://image.tmdb.org/t/p//w185/";
    //Final URL to be passed into Picasso
    String finalPosterUrl = basePosterUrl + posterId;

    Context context = this;
    Picasso.with(context).load(finalPosterUrl).into(mPoster);

    mDate.setText(dt1.format(date));
    mTitle.setText(movieTitle);
    mOverView.setText(movieOverView);
    mRating.setText(movieRating);




    ConnectivityManager connMgr =
        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      android.app.LoaderManager loaderManager = getLoaderManager();
      loaderManager.initLoader(LOADER_ID, null, this);


    }
    /*//Base URL for poster Url
    String basePosterUrl = "http://image.tmdb.org/t/p//w185/";
    //Final URL to be passed into Picasso
    String finalPosterUrl = basePosterUrl + posterId;

    Context context = this;
    Picasso.with(context).load(finalPosterUrl).into(mPoster);

    mDate.setText(dt1.format(date));
    mTitle.setText(movieTitle);
    mOverView.setText(movieOverView);
    mRating.setText(movieRating);
    */



  }


  @Override
  public Loader<ArrayList<ArrayList<String>>> onCreateLoader(int id,
      Bundle args) {
    Log.d(LOG_TAG,"about to go to MovieDetailsLoader");
    return new MovieDetailsLoader(this,videosURL,reviewsURL);
  }

  @Override
  public void onLoadFinished(Loader<ArrayList<ArrayList<String>>> loader,
      ArrayList<ArrayList<String>> data) {
    sTrailers=data.get(0);
    sReviews=data.get(1);
    Log.d(LOG_TAG,"data set");
    setUI();
    Log.d(LOG_TAG,"setUI called");

  }

  private void setUI() {
    Log.d(LOG_TAG, "in setUI");
    int i = 0;
    int s = sReviews.size();
    while (s != 0) {
      ReviewsTotal = ReviewsTotal
          + "<b>\n\n------------------------------------------------------------------------\n\n</b>"
          + (i + 1) + "." + sReviews.get(i);
      i++;
      s--;
    }
    int t = sTrailers.size();
    mTrailer1.setTag(sTrailers.get(0));
    mTrailer2.setTag(sTrailers.get(1));
    mReviews.setText(Html.fromHtml(ReviewsTotal));

  }

  @Override
  public void onLoaderReset(Loader<ArrayList<ArrayList<String>>> loader) {
      sTrailers=null;
      sReviews=null;
  }

}
