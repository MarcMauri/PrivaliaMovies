package es.marcmauri.privaliamovies.services;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private static final String BASE_URL_TMDB = "https://api.themoviedb.org/3/";
    public static final String TMDB_KEY = "93aea0c77bc168d8bbce3918cefefa45";

    private static Retrofit retrofit_tmdb = null;


    private final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(8, TimeUnit.SECONDS)
            .connectTimeout(8, TimeUnit.SECONDS)
            .build();

    public static Retrofit getTmdbApi() {
        // We create the Retrofit instance. It allows us to consume JSON API Calls
        if (retrofit_tmdb == null) {
            retrofit_tmdb = new Retrofit.Builder()
                    .baseUrl(BASE_URL_TMDB)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit_tmdb;
    }
}
