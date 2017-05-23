package com.example.pranaykumar.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PRANAYKUMAR on 21-05-2017.
 */

public class Movie implements Parcelable{
  //Title of the movie
  private String mMovieTitle;

  //darawable resource ID
  //private int mImageResourceID;
  private String mImageResourceID;
  private String mOverView;
  private double mRating;
  private String mDate;

  /*
  *Create a new Movie Object.
  *
  * @param vTitle is the title of the Movie(e.g.Interstellar)
  * @param imageResourceID is the drawable reference iD that corresponds to the movie
  * */

  public Movie(String vTitle,String imageResourceID,String vOverView,double vRating,String vDate){
    mMovieTitle=vTitle;
    mImageResourceID=imageResourceID;
    mOverView=vOverView;
    mRating=vRating;
    mDate=vDate;
  }

  public Movie(Parcel source) {
    mMovieTitle=source.readString();
    mImageResourceID=source.readString();
    mOverView=source.readString();
    mRating=source.readDouble();
    mDate=source.readString();
  }

  //Get the title of the movie
  public String getmMovieTitle(){
    return mMovieTitle;
  }

  //Get image resource ID
  public String getmImageResourceID(){
    return mImageResourceID;
  }

  public String getmOverView(){return mOverView;}

  public double getmRating(){return mRating;}

  public String getmDate(){return mDate;}

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(mMovieTitle);
    dest.writeString(mImageResourceID);
    dest.writeString(mOverView);
    dest.writeDouble(mRating);
    dest.writeString(mDate);
  }
  public static final Parcelable.Creator CREATOR
      = new Parcelable.Creator() {
    @Override public Movie createFromParcel(Parcel source) {
      return new Movie(source);
    }

    @Override public Movie[] newArray(int i) {
      return new Movie[i];
    }
  };
}

