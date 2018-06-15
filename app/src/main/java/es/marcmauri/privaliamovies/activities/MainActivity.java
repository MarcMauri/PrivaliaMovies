package es.marcmauri.privaliamovies.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import es.marcmauri.privaliamovies.fragments.MovieListFragment;
import es.marcmauri.privaliamovies.R;

public class MainActivity extends AppCompatActivity {


    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindUI();
        setFragmentByDefault();
    }

    private void setFragmentByDefault() {
        changeFragmentToPopularMovies();
    }

    private void changeFragmentToPopularMovies() {
        changeFragment(new MovieListFragment());
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    private void bindUI() {
        this.toolbar_title = findViewById(R.id.toolbar_title);
    }
}
