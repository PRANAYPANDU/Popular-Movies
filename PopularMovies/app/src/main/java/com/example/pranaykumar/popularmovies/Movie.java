package com.example.pranaykumar.popularmovies;

/**
 * Created by PRANAYKUMAR on 21-05-2017.
 */

public class Movie {
  //Title of the movie
  private String mMovieTitle;

  //darawable resource ID
  private int mImageResourceID;

  /*
  *Create a new Movie Object.
  *
  * @param vTitle is the title of the Movie(e.g.Interstellar)
  * @param imageResourceID is the drawable reference iD that corresponds to the movie
  * */

  public Movie(String vTitle,int imageResourceID){
    mMovieTitle=vTitle;
    mImageResourceID=imageResourceID;
  }

  //Get the title of the movie
  public String getmMovieTitle(){
    return mMovieTitle;
  }

  //Get image resource ID
  public int getmImageResourceID(){
    return mImageResourceID;
  }
}
