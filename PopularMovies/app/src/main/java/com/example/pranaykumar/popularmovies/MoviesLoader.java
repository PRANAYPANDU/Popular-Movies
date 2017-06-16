package com.example.pranaykumar.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.ArrayList;

/**
 * Created by PRANAYKUMAR on 23-05-2017.
 */

public class MoviesLoader extends AsyncTaskLoader<ArrayList<Movie>> {

  private String mUrl;

  public MoviesLoader(Context context, String movies_url) {
    super(context);
    mUrl = movies_url;
  }

  @Override
  protected void onStartLoading() {
    forceLoad();
  }

  @Override
  public ArrayList<Movie> loadInBackground() {
    if (mUrl == null) {
      return null;
    }
    ArrayList<Movie> moviesFeed = QueryUtils.fetchMovies(mUrl);
    return moviesFeed;
  }
}