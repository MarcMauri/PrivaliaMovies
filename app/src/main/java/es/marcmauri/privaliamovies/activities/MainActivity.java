package es.marcmauri.privaliamovies.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import es.marcmauri.privaliamovies.fragments.MovieListFragment;
import es.marcmauri.privaliamovies.R;
import es.marcmauri.privaliamovies.utils.Util;

public class MainActivity extends AppCompatActivity {


    private TextView toolbar_title;
    private SearchView searchView;

    private boolean isQueryTextSubmitted = false;

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
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFragmentByDefault() {
        changeFragmentToPopularMovies();
    }

    private void changeFragmentToPopularMovies() {
        MovieListFragment fragment = new MovieListFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(Util.FRAGMENT_BUNDLE_PROPERTY_FLOW, Util.MOVIE_LIST_FLOW_POPULAR);
        fragment.setArguments(bundle);

        changeFragment(fragment);
    }

    private void changeFragmentToSearchedMovies(final String query) {
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
                toolbar_title.setVisibility(View.GONE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Do nothing, then keyboard just disappears
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // if query available, get movies by search
                if (!TextUtils.isEmpty(newText)) changeFragmentToSearchedMovies(newText);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // Show app name again
                toolbar_title.setVisibility(View.VISIBLE);
                // And show popular movies again
                changeFragmentToPopularMovies();
                return false;
            }
        });

        return true;
    }

    private void bindUI() {
        this.toolbar_title = findViewById(R.id.toolbar_title);
    }
}
