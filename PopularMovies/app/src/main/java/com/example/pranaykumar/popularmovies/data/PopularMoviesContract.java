package com.example.pranaykumar.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by PRANAYKUMAR on 10-06-2017.
 */

public class PopularMoviesContract {

  // The authority, which is how your code knows which Content Provider to access
  public static final String AUTHORITY = "com.example.pranaykumar.popularmovies";

  // The base content URI = "content://" + <authority>
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

  // Define the possible paths for accessing data in this contract
  // This is the path for the "favouriteMovies" directory
  public static final String PATH_MOVIES = "favouriteMovies";


  public static final class FavouriteMoviesEntry implements BaseColumns {
    //FavouriteMoviesEntry content URI=base content URI + path
    public static final Uri CONTENT_URI=
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
    //Table Name
    public static final String TABLE_NAME="favouriteMovies";

    //Columns
    public static final String COLUMN_NAME="title";
    public static final String COLUMN_POSTER="poster";
    public static final String COLUMN_DATE="releaseDate";
    public static final String COLUMN_RATING="rating";
    public static final String COLUMN_OVERVIEW="overview";
    public static final String COLUMN_MOVIE_ID="movieID";

  }

}