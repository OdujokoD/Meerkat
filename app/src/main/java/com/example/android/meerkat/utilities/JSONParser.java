package com.example.android.meerkat.utilities;

import com.example.android.meerkat.model.Movie;
import com.example.android.meerkat.model.Review;
import com.example.android.meerkat.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import static com.example.android.meerkat.utilities.Constants.IMAGE_DEFAULT_SIZE;

public class JSONParser {

    private JSONArray movieJSONArray;
    private JSONArray trailerJSONArray;
    private JSONArray reviewJSONArray;

    public JSONParser(){

    }


    public JSONParser(String movieListJSON){
        try {
            JSONObject movieJSONObject = new JSONObject(movieListJSON);
            movieJSONArray = movieJSONObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> parseMovieJSON(){
        List<Movie> movieLists = new LinkedList<>();

        for (int counter = 0; counter < movieJSONArray.length(); counter++){

            Movie movie = new Movie();

            try {
                JSONObject movieObject = movieJSONArray.getJSONObject(counter);
                String imageUrl = NetworkUtils.buildImageURL(
                        movieObject.getString("poster_path"), IMAGE_DEFAULT_SIZE);

                movie.setMoviePosterURL(imageUrl);
                movie.setMovieId(movieObject.getString("id"));
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

    public List<Trailer> parseTrailerJSON(String trailerListJSON){
        try {
            JSONObject trailerJSONObject = new JSONObject(trailerListJSON);
            trailerJSONArray = trailerJSONObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<Trailer> trailerLists = new LinkedList<>();

        for (int counter = 0; counter < trailerJSONArray.length(); counter++){

            Trailer trailer = new Trailer();

            try {
                JSONObject trailerObject = trailerJSONArray.getJSONObject(counter);

                trailer.setKey(trailerObject.getString("key"));
                trailer.setName(trailerObject.getString("name"));

                trailerLists.add(trailer);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return trailerLists;
    }

    public List<Review> parseReviewJSON(String reviewListJSON){
        try {
            JSONObject reviewJSONObject = new JSONObject(reviewListJSON);
            reviewJSONArray = reviewJSONObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<Review> reviewLists = new LinkedList<>();

        for (int counter = 0; counter < reviewJSONArray.length(); counter++){

            Review review = new Review();

            try {
                JSONObject reviewObject = reviewJSONArray.getJSONObject(counter);

                review.setId(reviewObject.getString("id"));
                review.setAuthor(reviewObject.getString("author"));
                review.setContent(reviewObject.getString("content"));

                reviewLists.add(review);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return reviewLists;
    }
}
