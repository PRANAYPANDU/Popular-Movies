package com.example.pranaykumar.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat.CryptoObject;
import android.util.Log;
import com.example.pranaykumar.popularmovies.data.PopularMoviesContract.FavouriteMoviesEntry;


/**
 * Created by PRANAYKUMAR on 10-06-2017.
 */

public class PopularMoviesContentProvider extends ContentProvider {

  public static final int MOVIES=100;
  public static final int MOVIES_WITH_ID=101;
  private PopularMoviesDbHelper moviesDbHelper;

  private static final UriMatcher sUriMatcher=buildUriMatcher();

  private static UriMatcher buildUriMatcher() {
    UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);

    uriMatcher.addURI(PopularMoviesContract.AUTHORITY,PopularMoviesContract.PATH_MOVIES,MOVIES);
    uriMatcher.addURI(PopularMoviesContract.AUTHORITY,PopularMoviesContract.PATH_MOVIES + "/#",MOVIES_WITH_ID);

    return uriMatcher;
  }



  @Override
  public boolean onCreate() {

    Context context=getContext();
    moviesDbHelper=new PopularMoviesDbHelper(context);
    Log.d("O_MY","After calling db helper");
    return true;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
      @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    return null;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return null;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    Context context=getContext();
    moviesDbHelper=new PopularMoviesDbHelper(context);
    SQLiteDatabase db=moviesDbHelper.getWritableDatabase();

    int match=sUriMatcher.match(uri);
    Uri returnUri;//URI to be returned

    switch (match){
      case MOVIES:

        long id=db.insert(FavouriteMoviesEntry.TABLE_NAME,null,values);
        Log.d("O_MY","Inserted");
        if(id>0){
          returnUri= ContentUris.withAppendedId(FavouriteMoviesEntry.CONTENT_URI,id);
        }else{
          throw new android.database.SQLException("Failed to insert data into"+uri);
        }
        break;
      default:
        throw new UnsupportedOperationException("Unknown URI:"+uri);
    }

    //Notify the resolver if the uri has been changed and return the newly inserted URI
    getContext().getContentResolver().notifyChange(uri,null);
    return returnUri;
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    final SQLiteDatabase db=moviesDbHelper.getWritableDatabase();
    int match=sUriMatcher.match(uri);
    int moviesDeleted;
    switch (match){
      case MOVIES_WITH_ID:
        String MovieId=uri.getPathSegments().get(1);
        moviesDeleted=db.delete(FavouriteMoviesEntry.TABLE_NAME,"movieID=?",new String[]{MovieId});
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri:"+uri);
    }
    if(moviesDeleted!=0){
      getContext().getContentResolver().notifyChange(uri,null);
    }
    return moviesDeleted;
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    return 0;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
      @Nullable String[] selectionArgs, @Nullable String sortOrder,
      @Nullable CancellationSignal cancellationSignal) {
    return super.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
  }
}
