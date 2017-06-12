package com.example.pranaykumar.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Created by PRANAYKUMAR on 09-06-2017.
 */

public class PlayVideoOnClickListener implements OnClickListener {
  @Override
  public void onClick(View v) {
    String url = "http://www.youtube.com/watch?v="+v.getTag();
    Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
    v.getContext().startActivity(i);
  }
}