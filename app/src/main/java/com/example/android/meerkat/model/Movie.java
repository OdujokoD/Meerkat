package com.example.android.meerkat.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
    private String movieId;
    private String moviePosterURL;
    private String OriginalTitle;
    private String overview;
    private String releaseDate;
    private String voteAverage;

    public Movie(){

    }

    protected Movie(Parcel in) {
        movieId = in.readString();
        moviePosterURL = in.readString();
        OriginalTitle = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMoviePosterURL() {
        return moviePosterURL;
    }

    public void setMoviePosterURL(String moviePosterURL) {
        this.moviePosterURL = moviePosterURL;
    }

    public String getOriginalTitle() {
        return OriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        OriginalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieId);
        parcel.writeString(moviePosterURL);
        parcel.writeString(OriginalTitle);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(voteAverage);
    }
}
