package com.example.pranaykumar.popularmovies;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;

public class FrontActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,LoaderManager.LoaderCallbacks<ArrayList<Movie>>{
  private static final int MOVIES_LOADER_ID=1;
  private static final String NOW_SHOWING_MOVIES_DB_REQUEST_URL = "http://api.themoviedb.org/3/movie/now_playing?api_key=857710a9c17b11d80aa32f98d00aa936";
  int isPopularMovies=1;
  //URL for popular movies,insert your API key in place of YourAPIKey
  private static final String POPULARMOVIES_DB_REQUEST_URL ="http://api.themoviedb.org/3/movie/popular?api_key=857710a9c17b11d80aa32f98d00aa936";
  //URL for top rated movies,insert your API key in place of YourAPIKey
  private static final String UP_COMING_DB_REQUEST_URL="https://api.themoviedb.org/3/movie/upcoming?api_key=857710a9c17b11d80aa32f98d00aa936";
  private static final String TOP_RATED_MOVIES_DB_REQUEST_URL ="http://api.themoviedb.org/3/movie/top_rated?api_key=857710a9c17b11d80aa32f98d00aa936";

  private String final_url= POPULARMOVIES_DB_REQUEST_URL;
  DrawerLayout drawer;
  ArrayList<Movie> movies;
  Menu menuFav;
  /**TextView that is displayed when the list is empty*/

  private MoviesAdapter mMoviesAdapter;
  private int mPosition;
  private ProgressBar loadingIndicator;
  private TextView EmptyView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_front);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

     drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    if(getIntent()!=null){
      Intent intent=getIntent();
      int i=intent.getFlags();
      if(i==2){
        navigationView.getMenu().findItem(R.id.nav_topRatedMovies).setChecked(true);
        final_url= TOP_RATED_MOVIES_DB_REQUEST_URL;
        setTitle(R.string.sortBy_top_rated_Movies);
      }else if(i==3){
        navigationView.getMenu().findItem(R.id.nav_nowPlaying).setChecked(true);
        final_url=NOW_SHOWING_MOVIES_DB_REQUEST_URL;
        setTitle("Now Playing");

      }else if(i==4){
        navigationView.getMenu().findItem(R.id.nav_upcoming).setChecked(true);
        final_url=UP_COMING_DB_REQUEST_URL;
        setTitle("Up Coming Movies");
      }
    }
    movies=new ArrayList<Movie>();


    //mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);


    GridLayoutManager gridLayoutManager
        =new GridLayoutManager(this,Utility.calculateNoOfColumns(getApplicationContext()));

    RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recyclerview_movies);
    loadingIndicator=(ProgressBar)findViewById(R.id.loading_indicator);
    EmptyView=(TextView)findViewById(R.id.empty_view);
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.setHasFixedSize(true);
    mMoviesAdapter=new MoviesAdapter(movies);
    recyclerView.setAdapter(mMoviesAdapter);

    //Get a reference to the ConnectivityManager to check state of network connectivity
    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(
        Context.CONNECTIVITY_SERVICE);

    //Get details on the currently active default data network
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

    //If there is a network connection,fetch data
    if (networkInfo != null && networkInfo.isConnected()) {
      //Get a reference to the LoaderManager,in order to interact with loaders

      android.app.LoaderManager loaderManager=getLoaderManager();
      // Initialize the loader. Pass in the int ID constant defined above and pass in null for
      // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
      // because this activity implements the LoaderCallbacks interface).
      loaderManager.initLoader(MOVIES_LOADER_ID,null,this);
    }
    else{
      loadingIndicator.setVisibility(View.GONE);
      EmptyView.setText(R.string.no_internet_connection);
    }
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
  @Override public boolean onOptionsItemSelected(MenuItem item) {

    //User clicked on a menu option in the app bar overflow menu
    if(item.getItemId()==R.id.action_aboutMe){

    }
    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_popularMovies) {
      final_url= POPULARMOVIES_DB_REQUEST_URL;
      isPopularMovies=1;
      getLoaderManager().restartLoader(MOVIES_LOADER_ID, null,this);
      setTitle(getString(R.string.app_name));
      // Handle the camera action
    } else if (id == R.id.nav_topRatedMovies) {
      final_url= TOP_RATED_MOVIES_DB_REQUEST_URL;
      isPopularMovies=0;
      getLoaderManager().restartLoader(MOVIES_LOADER_ID, null,this);
      setTitle(getString(R.string.sortBy_top_rated_Movies));
      return true;
    } else if (id == R.id.nav_nowPlaying) {
      final_url=NOW_SHOWING_MOVIES_DB_REQUEST_URL;
      isPopularMovies=0;
      getLoaderManager().restartLoader(MOVIES_LOADER_ID, null,this);
      setTitle("Now Showing");
      return true;
    } else if (id == R.id.nav_upcoming) {
      final_url=UP_COMING_DB_REQUEST_URL;
      isPopularMovies=0;
      getLoaderManager().restartLoader(MOVIES_LOADER_ID, null,this);
      setTitle("Up Coming Movies");
      return true;
    } else if(id==R.id.nav_favouriteMovies){
      Intent favIntent=new Intent(FrontActivity.this,FavouriteMoviesNavActivity.class);
      startActivity(favIntent);
    }
    else if (id==R.id.nav_share){
    }
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
  private void doMySearch(String query) {
    Log.d("O_MY","Searched mivie name is:"+query);
    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(
        Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      //Get a reference to the LoaderManager,in order to interact with loaders

      android.app.LoaderManager loaderManager=getLoaderManager();
      // Initialize the loader. Pass in the int ID constant defined above and pass in null for
      // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
      // because this activity implements the LoaderCallbacks interface).
      final_url="https://api.themoviedb.org/3/search/movie?api_key=857710a9c17b11d80aa32f98d00aa936&query="+query;
      //setTitle("Results for "+query);
      loaderManager.destroyLoader(MOVIES_LOADER_ID);
      loaderManager.initLoader(MOVIES_LOADER_ID,null,this);
    }
  }

  @Override public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {

    return new MoviesLoader(this,final_url);
  }

  @Override public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {

    loadingIndicator.setVisibility(View.GONE);
    // Set empty state text to display "No Movies found."
    EmptyView.setText(R.string.no_movies);
    if(data!=null&&!data.isEmpty()){
      Log.d("O_MY", String.valueOf(data.size()));
      mMoviesAdapter.setmMoviesData(data);
      EmptyView.setVisibility(View.GONE);
    }
  }

  @Override public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
    //Loader reset,so we can clear out our existing data
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    //inflate the menu options from the res/menu/sort.xml
    //This adds menu items to the app bar.
    menuFav=menu;
    getMenuInflater().inflate(R.menu.front, menu);
    //Adding search bar
    getMenuInflater().inflate(R.menu.search,menu);
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
    EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
    searchEditText.setHint("Search for a Movie");
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    searchView.setOnQueryTextListener(new OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        doMySearch(query);
        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return true;
      }
    });
    MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search), new MenuItemCompat.OnActionExpandListener() {
      @Override
      public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
      }
      @Override
      public boolean onMenuItemActionCollapse(MenuItem item) {
        // Write your code here
        backToMain(isPopularMovies);
        return true;
      }
    });

    return true;
  }

  private void backToMain(int isPopularMovies) {
    if(isPopularMovies==1)
      final_url= POPULARMOVIES_DB_REQUEST_URL;
    else
      final_url= TOP_RATED_MOVIES_DB_REQUEST_URL;

    getLoaderManager().restartLoader(MOVIES_LOADER_ID, null,this);
  }
}
