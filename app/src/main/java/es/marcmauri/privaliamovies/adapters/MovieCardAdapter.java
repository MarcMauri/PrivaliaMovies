package es.marcmauri.privaliamovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.marcmauri.privaliamovies.R;
import es.marcmauri.privaliamovies.models.Movie;
import es.marcmauri.privaliamovies.utils.Util;

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.ViewHolder> {

    private List<Movie> movies;
    private int layout;
    private Context context;


    public MovieCardAdapter(List<Movie> movies, int layout, Context context) {
        this.movies = movies;
        this.layout = layout;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(this.layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(this.movies.get(position), this.context);
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_poster;
        private TextView textView_title;
        private TextView textView_year;


        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView_poster = (ImageView) itemView.findViewById(R.id.imageView_poster);
            this.textView_title = (TextView) itemView.findViewById(R.id.textView_title);
            this.textView_year = (TextView) itemView.findViewById(R.id.textView_year);
        }

        public void bind(final Movie movie, final Context context) {

            final int movieId = movie.getId();

            this.textView_title.setText(movie.getTitle());
            String releaseDate = movie.getReleaseDate();
            //String year = context.getString(R.string.adapter_movie_card_adapter_old);
            String year = "Old";
            if (releaseDate != null && !releaseDate.isEmpty())
                year = movie.getReleaseDate().split("-")[0];
            this.textView_year.setText(year);

            Picasso.with(context)
                    .load(Util.IMG_BASE_URL_LOW_RES + movie.getBackdropPath())
                    .placeholder(context.getDrawable(R.mipmap.ic_launcher_foreground))
                    .fit().into(this.imageView_poster);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Movie ID: " + movieId, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
