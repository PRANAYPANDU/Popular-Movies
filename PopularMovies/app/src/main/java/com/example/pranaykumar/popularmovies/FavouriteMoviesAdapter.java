package com.example.pranaykumar.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.pranaykumar.popularmovies.data.PopularMoviesContract.FavouriteMoviesEntry;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by PRANAYKUMAR on 12-06-2017.
 */

public class FavouriteMoviesAdapter extends RecyclerView.Adapter<FavouriteMoviesAdapter.FavouriteMoviesAdapterViewHolder> {
  private final Context mContext;
  private Cursor mCursor;



  public FavouriteMoviesAdapter(Context context){
    mContext=context;
  }

  @Override
  public FavouriteMoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view=LayoutInflater
        .from(mContext)
        .inflate(R.layout.fav_grid_item,parent,false);
    view.setFocusable(true);
    return new FavouriteMoviesAdapterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(FavouriteMoviesAdapterViewHolder holder, int position) {
    mCursor.moveToPosition(position);
    final Movie currentMovie=new Movie(mCursor.getString(1),mCursor.getString(0),mCursor.getString(4),mCursor.getDouble(3),mCursor.getString(2),mCursor.getString(5),1);
    String posterId=mCursor.getString(0);
    String basePosterUrl = "http://image.tmdb.org/t/p//w185/";
    String finalPosterUrl;
    //Final URL to be passed into Picasso
    finalPosterUrl = basePosterUrl + posterId;
    Picasso.with(mContext).load(finalPosterUrl).into(holder.mPosterImageView);

    holder.layout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(mContext,MovieDetailsActivity.class);
        intent.putExtra("movie",(android.os.Parcelable)currentMovie);
        mContext.startActivity(intent);
      }
    });

  }

  @Override
  public int getItemCount() {
    if(null==mCursor)
      return 0;
    return mCursor.getCount();
  }
  void swapCursor(Cursor newCursor){
    mCursor=newCursor;
    notifyDataSetChanged();
  }


  class FavouriteMoviesAdapterViewHolder extends RecyclerView.ViewHolder{
    final ImageView mPosterImageView;
    private LinearLayout layout;
    public FavouriteMoviesAdapterViewHolder(View itemView) {
      super(itemView);
      mPosterImageView=(ImageView) itemView.findViewById(R.id.posterImg);
      layout=(LinearLayout)itemView.findViewById(R.id.Flayout);
    }
  }
}
