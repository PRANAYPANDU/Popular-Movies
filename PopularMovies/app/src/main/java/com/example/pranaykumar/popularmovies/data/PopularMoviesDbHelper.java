package com.example.pranaykumar.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.pranaykumar.popularmovies.data.PopularMoviesContract.FavouriteMoviesEntry;

/**
 * Created by PRANAYKUMAR on 10-06-2017.
 */

public class PopularMoviesDbHelper extends SQLiteOpenHelper{
  public static final String DATABASE="popularMovies.db";
  public static final int DATABASE_VERSION=2;
  SQLiteDatabase db;

  public PopularMoviesDbHelper(Context context) {
    super(context,DATABASE,null,DATABASE_VERSION);
    db=getWritableDatabase();
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    final String SQL_CREATE_FAVOURITE_MOVIES_TABLE=
        "CREATE TABLE "+ FavouriteMoviesEntry.TABLE_NAME+ " (" +
            FavouriteMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FavouriteMoviesEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            FavouriteMoviesEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
            FavouriteMoviesEntry.COLUMN_DATE + " TEXT NOT NULL, " +
            FavouriteMoviesEntry.COLUMN_RATING + " DOUBLE NOT NULL, " +
            FavouriteMoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
            FavouriteMoviesEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL);";
    Log.d("O_MY","in dbHelper onCreate");
    //Executing sql query
    db.execSQL(SQL_CREATE_FAVOURITE_MOVIES_TABLE);
    Log.d("O_MY","table created");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + FavouriteMoviesEntry.TABLE_NAME);
    onCreate(db);
  }
}