package es.marcmauri.privaliamovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoundMovies {

    @SerializedName("page")
    private int currentPage;
    @SerializedName("total_results")
    private int foundMovies;
    @SerializedName("total_pages")
    private int foundPages;
    @SerializedName("results")
    private List<Movie> movies;


    public FoundMovies() {
    }

    public FoundMovies(int currentPage, int foundMovies, int foundPages, List<Movie> movies) {
        this.currentPage = currentPage;
        this.foundMovies = foundMovies;
        this.foundPages = foundPages;
        this.movies = movies;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getFoundMovies() {
        return foundMovies;
    }

    public void setFoundMovies(int foundMovies) {
        this.foundMovies = foundMovies;
    }

    public int getFoundPages() {
        return foundPages;
    }

    public void setFoundPages(int foundPages) {
        this.foundPages = foundPages;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
