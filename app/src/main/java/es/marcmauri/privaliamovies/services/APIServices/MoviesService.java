package es.marcmauri.privaliamovies.services.APIServices;

import es.marcmauri.privaliamovies.models.FoundMovies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesService {

    @GET("movie/{type}")
    Call<FoundMovies> getMoviesByType(@Path("type") String type, @Query("page") int page, @Query("language") String lang, @Query("api_key") String key);

    @GET("search/movie")
    Call<FoundMovies> getMoviesByQuery(@Query("query") String query, @Query("page") int page, @Query("language") String lang, @Query("api_key") String key);
}
