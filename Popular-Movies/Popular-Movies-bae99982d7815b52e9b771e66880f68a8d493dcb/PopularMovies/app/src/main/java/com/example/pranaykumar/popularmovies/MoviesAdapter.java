package com.example.pranaykumar.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by PRANAYKUMAR on 11-06-2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder>{
  Context context;
  private ArrayList<Movie> mMoviesData;


  public MoviesAdapter(ArrayList<Movie> moviesdata){
    mMoviesData=moviesdata;
  }
  @Override
  public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
   context=parent.getContext();
    int layoutIdForGridItem=R.layout.grid_item;
    LayoutInflater inflater=LayoutInflater.from(context);

    View view=inflater.inflate(layoutIdForGridItem,parent,false);
    return new MoviesAdapterViewHolder(view);
  }

  public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder{
    public final ImageView mPoster;
    public final LinearLayout mLayout;

  public MoviesAdapterViewHolder(View itemView) {
    super(itemView);

    mPoster=(ImageView)itemView.findViewById(R.id.poster);
    mLayout=(LinearLayout)itemView.findViewById(R.id.layout);
  }

  }
  @Override
  public void onBindViewHolder(MoviesAdapterViewHolder holder, final int position) {
    Movie currentMovie=mMoviesData.get(position);

    String basePosterUrl="http://image.tmdb.org/t/p//w185/";
    String finalPosterUrl=basePosterUrl+currentMovie.getmImageResourceID();
    Picasso.with(context).load(finalPosterUrl).into(holder.mPoster);
    holder.mLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(context,MovieDetails.class);
        intent.putExtra("movie", (android.os.Parcelable) mMoviesData.get(position));

        context.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    if(null==mMoviesData)return 0;
    return mMoviesData.size();
  }
  public void setmMoviesData(ArrayList<Movie> moviesData){
    mMoviesData=moviesData;
    notifyDataSetChanged();
  }
}
