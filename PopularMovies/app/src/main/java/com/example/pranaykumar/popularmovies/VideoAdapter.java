package com.example.pranaykumar.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PRANAYKUMAR on 09-07-2017.
 */

public class VideoAdapter extends ArrayAdapter<Video> implements View.OnClickListener{

  Context mContext;


  public VideoAdapter(@NonNull Context context,
      @NonNull ArrayList<Video> objects) {
    super(context,0,objects);
    mContext=context;
  }
  @Override
  public void onClick(View v) {
    String API_KEY="AIzaSyCApU76omffWISxSNxOf_lv_hMliaLUdyQ";

    //Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
    //v.getContext().startActivity(i);
    int position= (int) v.getTag();

    Video currentVideo=getItem(position);
    String url = v.getContext().getString(R.string.Youtube_link)+currentVideo.getmVideoId();
    Intent intent = YouTubeStandalonePlayer
        .createVideoIntent((Activity) mContext,API_KEY,currentVideo.getmVideoId().toString(),0, true,true);
    v.getContext().startActivity(intent);

  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

    View result=convertView;
    if(result==null){
      LayoutInflater inflater = LayoutInflater.from(getContext());
      result = inflater.inflate(R.layout.video_item, parent, false);
      }
    Video video=getItem(position);
    TextView textView=(TextView)result.findViewById(R.id.VideoName);
    ImageButton imgButton=(ImageButton)result.findViewById(R.id.videoThumbNail);

   textView.setText(video.getmVideoName());
    String imgUrl = "http://img.youtube.com/vi/"+video.getmVideoId()+ "/0.jpg";
    Picasso.with(mContext).load(imgUrl).into(imgButton);
   textView.setOnClickListener(this);
   imgButton.setOnClickListener(this);
    textView.setTag(position);
    imgButton.setTag(position);
    return result;
  }
}
