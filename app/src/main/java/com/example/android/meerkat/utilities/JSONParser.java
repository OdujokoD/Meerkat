package com.example.android.meerkat.utilities;

import com.example.android.meerkat.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JSONParser {

    private JSONArray movieJSONArray;


    public JSONParser(String movieListJSON){
        try {
            JSONObject movieJSONObject = new JSONObject(movieListJSON);
            movieJSONArray = movieJSONObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> parseJSON(){
        List<Movie> movieLists = new LinkedList<>();

        for (int counter = 0; counter < movieJSONArray.length(); counter++){

            Movie movie = new Movie();

            try {
                JSONObject movieObject = movieJSONArray.getJSONObject(counter);
                String imageUrl = NetworkUtils.buildImageURL(movieObject.getString("poster_path"));

                movie.setMoviePosterURL(imageUrl);
                movie.setOriginalTitle(movieObject.getString("original_title"));
                movie.setOverview(movieObject.getString("overview"));
                movie.setReleaseDate(movieObject.getString("release_date"));
                movie.setVoteAverage(movieObject.getString("vote_average"));

                movieLists.add(movie);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        
        return movieLists;
    }
}
