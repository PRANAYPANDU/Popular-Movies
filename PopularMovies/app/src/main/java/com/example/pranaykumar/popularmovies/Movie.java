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
  private int mImageResourceID;
  private String mOverView;
  private int mRating;
  private String mDate;

  /*
  *Create a new Movie Object.
  *
  * @param vTitle is the title of the Movie(e.g.Interstellar)
  * @param imageResourceID is the drawable reference iD that corresponds to the movie
  * */

  public Movie(String vTitle,int imageResourceID,String vOverView,int vRating,String vDate){
    mMovieTitle=vTitle;
    mImageResourceID=imageResourceID;
    mOverView=vOverView;
    mRating=vRating;
    mDate=vDate;
  }

  public Movie(Parcel source) {
    mMovieTitle=source.readString();
    mImageResourceID=source.readInt();
    mOverView=source.readString();
    mRating=source.readInt();
    mDate=source.readString();
  }

  //Get the title of the movie
  public String getmMovieTitle(){
    return mMovieTitle;
  }

  //Get image resource ID
  public int getmImageResourceID(){
    return mImageResourceID;
  }

  public String getmOverView(){return mOverView;}

  public int getmRating(){return mRating;}

  public String getmDate(){return mDate;}

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(mMovieTitle);
    dest.writeInt(mImageResourceID);
    dest.writeString(mOverView);
    dest.writeInt(mRating);
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

