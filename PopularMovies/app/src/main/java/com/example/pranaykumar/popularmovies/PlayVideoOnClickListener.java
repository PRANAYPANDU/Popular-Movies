package com.example.pranaykumar.popularmovies;

import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

/**
 * Created by PRANAYKUMAR on 09-06-2017.
 */

public class PlayVideoOnClickListener implements OnClickListener {
  Activity activity;

  public PlayVideoOnClickListener(MovieDetailsActivity movieDetailsActivity) {
    activity=movieDetailsActivity;
  }

  @Override
  public void onClick(View v) {
    String API_KEY="AIzaSyCApU76omffWISxSNxOf_lv_hMliaLUdyQ";
    String url = v.getContext().getString(R.string.Youtube_link)+v.getTag();
    //Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
    //v.getContext().startActivity(i);
    Intent intent = YouTubeStandalonePlayer.createVideoIntent(activity,API_KEY,v.getTag().toString(),0, true,true);
    v.getContext().startActivity(intent);
  }
}