package com.example.pranaykumar.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PRANAYKUMAR on 21-05-2017.
 */

public class Movie implements Parcelable {
  //Title of the movie
  private String mMovieTitle;
  private String mImageResourceURL;
  private String mOverView;
  private double mRating;
  private String mDate;
  private String mID;

  /*
  *Create a new Movie Object.
  *
  * @param vTitle is the title of the Movie(e.g.Interstellar)
  * @param imageResourceID is the drawable reference iD that corresponds to the movie
  * */

  public Movie(String vTitle, String imageResourceURL, String vOverView, double vRating,
      String vDate,String id) {
    mMovieTitle = vTitle;
    mImageResourceURL = imageResourceURL;
    mOverView = vOverView;
    mRating = vRating;
    mDate = vDate;
    mID=id;

  }

  public Movie(Parcel source) {
    mMovieTitle = source.readString();
    mImageResourceURL = source.readString();
    mOverView = source.readString();
    mRating = source.readDouble();
    mDate = source.readString();
    mID=source.readString();
  }

  //Get the title of the movie
  public String getmMovieTitle() {
    return mMovieTitle;
  }

  //Get image resource ID
  public String getmImageResourceID() {
    return mImageResourceURL;
  }

  public String getmOverView() {
    return mOverView;
  }

  public double getmRating() {
    return mRating;
  }

  public String getmDate() {
    return mDate;
  }

  public String getmId(){return mID;}


  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(mMovieTitle);
    dest.writeString(mImageResourceURL);
    dest.writeString(mOverView);
    dest.writeDouble(mRating);
    dest.writeString(mDate);
    dest.writeString(mID);
  }

  public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
    @Override public Movie createFromParcel(Parcel source) {
      return new Movie(source);
    }

    @Override public Movie[] newArray(int i) {
      return new Movie[i];
    }
  };
}
