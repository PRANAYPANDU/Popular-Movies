package com.example.pranaykumar.popularmovies;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by PRANAYKUMAR on 21-05-2017.
 */

public class MovieAdapter extends ArrayAdapter<Movie>{
  public MovieAdapter(Activity context,ArrayList<Movie> movies){
    super(context,0,movies);
  }

  @NonNull @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    //Check if existing view is being reused,otherwise inflate  the view
    View gridItemView=convertView;
    if(gridItemView==null){
      gridItemView= LayoutInflater.from(getContext()).inflate(
          R.layout.grid_item,parent,false);
    }
    Movie currentMovie=getItem(position);

    ImageView posterImageView=(ImageView)gridItemView.findViewById(R.id.poster);
    //posterImageView.setImageResource(currentMovie.getmImageResourceID());
      posterImageView.setImageResource(R.drawable.img1);
    //TextView titleTextView=(TextView)gridItemView.findViewById(R.id.title);
    //titleTextView.setText(currentMovie.getmMovieTitle());

    return gridItemView;
  }
}
