package es.marcmauri.privaliamovies.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.marcmauri.privaliamovies.R;
import es.marcmauri.privaliamovies.adapters.MovieCardAdapter;
import es.marcmauri.privaliamovies.models.FoundMovies;
import es.marcmauri.privaliamovies.models.Movie;
import es.marcmauri.privaliamovies.services.API;
import es.marcmauri.privaliamovies.services.APIServices.MoviesService;
import es.marcmauri.privaliamovies.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment implements Callback<FoundMovies> {

    private int MOVIE_LIST_FLOW;
    private String MOVIE_LIST_QUERY;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private int totalPages;
    private int nextPage = 1;
    private boolean loading = false;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private LinearLayout linearLayout_progressbar;

    private List<Movie> movies;
    private Call<FoundMovies> foundMoviesCall;


    public MovieListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movies = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        /* BIND UI */
        linearLayout_progressbar = view.findViewById(R.id.linearLayout_progressbar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_movies);

        /* Get data from Bundle */
        MOVIE_LIST_FLOW = getArguments().getInt(Util.FRAGMENT_BUNDLE_PROPERTY_FLOW, 0);
        if (MOVIE_LIST_FLOW == Util.MOVIE_LIST_FLOW_SEARCH) {
            MOVIE_LIST_QUERY = getArguments().getString(Util.FRAGMENT_BUNDLE_PROPERTY_QUERY, "");
        }

        /* Configure recyclerview */
        setRecyclerView();

        /* Get movies if possible */
        if (MOVIE_LIST_FLOW == Util.MOVIE_LIST_FLOW_SEARCH
                && TextUtils.isEmpty(MOVIE_LIST_QUERY))
            clearMovieList();
        else
            getMovies(nextPage);

        return view;
    }

    private void clearMovieList() {
        this.movies.clear();
        mAdapter.notifyDataSetChanged();
        linearLayout_progressbar.setVisibility(View.GONE);
    }

    private void setRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(this.mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Here get the child count, item count and visible items from layout manager
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisibleItems = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();

                // Just when we go down
                if (dy > 0) {
                    // Check if not loading and we are on bottom
                    if (!loading && (totalItemCount - visibleItemCount) <= (pastVisibleItems)) {
                        loading = true;
                        ++nextPage;

                        if (nextPage <= totalPages) {
                            linearLayout_progressbar.setVisibility(View.VISIBLE);
                            getMovies(nextPage);
                        } else {
                            Toast.makeText(getContext(), "No more movies to show", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        mAdapter = new MovieCardAdapter(this.movies, R.layout.card_view_movie, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getMovies(int page) {
        linearLayout_progressbar.setVisibility(View.VISIBLE);

        MoviesService service = API.getTmdbApi().create(MoviesService.class);

        switch (MOVIE_LIST_FLOW) {
            case Util.MOVIE_LIST_FLOW_SEARCH:
                foundMoviesCall = service.getMoviesByQuery(
                        MOVIE_LIST_QUERY,
                        page,
                        Util.getDeviceLanguage(getContext()),
                        API.TMDB_KEY);
                break;
            default:
                foundMoviesCall = service.getMoviesByType(
                        Util.TAG_MOVIE_TYPE_POPULAR,
                        page,
                        Util.getDeviceLanguage(getContext()),
                        API.TMDB_KEY);
        }

        foundMoviesCall.enqueue(this);
    }


    /* EVENTS */

    @Override
    public void onResponse(Call<FoundMovies> call, Response<FoundMovies> response) {
        if (response.isSuccessful()) {
            linearLayout_progressbar.setVisibility(View.GONE);
            loading = false;

            List<Movie> _movies;
            if ((_movies = response.body().getMovies()) != null && !_movies.isEmpty()) {

                if (nextPage == 1) totalPages = response.body().getFoundPages();
                this.movies.addAll(_movies);
                mAdapter.notifyDataSetChanged();

            } else {
                //Toast.makeText(getContext(), "An error has occurred within successful response", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Se produjo un error con los parametros de llamada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<FoundMovies> call, Throwable t) {
        if (!foundMoviesCall.isCanceled()) {
            linearLayout_progressbar.setVisibility(View.GONE);
            //Toast.makeText(getContext(), getString(R.string.error_occurred), Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), "An error has been occurred", Toast.LENGTH_SHORT).show();
        }
    }
}
