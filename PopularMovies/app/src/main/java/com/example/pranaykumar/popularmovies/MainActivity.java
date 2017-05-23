package com.example.pranaykumar.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{
  private static final int MOVIES_LOADER_ID=1;
  private static final String moviesDB_REQUEST_URL="http://api.themoviedb.org/3/movie/popular?api_key=857710a9c17b11d80aa32f98d00aa936";
  private static final String LOG_TAG=MainActivity.class.getSimpleName();
  private MovieAdapter movieAdapter;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Create an ArrayList of Movie Objects with Movie poster and title
    final ArrayList<Movie> movies=new ArrayList<Movie>();
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
    movieAdapter=new MovieAdapter(this,movies);


    GridView gridView=(GridView)findViewById(R.id.grid_view);
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
  }

  @Override public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {

    return new MoviesLoader(this,moviesDB_REQUEST_URL);
  }

  @Override public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
    movieAdapter.clear();

    if(data!=null&&!data.isEmpty()){
      movieAdapter.addAll(data);
    }
  }

  @Override public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
    //Loader reset,so we can clear out our existing data.
    movieAdapter.clear();
  }
}
