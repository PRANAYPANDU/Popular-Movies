package com.example.pranaykumar.popularmovies;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.pranaykumar.popularmovies.databinding.ActivityMainBinding;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{

  private static final int MOVIES_LOADER_ID=1;
  ActivityMainBinding mainBinding;

  //URL for popular movies,insert your API key in place of YourAPIKey
  private static final String PopularmoviesDB_REQUEST_URL="http://api.themoviedb.org/3/movie/popular?api_key=YOUR_API_KEY";
  //URL for top rated movies,insert your API key in place of YourAPIKey
  private static final String top_ratedMoviesDB_REQUEST_URL="http://api.themoviedb.org/3/movie/top_rated?api_key=YOUR_API_KEY";

  private String final_url=PopularmoviesDB_REQUEST_URL;

  ArrayList<Movie> movies;

  /**TextView that is displayed when the lsit is empty*/

  private MoviesAdapter mMoviesAdapter;
  private int mPosition;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    if(getIntent()!=null){
      Intent intent=getIntent();
      int i=intent.getFlags();
      if(i==2){
        final_url=top_ratedMoviesDB_REQUEST_URL;
        setTitle(R.string.sortBy_top_rated_Movies);
      }

    }
    movies=new ArrayList<Movie>();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);


    GridLayoutManager gridLayoutManager
        =new GridLayoutManager(this,Utility.calculateNoOfColumns(getApplicationContext()));

    mainBinding.recyclerviewMovies.setLayoutManager(gridLayoutManager);
    mainBinding.recyclerviewMovies.setHasFixedSize(true);



    mMoviesAdapter=new MoviesAdapter(movies);
    mainBinding.recyclerviewMovies.setAdapter(mMoviesAdapter);

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
      mainBinding.loadingIndicator.setVisibility(View.GONE);

      mainBinding.emptyView.setText(R.string.no_internet_connection);
    }
  }

  @Override public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {

    return new MoviesLoader(this,final_url);
  }

  @Override public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {

    mainBinding.loadingIndicator.setVisibility(View.GONE);
    // Set empty state text to display "No Movies found."
    mainBinding.emptyView.setText(R.string.no_movies);

    if(data!=null&&!data.isEmpty()){
      Log.d("O_MY", String.valueOf(data.size()));
      mMoviesAdapter.setmMoviesData(data);
      mainBinding.emptyView.setVisibility(View.GONE);
    }
  }

  @Override public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
    //Loader reset,so we can clear out our existing data
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    //inflate the menu options from the res/menu/sort.xml
    //This adds menu items to the app bar.
    getMenuInflater().inflate(R.menu.sort,menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {

    //User clicked on a menu option in the app bar overflow menu
    switch (item.getItemId()){
      //Respond to a click on the "Popular Movies" menu option
      case R.id.sortBy_popularMovies:
        final_url=PopularmoviesDB_REQUEST_URL;
        getLoaderManager().restartLoader(MOVIES_LOADER_ID, null,this);
        setTitle(getString(R.string.app_name));
        return true;
      case R.id.sortBy_top_rated_Movies:
        final_url=top_ratedMoviesDB_REQUEST_URL;
        getLoaderManager().restartLoader(MOVIES_LOADER_ID, null,this);
        setTitle(getString(R.string.sortBy_top_rated_Movies));
        return true;
      case R.id.sortBy_favourite_Movies:
        Intent favIntent=new Intent(MainActivity.this,FavouriteMoviesActivity.class);
        startActivity(favIntent);
    }
    return super.onOptionsItemSelected(item);
  }
}
