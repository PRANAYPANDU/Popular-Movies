package com.example.pranaykumar.popularmovies;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import java.util.ArrayList;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{
  private static final int MOVIES_LOADER_ID=1;
  private static final String PopularmoviesDB_REQUEST_URL="http://api.themoviedb.org/3/movie/popular?api_key=857710a9c17b11d80aa32f98d00aa936";
  private static final String top_ratedMoviesDB_REQUEST_URL="http://api.themoviedb.org/3/movie/top_rated?api_key=857710a9c17b11d80aa32f98d00aa936";
  private String final_url=PopularmoviesDB_REQUEST_URL;
  private static final String LOG_TAG=MainActivity.class.getSimpleName();
  private MovieAdapter movieAdapter;

  /**TextView that is displayed when the lsit is empty*/
  private TextView mEmptyStateTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    final ArrayList<Movie> movies=new ArrayList<Movie>();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Create an ArrayList of Movie Objects with Movie poster and title

    /*movies.add(new Movie("Beauty and the Beast","R.drawable.img1","nicdfffffffffe Movie",4,"22-5-2016"));
    movies.add(new Movie("Fight Club","R.drawable.img2","nice Mosfffffffffffffffffffffvie",5,"22-5-2016"));
    movies.add(new Movie("Saving Private Ryan","R.drawable.img3","nice Movsaaaaaaaaie",4,"22-5-2016"));
    movies.add(new Movie("the silence of the lambs","R.drawable.img4","nicessssssssssssssssssdd Movie",4,"22-5-2016"));
    movies.add(new Movie("Moonlight","R.drawable.img5","nice Movisssddddddddddde",4,"22-5-2016"));
    movies.add(new Movie("The Grey","R.drawable.img6","nice Movidcdccccccccccccccccccccsssssssssssssssssffffffffffffe",4,"22-5-2016"));
    movies.add(new Movie("Naya","R.drawable.img7","nice Movdvddfsdfffffffffffffffffffffie",4,"22-5-2016"));
    movies.add(new Movie("Pirates of the Caribbean","R.drawable.img8","nice Movieasasssssssssss",4,"22-5-2016"));
    movies.add(new Movie("Perfume","R.drawable.img9","nice Movieadssdddddddddddasss",4,"22-5-2016"));
    */
    GridView gridView=(GridView)findViewById(R.id.grid_view);

    mEmptyStateTextView=(TextView)findViewById(R.id.empty_view);

    movieAdapter=new MovieAdapter(this,movies);
    gridView.setAdapter(movieAdapter);

    //Setup the item click listener
    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(MainActivity.this,MovieDetails.class);
        intent.putExtra("movie",movies.get(position));
        startActivity(intent);

      }}
    );

    //Get a reference to the ConnectivityManager to check state of network connectivity
    ConnectivityManager connMgr=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

    //Get details on the currently active default data network
    NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();

    //If there is a network connection,fetch data
    if(networkInfo!=null&&networkInfo.isConnected()){
      //Get a reference to the LoaderManager,in order to interact with loaders

      android.app.LoaderManager loaderManager=getLoaderManager();
      // Initialize the loader. Pass in the int ID constant defined above and pass in null for
      // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
      // because this activity implements the LoaderCallbacks interface).
      loaderManager.initLoader(MOVIES_LOADER_ID,null,this);
    }
    else{
      View loadingIndicator=findViewById(R.id.loading_indicator);
      loadingIndicator.setVisibility(View.GONE);

      mEmptyStateTextView.setText(R.string.no_internet_connection);
    }
  }
  @Override public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {

    return new MoviesLoader(this,final_url);
  }

  @Override public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
    View loadingIndicator = findViewById(R.id.loading_indicator);
    loadingIndicator.setVisibility(View.GONE);
    // Set empty state text to display "No Movies found."
    mEmptyStateTextView.setText(R.string.no_movies);
    movieAdapter.clear();

    if(data!=null&&!data.isEmpty()){
      movieAdapter.addAll(data);
      mEmptyStateTextView.setVisibility(View.GONE);
    }
  }

  @Override public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
    //Loader reset,so we can clear out our existing data.
    movieAdapter.clear();
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

    }
    return super.onOptionsItemSelected(item);
  }
}
