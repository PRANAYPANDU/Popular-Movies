package com.example.pranaykumar.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.StrictMode;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager.Properties;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.example.pranaykumar.popularmovies.data.PopularMoviesContract.FavouriteMoviesEntry;

public class FavouriteMoviesActivity extends AppCompatActivity
implements LoaderManager.LoaderCallbacks<Cursor>{
  private static final int ID_FAVOURITE_MOVIES_LOADER=56;

  static final String[] FAV_MOVIES_PROJECTION={
      FavouriteMoviesEntry.COLUMN_POSTER,
      FavouriteMoviesEntry.COLUMN_NAME,
      FavouriteMoviesEntry.COLUMN_DATE,
      FavouriteMoviesEntry.COLUMN_RATING,
      FavouriteMoviesEntry.COLUMN_OVERVIEW,
      FavouriteMoviesEntry.COLUMN_MOVIE_ID,
  };

  private FavouriteMoviesAdapter favouriteMoviesAdapter;
  private RecyclerView mRecyclerView;
  private int mPosition=RecyclerView.NO_POSITION;

  private ProgressBar mLoadingIndicator;

  private Context context;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favourite_movies);
    setTitle(R.string.favourite_movies);
    context=this;
    mRecyclerView=(RecyclerView)findViewById(R.id.Frecyclerview_movies);
    mLoadingIndicator=(ProgressBar)findViewById(R.id.Floading_indicator);
    int columnsCount=2;
    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
      columnsCount=4;
    }
    GridLayoutManager gridLayoutManager
        =new GridLayoutManager(this,columnsCount);
    mRecyclerView.setLayoutManager(gridLayoutManager);
    mRecyclerView.setHasFixedSize(true);
    favouriteMoviesAdapter=new FavouriteMoviesAdapter(this);
    mRecyclerView.setAdapter(favouriteMoviesAdapter);
    showLoading();
    getSupportLoaderManager().initLoader(ID_FAVOURITE_MOVIES_LOADER,null,this);

  }

  private void showLoading() {
    mRecyclerView.setVisibility(View.INVISIBLE);
    mLoadingIndicator.setVisibility(View.VISIBLE);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.sort,menu);
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    MenuItem favMenu=menu.findItem(R.id.sortBy_favourite_Movies);
    favMenu.setVisible(false);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()){
      case R.id.sortBy_popularMovies:
        Intent intent =new Intent(FavouriteMoviesActivity.this,MainActivity.class);
        intent.setFlags(1);
        startActivity(intent);
        break;
      case R.id.sortBy_top_rated_Movies:
        Intent intent1=new Intent(FavouriteMoviesActivity.this,MainActivity.class);
        intent1.setFlags(2);
        startActivity(intent1);
        break;

    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    switch (id){
      case ID_FAVOURITE_MOVIES_LOADER:
        Uri favMoviesQueryUri= FavouriteMoviesEntry.CONTENT_URI;

        return new CursorLoader(this,
            favMoviesQueryUri,
            FAV_MOVIES_PROJECTION,
            null,
            null,
            null
            );
      default:
        throw new RuntimeException("Loader Not Implemented: " + id);

    }
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    favouriteMoviesAdapter.swapCursor(data);
    if(mPosition==RecyclerView.NO_POSITION)mPosition=0;
    mRecyclerView.smoothScrollToPosition(mPosition);
    if(data.getCount()!=0){
      mLoadingIndicator.setVisibility(View.INVISIBLE);
      mRecyclerView.setVisibility(View.VISIBLE);

    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    favouriteMoviesAdapter.swapCursor(null);
  }
}
