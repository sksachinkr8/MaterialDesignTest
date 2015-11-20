package com.sachinkumar.materialdesigntest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.sachinkumar.materialdesigntest.model.Movie;
import com.sachinkumar.materialdesigntest.network.VolleySingleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sachinkumar on 19/11/15.
 */
public class AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.ViewHolderBoxOffice> {

    private LayoutInflater layoutInflater;
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public AdapterBoxOffice(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setListMovies(ArrayList<Movie> listMovies) {
        this.listMovies = listMovies;
        notifyItemRangeChanged(0, listMovies.size());
    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        //Create an object of ViewHolderBoxOffice class
        View view = layoutInflater.inflate(R.layout.custom_movie_box_office_row, parent, false);
        ViewHolderBoxOffice viewHolder = new ViewHolderBoxOffice(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        Movie currentMovie = listMovies.get(position);
        holder.movieTitle.setText(currentMovie.getTitle());
        Date movieReleaseDate = currentMovie.getReleaseDateTheater();
        if (movieReleaseDate!=null) {
            String formatedDate = dateFormat.format(movieReleaseDate);
            holder.movieReleaseDate.setText(formatedDate);
        }
        else {
            holder.movieReleaseDate.setText("NA");
        }

        int audienceScore = currentMovie.getAudienceScore();
        if (audienceScore==-1) {
            holder.movieAudienceScore.setRating(0.0F);
            holder.movieAudienceScore.setAlpha(0.5F);
        }
        else {
            holder.movieAudienceScore.setRating(audienceScore / 20.0F);
            holder.movieAudienceScore.setAlpha(1.0F);
        }

        String urlThumbnail = currentMovie.getUrlThumbnail();
        imageLoader(urlThumbnail, holder);
    }

    public void imageLoader(String urlThumbnail, final ViewHolderBoxOffice holder) {
        if (!urlThumbnail.equals("NA")) {
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movieThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder {

        private ImageView movieThumbnail;
        private TextView movieTitle;
        private TextView movieReleaseDate;
        private RatingBar movieAudienceScore;

        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            movieAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);
        }
    }


}
