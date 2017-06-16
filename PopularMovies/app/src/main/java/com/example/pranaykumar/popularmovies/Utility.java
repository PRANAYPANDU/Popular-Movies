package com.example.pranaykumar.popularmovies;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by PRANAYKUMAR on 15-06-2017.
 */
public class Utility {
  public static int calculateNoOfColumns(Context context) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
    int noOfColumns = (int) (dpWidth / 180);
    return noOfColumns;
  }
}