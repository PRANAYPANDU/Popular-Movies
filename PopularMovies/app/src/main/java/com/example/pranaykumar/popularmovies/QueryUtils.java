package com.example.pranaykumar.popularmovies;

import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PRANAYKUMAR on 23-05-2017.
 */

public class QueryUtils {

  //Tag for the log messages
  private static final String LOG_TAG = QueryUtils.class.getSimpleName();

  private QueryUtils() {
  }
  public static ArrayList<String> fetchVideos(String videoUrl){
    URL vUrl=createUrl(videoUrl);

    String jsonResp=null;
    jsonResp=makeHttpRequest(vUrl);
    ArrayList<String> videos=extractVideoFromJson(jsonResp);
    return videos;
  }
  public static ArrayList<String> fetchReviews(String reviewsUrl){
    URL vUrl=createUrl(reviewsUrl);

    String jsonResp=null;
    jsonResp=makeHttpRequest(vUrl);
    ArrayList<String> reviews=extractReviewFromJson(jsonResp);
    return reviews;
  }

  private static ArrayList<String> extractReviewFromJson(String jsonResp) {
    if(TextUtils.isEmpty(jsonResp)){
      return null;
    }
    ArrayList<String> reviews=new ArrayList<>();
    try{
      JSONObject baseJson=new JSONObject(jsonResp);
      JSONArray results=baseJson.getJSONArray("results");
      for(int i=0;i<results.length();i++){
        JSONObject currentReview=results.getJSONObject(i);
        String author=currentReview.getString("author");

        reviews.add("<b>A review by :"+author+"</b>"+"<br>"+currentReview.getString("content"));

      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return reviews;
  }


  public static ArrayList<Movie> fetchMovies(String requestUrl) {
    URL url = createUrl(requestUrl);
    //Perform HTTP request to the URL and receive a JSON response back
    String jsonResponse = null;
    jsonResponse = makeHttpRequest(url);
    //Extract relevant fields from the JSON response and create a list of {@link Movie}s
    ArrayList<Movie> movies = extractFeatureFromJson(jsonResponse);
    //Return the list of {@link Movie}
    return movies;
  }

  private static ArrayList<String> extractVideoFromJson(String jsonResp) {
    if(TextUtils.isEmpty(jsonResp)){
      return null;
    }
    ArrayList<String> videos=new ArrayList<>();
    try{
      JSONObject baseJson=new JSONObject(jsonResp);
      JSONArray results=baseJson.getJSONArray("results");
      for(int i=0;i<results.length();i++){
        JSONObject currentTrailer=results.getJSONObject(i);
        if(currentTrailer.getString("type").equals("Trailer")){
          videos.add(currentTrailer.getString("key"));
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return videos;
  }
  private static ArrayList<Movie> extractFeatureFromJson(String jsonResponse) {
    //If the JSON string is empty or null,then return early.
    if (TextUtils.isEmpty(jsonResponse)) {
      return null;
    }
    //Create an empty ArrayList that we can start adding movies
    ArrayList<Movie> movies = new ArrayList<>();
    try {
      JSONObject baseJson = new JSONObject(jsonResponse);
      JSONArray results = baseJson.getJSONArray("results");

      for (int i = 0; i < results.length(); i++) {
        JSONObject currentMovie = results.getJSONObject(i);
        String poster_path = currentMovie.getString("poster_path");
        String overView = currentMovie.getString("overview");
        String title = currentMovie.getString("original_title");
        Double rating = currentMovie.getDouble("vote_average");
        String Rdate = currentMovie.getString("release_date");
        String id=currentMovie.getString("id");

        Movie movie = new Movie(title, poster_path, overView, rating, Rdate,id);
        movies.add(movie);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return movies;
  }

  private static String makeHttpRequest(URL url) {
    String jsonResponse = "";

    //If the URL is null,then return early.
    if (url == null) {
      return jsonResponse;
    }
    HttpURLConnection urlConnection = null;
    InputStream inputStream = null;
    try {
      urlConnection = (HttpURLConnection) url.openConnection();
      Log.i("MESSAGE", "Connection opened");
      urlConnection.setReadTimeout(10000/*milliseconds*/);
      urlConnection.setConnectTimeout(15000);
      urlConnection.setRequestMethod("GET");
      urlConnection.connect();
      Log.i(LOG_TAG, "Connected");

      //If the request was succesful (response code 200),
      //then read the input stream and parse the response.
      if (urlConnection.getResponseCode() == 200) {
        inputStream = urlConnection.getInputStream();
        jsonResponse = readFromStream(inputStream);
        Log.i(LOG_TAG, "got response code" + urlConnection.getResponseCode());
      } else {
        Log.e(LOG_TAG, "got response code :" + urlConnection.getResponseCode());
      }
    } catch (IOException e) {
      Log.e(LOG_TAG, "Problem retrieving informtaion");
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return jsonResponse;
  }

  private static String readFromStream(InputStream inputStream) {
    StringBuilder output = new StringBuilder();
    if (inputStream != null) {
      InputStreamReader inputStreamReader =
          new InputStreamReader(inputStream, Charset.forName("UTF-8"));
      BufferedReader reader = new BufferedReader(inputStreamReader);
      String line = null;
      try {
        line = reader.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      while (line != null) {
        output.append(line);
        try {
          line = reader.readLine();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return output.toString();
  }

  private static URL createUrl(String requestUrl) {
    URL url = null;
    try {
      url = new URL(requestUrl);
    } catch (MalformedURLException e) {
      Log.e("ERROR", "Problem building the URL");
    }
    return url;
  }
}
