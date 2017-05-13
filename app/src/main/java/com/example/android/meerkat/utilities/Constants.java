package com.example.android.meerkat.utilities;

public class Constants {
    public final static String MOVIE_TITLE_EXTRA_TEXT = "movie title";
    public final static String MOVIE_IMAGE_EXTRA_TEXT = "movie poster url";
    public final static String MOVIE_RELEASE_DATE_EXTRA_TEXT = "movie release date";
    public final static String MOVIE_RATING_EXTRA_TEXT = "movie rating";
    public final static String MOVIE_OVERVIEW_EXTRA_TEXT = "movie overview";

    /*=================  Main activity Constants ====================== */
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREFS_PARAM = "currentCategory";
    public static final int MOVIE_DB_LOADER_ID = 77;
    public static final String FETCH_MOVIE_URL_EXTRA = "fetch";

    /*=================  Movie Details activity Constants ====================== */
    public static final int TRAILER_LOADER_ID = 88;
    public static final String FETCH_TRAILER_URL_EXTRA = "trailer_fetch";
    public static final int REVIEW_LOADER_ID = 99;
    public static final String FETCH_REVIEW_URL_EXTRA = "review_fetch";

    /*=================  Network Utilities Constants ====================== */
    final static String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public final static String POPULAR_MOVIE_PATH = "popular";
    public final static String TOP_RATED_MOVIE_PATH = "top_rated";
    final static String IMAGE_DEFAULT_SIZE = "w185";
    final static String MOVIE_REVIEW_PATH = "reviews";
    final static String MOVIE_TRAILER_PATH = "videos";
    final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    final static String TRAILER_BASE_URL = "https://www.youtube.com/watch";
    public final static String TRAILER_KEY = "v";

    // TODO: Add your API KEY
    final static String API_KEY = "9f92dc0a0f71e8cbe0726622a1f41305";
    final static String PARAM_API_KEY = "api_key";
}
