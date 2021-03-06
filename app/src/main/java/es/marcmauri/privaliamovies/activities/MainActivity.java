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

        // Set back arrow behavior (into subtitle layout)
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
        this.CURRENT_FLOW = Util.APP_FLOW_POPULAR;
        setSubtitle(false, getString(R.string.activity_main_subtitle_popular_movies));

        Bundle bundle = new Bundle();
        bundle.putInt(Util.FRAGMENT_BUNDLE_PROPERTY_FLOW, Util.APP_FLOW_POPULAR);

        MovieListFragment fragment = new MovieListFragment();
        fragment.setArguments(bundle);
        changeFragment(fragment);
    }

    private void changeFragmentToSearchedMovies(final String query) {
        this.CURRENT_FLOW = Util.APP_FLOW_SEARCH;
        setSubtitle(true, getString(R.string.activity_main_subtitle_search_tag) + " " + query);

        Bundle bundle = new Bundle();
        bundle.putInt(Util.FRAGMENT_BUNDLE_PROPERTY_FLOW, Util.APP_FLOW_SEARCH);
        bundle.putString(Util.FRAGMENT_BUNDLE_PROPERTY_QUERY, query);

        MovieListFragment fragment = new MovieListFragment();
        fragment.setArguments(bundle);
        changeFragment(fragment);
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
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


    /* EVENTS */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.activity_main_searchview_hint));

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
                // if query available then ask for the search results in the related fragment
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
                if (CURRENT_FLOW == Util.APP_FLOW_SEARCH)
                    changeFragmentToPopularMovies();
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        /* When we press back button:
            case 1 > We are on SEARCH FLOW => We return to POPULAR FLOW ~ The search ends.
            case 2 > We are on another flow: POPULAR FLOW => We make a double validation before exit.
        */
        switch (this.CURRENT_FLOW) {
            case Util.APP_FLOW_SEARCH:
                closeSearchBoxAndGoToPopularMovies();
                break;
            default:
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                } else {
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, R.string.activity_main_before_exit_notice, Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2500);
                }
        }
    }
}
