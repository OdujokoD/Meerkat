package com.example.android.meerkat.utilities;

public class Constants {

    /*=================  Main activity Constants ====================== */
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREFS_PARAM = "currentCategory";
    public static final int MOVIE_DB_LOADER_ID = 77;
    public static final String FETCH_MOVIE_URL_EXTRA = "fetch";
    public static final String RECYCLER_VIEW_STATE = "state";

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
    final static String TRAILER_KEY = "v";
    final static String PARAM_API_KEY = "api_key";


    /*=================  Favorite Movie Content Provider Constants ====================== */
    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;
    public static final int MOVIE_LOADER_ID = 11;

    /*=================  Reviews Content Provider Constants ====================== */
    public static final int REVIEW = 200;
    public static final int REVIEW_WITH_ID = 201;

    /*=================  Trailers Content Provider Constants ====================== */
    public static final int TRAILER = 300;
    public static final int TRAILER_WITH_ID = 301;

    /*=================  Favorites movies details Constants ====================== */
    public static final String POSTER_URL = "poster_url";
    public static final String MOVIE_NAME = "movie_name";
    public static final String MOVIE_ID = "movie_id";
    public static final String OVERVIEW = "overview";
    public static final String RATING = "rating";
    public static final String RELEASE_DATE = "release_date";
}
