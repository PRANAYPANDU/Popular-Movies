package com.example.pranaykumar.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;

/**
 * Created by PRANAYKUMAR on 09-06-2017.
 */

public class MovieDetailsLoader extends AsyncTaskLoader<ArrayList<ArrayList<String>>> {
  private final String LOG_TAG="O_MY_GOD:";
  private String mVideosURL;
  private String mReviewsURL;

  public MovieDetailsLoader(Context context,String videosURL,String reviewsURL) {
    super(context);
    mVideosURL=videosURL;
    mReviewsURL=reviewsURL;
    Log.d(LOG_TAG,"urls set");
  }
  @Override protected void onStartLoading() {
    forceLoad();
  }
  @Override
  public ArrayList<ArrayList<String>> loadInBackground() {
    if(mReviewsURL==null||mVideosURL==null){
      return null;
    }
    ArrayList<String> videos=QueryUtils.fetchVideos(mVideosURL);
    ArrayList<String> reviews=QueryUtils.fetchReviews(mReviewsURL);
    ArrayList<ArrayList<String>>details= new ArrayList<>();
    Log.d(LOG_TAG,"videos,reviews set");
    details.add(0,videos);
    details.add(1,reviews);
    Log.d(LOG_TAG,"into details arraylist");
    return  details;
  }
}
