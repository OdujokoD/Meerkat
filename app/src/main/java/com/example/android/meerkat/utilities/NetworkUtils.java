package com.example.android.meerkat.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String POPULAR_MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/popular";
    final static String TOP_RATED_MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated";
    final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";

    // TODO: Add your API KEY
    final static String API_KEY = "";
    final static String PARAM_API_KEY = "api_key";

    // Fetch popular movies
    public static URL buildPopularMoviesURL(){
        Uri builtUri = Uri.parse(POPULAR_MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        }catch (MalformedURLException m){
            m.printStackTrace();
        }

        return url;
    }

    // Fetch top rated movies
    public static URL buildTopRatedMoviesURL(){
        Uri builtUri = Uri.parse(TOP_RATED_MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        }catch (MalformedURLException m){
            m.printStackTrace();
        }

        return url;
    }

    public static String buildImageURL(String imagePath){
        Uri builtUri = Uri.parse(IMAGE_BASE_URL+imagePath).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        }catch (MalformedURLException m){
            m.printStackTrace();
        }

        String completeImagePath = url.toString();

        return completeImagePath;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
