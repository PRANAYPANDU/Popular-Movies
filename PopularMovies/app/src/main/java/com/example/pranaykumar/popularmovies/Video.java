package com.example.pranaykumar.popularmovies;

/**
 * Created by PRANAYKUMAR on 09-07-2017.
 */

public class Video {
  private String mVideoId;
  private String mVideoName;

  public Video(String videoID,String videoName){
    mVideoId=videoID;
    mVideoName=videoName;

  }
  public String getmVideoId() {
    return mVideoId;
  }
  public String getmVideoName() {
    return mVideoName;
  }
}
