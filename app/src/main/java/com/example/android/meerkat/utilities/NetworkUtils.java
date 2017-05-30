package com.example.android.meerkat.utilities;

import android.net.Uri;

import com.example.android.meerkat.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static com.example.android.meerkat.utilities.Constants.IMAGE_BASE_URL;
import static com.example.android.meerkat.utilities.Constants.MOVIE_BASE_URL;
import static com.example.android.meerkat.utilities.Constants.MOVIE_REVIEW_PATH;
import static com.example.android.meerkat.utilities.Constants.MOVIE_TRAILER_PATH;
import static com.example.android.meerkat.utilities.Constants.PARAM_API_KEY;
import static com.example.android.meerkat.utilities.Constants.TRAILER_BASE_URL;
import static com.example.android.meerkat.utilities.Constants.TRAILER_KEY;

public class NetworkUtils {
    // Fetch popular movies
    public static URL buildMovieURL(String category){
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(category)
                .appendQueryParameter(PARAM_API_KEY, BuildConfig.MOVIE_DB_API_KEY)
                .build();

        return buildURL(builtUri);
    }

    static String buildImageURL(String imagePath, String size){
        Uri builtUri = Uri.parse(IMAGE_BASE_URL + size + imagePath).buildUpon()
                .build();

        return buildURL(builtUri).toString();
    }

    public static URL buildReviewURL(String movieId){
        Uri reviewUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(MOVIE_REVIEW_PATH)
                .appendQueryParameter(PARAM_API_KEY, BuildConfig.MOVIE_DB_API_KEY)
                .build();

        return buildURL(reviewUri);
    }

    public static URL buildVideosURL(String movieId){
        Uri videosUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(MOVIE_TRAILER_PATH)
                .appendQueryParameter(PARAM_API_KEY, BuildConfig.MOVIE_DB_API_KEY)
                .build();

        return buildURL(videosUri);
    }

    public static String buildTrailerURL(String key){
        Uri trailerUri = Uri.parse(TRAILER_BASE_URL).buildUpon()
                .appendQueryParameter(TRAILER_KEY, key)
                .build();

        return buildURL(trailerUri).toString();
    }

    private static URL buildURL(Uri uri){
        URL url = null;
        try {
            url = new URL(uri.toString());
        }catch (MalformedURLException m){
            m.printStackTrace();
        }

        if (url == null) return null;

        return url;
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
