package es.marcmauri.privaliamovies.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import es.marcmauri.privaliamovies.R;
import es.marcmauri.privaliamovies.adapters.MovieCardAdapter;
import es.marcmauri.privaliamovies.models.Movie;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment {

    private LinearLayout linearLayout_progressbar;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Movie> movies;


    public MovieListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movies = mockData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        linearLayout_progressbar = view.findViewById(R.id.linearLayout_progressbar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_movies);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(this.mLayoutManager);

        mAdapter = new MovieCardAdapter(this.movies, R.layout.card_view_movie, getContext());
        mRecyclerView.setAdapter(mAdapter);

        linearLayout_progressbar.setVisibility(View.GONE);

        return view;
    }

    private List<Movie> mockData() {
        return new ArrayList<Movie>() {{
            add(new Movie(351286, "Jurassic World: Fallen Kingdom", "Jurassic World: Fallen Kingdom", "/c9XxwwhPHdaImA2f1WEfEsbhaFB.jpg", "/gBmrsugfWpiXRh13Vo3j0WW55qD.jpg", "A volcanic eruption threatens the remaining dinosaurs on the island of Isla Nublar, where the creatures have freely roamed for several years after the demise of an animal theme park known as Jurassic World. Claire Dearing, the former park manager, has now founded the Dinosaur Protection Group, an organization dedicated to protecting the dinosaurs. To help with her cause, Claire has recruited Owen Grady, a former dinosaur trainer who worked at the park, to prevent the extinction of the dinosaurs once again.", "2018-06-06"));
            add(new Movie(383498, "Deadpool 2", "Deadpool 2", "/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg", "/3P52oz9HPQWxcwHOwxtyrVV1LKi.jpg", "Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life.", "2018-05-15"));
            add(new Movie(297762, "Wonder Woman", "Wonder Woman", "/imekS7f1OuHyUP2LAiTEM0zBzUz.jpg", "xx/6iUNJZymJBMXXriQyFZfLAKnjO6.jpg", "An Amazon princess comes to the world of Man in the grips of the First World War to confront the forces of evil and bring an end to human conflict.", "2017-05-30"));
            add(new Movie(351286, "Jurassic World", "Jurassic World", "/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg", "/t5KONotASgVKq4N19RyhIthWOPG.jpg", "Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.", "2015-06-06"));
            add(new Movie(525102, "Girl Lost", "Girl Lost", "/n3PDMPyFG04bcXUHHhRIwWd9cx1.jpg", "/j3FnUedjz0NHYFfZ62u9WsBU0wy.jpg", "Girl Lost tackles the issue of underage prostitution as told through the eyes of a wayward teen. Groomed by her own mother to work in the underbelly of Los Angeles, the young girl struggles to survive in this dark world.", "2018-05-01"));
            add(new Movie(337167, "Fifty Shades Freed", "Fifty Shades Freed", "/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg", "/9ywA15OAiwjSTvg3cBs9B7kOCBF.jpg", "Believing they have left behind shadowy figures from their past, newlyweds Christian and Ana fully embrace an inextricable connection and shared life of luxury. But just as she steps into her role as Mrs. Grey and he relaxes into an unfamiliar stability, new threats could jeopardize their happy ending before it even begins.", "2018-01-17"));
            add(new Movie(80752, "The Dark Side of Love", "Fotografando Patrizia", "/eYO04ARWm4gT7yhakM7C6I7vT2n.jpg", "/u5Qpck9L544iVMO2zHXYzXCL8aZ.jpg", "The sexual relationship between a successful woman and her brother, an introvert, hypochondriacal youth, who is also a pornophile.", "1984-12-01"));
        }};
    }

}
