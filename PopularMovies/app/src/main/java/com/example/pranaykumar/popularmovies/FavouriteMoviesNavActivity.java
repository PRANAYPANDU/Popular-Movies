package com.example.pranaykumar.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.pranaykumar.popularmovies.data.PopularMoviesContract.FavouriteMoviesEntry;

public class FavouriteMoviesNavActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<Cursor>,NavigationView.OnNavigationItemSelectedListener {
  private static final int ID_FAVOURITE_MOVIES_LOADER=56;

  static final String[] FAV_MOVIES_PROJECTION={
      FavouriteMoviesEntry.COLUMN_POSTER,
      FavouriteMoviesEntry.COLUMN_NAME,
      FavouriteMoviesEntry.COLUMN_DATE,
      FavouriteMoviesEntry.COLUMN_RATING,
      FavouriteMoviesEntry.COLUMN_OVERVIEW,
      FavouriteMoviesEntry.COLUMN_MOVIE_ID,
  };
  RecyclerView recyclerView;
  TextView emptyView;
  ProgressBar loadingIndicator;
  private Context context;
  private FavouriteMoviesAdapter favouriteMoviesAdapter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favourite_movies_nav);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    setTitle(R.string.favourite_movies);

    context=this;

    //favouriteMoviesBinding= DataBindingUtil.setContentView(this,R.layout.activity_favourite_movies);
    GridLayoutManager gridLayoutManager
        =new GridLayoutManager(this,Utility.calculateNoOfColumns(getApplicationContext()));
    recyclerView= (RecyclerView) findViewById(R.id.Frecyclerview_movies);
    emptyView=(TextView)findViewById(R.id.Fempty_view);
    loadingIndicator=(ProgressBar)findViewById(R.id.Floading_indicator);
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.setHasFixedSize(true);
    favouriteMoviesAdapter=new FavouriteMoviesAdapter(this);
    recyclerView.setAdapter(favouriteMoviesAdapter);
    showLoading();
    getSupportLoaderManager().initLoader(ID_FAVOURITE_MOVIES_LOADER,null,this);


  }

  private void showLoading() {
    recyclerView.setVisibility(View.INVISIBLE);
    loadingIndicator.setVisibility(View.VISIBLE);
  }
  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.favourite_movies_nav, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_aboutMe) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_popularMovies) {
      Intent intent =new Intent(FavouriteMoviesNavActivity.this,FrontActivity.class);
      intent.setFlags(1);
      startActivity(intent);
      // Handle the camera action
    } else if (id == R.id.nav_topRatedMovies) {
      Intent intent1=new Intent(FavouriteMoviesNavActivity.this,FrontActivity.class);
      intent1.setFlags(2);
      startActivity(intent1);
    } else if (id == R.id.nav_nowPlaying) {
      Intent intent1=new Intent(FavouriteMoviesNavActivity.this,FrontActivity.class);
      intent1.setFlags(3);
      startActivity(intent1);

    } else if (id == R.id.nav_upcoming) {
      Intent intent1=new Intent(FavouriteMoviesNavActivity.this,FrontActivity.class);
      intent1.setFlags(4);
      startActivity(intent1);
    } else if(id==R.id.nav_favouriteMovies){
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
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
        throw new RuntimeException(getString(R.string.Loader_Not_implemented) + id);

    }
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    favouriteMoviesAdapter.swapCursor(data);

    //if(mPosition==RecyclerView.NO_POSITION)mPosition=0;
    //mRecyclerView.smoothScrollToPosition(mPosition);
    if(data.getCount()!=0){
      loadingIndicator.setVisibility(View.INVISIBLE);
      recyclerView.setVisibility(View.VISIBLE);
    }else{
      loadingIndicator.setVisibility(View.INVISIBLE);
      emptyView.setText(R.string.No_Favourites);
    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    favouriteMoviesAdapter.swapCursor(null);

  }
}

