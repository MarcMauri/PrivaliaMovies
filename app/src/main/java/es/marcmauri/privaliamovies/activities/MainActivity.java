package es.marcmauri.privaliamovies.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import es.marcmauri.privaliamovies.R;
import es.marcmauri.privaliamovies.fragments.MovieListFragment;
import es.marcmauri.privaliamovies.utils.Util;

public class MainActivity extends AppCompatActivity {

    private int CURRENT_FLOW;
    private boolean doubleBackToExitPressedOnce = false;

    private TextView toolbar_title;
    private SearchView searchView;

    private TextView actionBar_subtitle;
    private ImageView imageView_arrowBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindUI();
        setToolbar();
        setFragmentByDefault();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Set subtitle back arrow behavior
        this.imageView_arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSearchBoxAndGoToPopularMovies();
            }
        });
    }

    private void setSubtitle(final boolean isArrowBack, final String name) {
        this.imageView_arrowBack.setVisibility(isArrowBack ? View.VISIBLE : View.GONE);
        this.actionBar_subtitle.setText(name);
    }

    private void setFragmentByDefault() {
        changeFragmentToPopularMovies();
    }

    private void changeFragmentToPopularMovies() {
        this.CURRENT_FLOW = Util.MOVIE_LIST_FLOW_POPULAR;
        setSubtitle(false, "Popular movies");

        MovieListFragment fragment = new MovieListFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(Util.FRAGMENT_BUNDLE_PROPERTY_FLOW, Util.MOVIE_LIST_FLOW_POPULAR);
        fragment.setArguments(bundle);

        changeFragment(fragment);
    }

    private void changeFragmentToSearchedMovies(final String query) {
        this.CURRENT_FLOW = Util.MOVIE_LIST_FLOW_SEARCH;
        setSubtitle(true, "Search: " + query);

        MovieListFragment fragment = new MovieListFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(Util.FRAGMENT_BUNDLE_PROPERTY_FLOW, Util.MOVIE_LIST_FLOW_SEARCH);
        bundle.putString(Util.FRAGMENT_BUNDLE_PROPERTY_QUERY, query);
        fragment.setArguments(bundle);

        changeFragment(fragment);
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search movies...");
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide app name when search button is pressed
                toolbar_title.setVisibility(View.GONE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Do nothing, just hide the keyboard
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // if query available get the those movies
                if (!TextUtils.isEmpty(newText))
                    changeFragmentToSearchedMovies(newText);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // Show app name again
                toolbar_title.setVisibility(View.VISIBLE);
                // And show popular movies again if we are on SEARCH FLOW
                if (CURRENT_FLOW == Util.MOVIE_LIST_FLOW_SEARCH)
                    changeFragmentToPopularMovies();
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {

        switch (this.CURRENT_FLOW) {
            case Util.MOVIE_LIST_FLOW_SEARCH:
                closeSearchBoxAndGoToPopularMovies();
                break;
            default:
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                } else {
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, "Back again to exit", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2500);
                }
        }
    }

    private void closeSearchBoxAndGoToPopularMovies() {
        closeSearchBox();
        changeFragmentToPopularMovies();
    }

    void closeSearchBox() {
        if (!searchView.isIconified()) {
            searchView.setQuery("", false);
            searchView.setIconified(true);
            searchView.clearFocus();
        }
    }

    private void bindUI() {
        this.toolbar_title = findViewById(R.id.toolbar_title);
        this.actionBar_subtitle = findViewById(R.id.actionBar_subtitle);
        this.imageView_arrowBack = findViewById(R.id.imageView_arrowBack);
    }
}
