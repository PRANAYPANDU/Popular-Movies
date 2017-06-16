package com.example.pranaykumar.popularmovies;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.example.pranaykumar.popularmovies.data.PopularMoviesContract.FavouriteMoviesEntry;
import com.example.pranaykumar.popularmovies.data.PopularMoviesDbHelper;
import com.example.pranaykumar.popularmovies.databinding.ActivityMovieDetailsBinding;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<ArrayList<String >>>{

  ActivityMovieDetailsBinding movieDetailsBinding;


  private String moviesDbURL="http://api.themoviedb.org/3/";
  private String apiKey="?api_key=857710a9c17b11d80aa32f98d00aa936";
  private static final int LOADER_ID = 1;

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
  int isFav=0;
  String ReviewsTotal="";
  String finalPosterUrl;
Cursor cursor;
  private static final String[] FAV_MOVIE_PROJECTION={
      FavouriteMoviesEntry.COLUMN_NAME,
  };
  private static  String selection;
  private static String[] selectionArgs;

  @RequiresApi(api = VERSION_CODES.JELLY_BEAN)
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_details);
    movieDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_movie_details);



    setTitle(R.string.Movie_details);
    PlayVideoOnClickListener clickListener=new PlayVideoOnClickListener();
    movieDetailsBinding.textViewTrailer1.setOnClickListener(clickListener);
    movieDetailsBinding.textViewTrailer2.setOnClickListener(clickListener);
    movieDetailsBinding.playVideoBtn1.setOnClickListener(clickListener);
    movieDetailsBinding.playVideoBtn2.setOnClickListener(clickListener);

    Intent intent=getIntent();
      Bundle b=intent.getExtras();
      Movie currentMovie = b.getParcelable("movie");
      movieTitle = currentMovie.getmMovieTitle();
      posterId = currentMovie.getmImageResourceID();
      movieOverView = currentMovie.getmOverView();
      movieRating = String.valueOf(currentMovie.getmRating());
      movieReleaseDate = currentMovie.getmDate();
      id = currentMovie.getmId();
    isFav=currentMovie.getmIsFav();
    searchDB.execute();

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
    String basePosterUrl="http://image.tmdb.org/t/p//w185/";
    //Final URL to be passed into Picasso
    finalPosterUrl = basePosterUrl + posterId;

    Context context = this;
    Picasso.with(context).load(finalPosterUrl).into(movieDetailsBinding.posterImageView);
    movieRating=movieRating+"/10";

    movieDetailsBinding.dateTextView.setText(dt1.format(date));

    movieDetailsBinding.titleTextView.setText(movieTitle);
    movieDetailsBinding.overviewTextView.setText(movieOverView);

    movieDetailsBinding.ratingTextView.setText(movieRating);

    ConnectivityManager connMgr =
        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      android.app.LoaderManager loaderManager = getLoaderManager();
      loaderManager.initLoader(LOADER_ID, null, this);
    }
    if(isFav==1){
      movieDetailsBinding.markAsFavButton.setImageResource(R.drawable.ic_favorite_red_a700_36dp);
    }

        movieDetailsBinding.markAsFavButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (isFav==0) {
          String message = movieTitle + getString(R.string.marked_as_fav);
          Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
          movieDetailsBinding.markAsFavButton.setImageResource(R.drawable.ic_favorite_red_a700_36dp);
          addToDb();
          isFav = 1;

        }
        else if(isFav==1){
          String message=movieTitle+getString(R.string.removed_from_fav);
          Toast.makeText(v.getContext(),message,Toast.LENGTH_SHORT).show();
          movieDetailsBinding.markAsFavButton.setImageResource(R.drawable.ic_favorite_border_red_400_36dp);
          removeFromFav();
          isFav=0;
        }
      }
    });

  }
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
    }

    return(super.onOptionsItemSelected(item));
  }

  private void removeFromFav() {
    Uri uri= FavouriteMoviesEntry.CONTENT_URI;
    uri=uri.buildUpon().appendPath(id).build();
    getContentResolver().delete(uri,null,null);

  }

  private void addToDb() {
    ContentValues contentValues=new ContentValues();
    contentValues.put(FavouriteMoviesEntry.COLUMN_NAME,movieTitle);
    contentValues.put(FavouriteMoviesEntry.COLUMN_POSTER,posterId);
    contentValues.put(FavouriteMoviesEntry.COLUMN_DATE,movieReleaseDate);
    contentValues.put(FavouriteMoviesEntry.COLUMN_RATING,movieRating);
    contentValues.put(FavouriteMoviesEntry.COLUMN_OVERVIEW,movieOverView);
    contentValues.put(FavouriteMoviesEntry.COLUMN_MOVIE_ID,id);

    getContentResolver().insert(FavouriteMoviesEntry.CONTENT_URI,contentValues);
  }

  @Override
  public Loader<ArrayList<ArrayList<String>>> onCreateLoader(int id,
      Bundle args) {

    return new MovieDetailsLoader(this,videosURL,reviewsURL);
  }
  AsyncTask<Void,Void,Cursor> searchDB= new AsyncTask<Void, Void, Cursor>() {
    @Override
    protected Cursor doInBackground(Void... params) {
      selection=FavouriteMoviesEntry.COLUMN_MOVIE_ID+"=?";
      selectionArgs=new String[]{id};
      PopularMoviesDbHelper moviesDbHelper=new PopularMoviesDbHelper(getApplicationContext());
      SQLiteDatabase db=moviesDbHelper.getReadableDatabase();
      cursor=db.query(FavouriteMoviesEntry.TABLE_NAME,FAV_MOVIE_PROJECTION,selection,selectionArgs,null,null,null);
      return cursor;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
      if(cursor.getCount()!=0){
        isFav=1;
        movieDetailsBinding.markAsFavButton.setImageResource(R.drawable.ic_favorite_red_a700_36dp);
      }
    }
  };
  @Override
  public void onLoadFinished(Loader<ArrayList<ArrayList<String>>> loader,
      ArrayList<ArrayList<String>> data) {
    sTrailers=data.get(0);
    sReviews=data.get(1);
    setUI();
  }


  private void setUI() {
    int i = 0;
    int s = sReviews.size();
    while (s>0) {
      ReviewsTotal = ReviewsTotal
          +"<p></br>"
          + (i + 1) + "." + sReviews.get(i)
          +"</br></p>";
      i++;
      s--;
    }
    int t = sTrailers.size();
    if(t>0) {
      movieDetailsBinding.textViewTrailer1.setTag(sTrailers.get(0));
      if (t >= 2) {
        movieDetailsBinding.textViewTrailer2.setTag(sTrailers.get(1));
      } else {
        movieDetailsBinding.textViewTrailer2.setVisibility(View.INVISIBLE);
        movieDetailsBinding.markAsFavButton.setVisibility(View.INVISIBLE);
      }
    }
    movieDetailsBinding.textViewReviews.setText(Html.fromHtml(ReviewsTotal));

  }

  @Override
  public void onLoaderReset(Loader<ArrayList<ArrayList<String>>> loader) {
    sTrailers=null;
    sReviews=null;
  }


}