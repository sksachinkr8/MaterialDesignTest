package com.sachinkumar.materialdesigntest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sachinkumar.materialdesigntest.AdapterBoxOffice;
import com.sachinkumar.materialdesigntest.MyApplication;
import com.sachinkumar.materialdesigntest.R;
import com.sachinkumar.materialdesigntest.model.Movie;
import com.sachinkumar.materialdesigntest.network.VolleySingleton;

import static com.sachinkumar.materialdesigntest.model.UrlEndpoints.*;
import static com.sachinkumar.materialdesigntest.model.Keys.EndPointsBoxOffice.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by sachinkumar on 09/11/15.
 */
public class Exclusive extends Fragment {

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    ArrayList<Movie> moviesList = new ArrayList<>();
    private AdapterBoxOffice adapterBoxOffice;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private RecyclerView listMovieHits;
    private TextView textVolleyError;

//    public static final String URL_ROTTEN_TOMATOES_BOX_OFFICE = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json";

    public static String getRequestUrl(int limit) {

        return URL_BOX_OFFICE
                + URL_CHAR_QUESTION
                + URL_PARAM_APIKEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMPERSAND
                + URL_PARAM_LIMIT + limit;

//        return URL_ROTTEN_TOMATOES_BOX_OFFICE+"?apikey="+ MyApplication.API_KEY_ROTTEN_TOMATOES+"&limit="+limit;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        sendJsonRequest();

    }

    private void sendJsonRequest() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(30), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                textVolleyError.setVisibility(View.GONE);
                moviesList = parseJsonResponse(response);
                adapterBoxOffice.setListMovies(moviesList);
//                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleVolleyError(error);
            }
        });

        requestQueue.add(request);
    }

    public void handleVolleyError(VolleyError error) {
        textVolleyError.setVisibility(View.VISIBLE);
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            textVolleyError.setText(R.string.error_timeout);
        }
        else if (error instanceof AuthFailureError) {
            textVolleyError.setText(R.string.error_auth);
        }
        else if (error instanceof ServerError) {
            textVolleyError.setText(R.string.error_server);
        }
        else if (error instanceof NetworkError) {
            textVolleyError.setText(R.string.error_network);
        }
        else if (error instanceof ParseError) {
            textVolleyError.setText(R.string.error_parse);
        }
    }

    private ArrayList<Movie> parseJsonResponse(JSONObject response) {

        ArrayList<Movie> listMovie = new ArrayList<>();

        if (response!=null || response.length() > 0 ) {

            try {
//                StringBuilder data = new StringBuilder();

                JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);
                for (int i = 0; i < arrayMovies.length(); i++) {

                    long id = -1;
                    String title = "NA";
                    String releaseDate = "NA";
                    int audienceScore = -1;
                    String synopsis = "NA";
                    String urlThumbnail = "NA";

                    JSONObject currentMovie = arrayMovies.getJSONObject(i);

                    if (currentMovie.has(KEY_ID) && !currentMovie.isNull(KEY_ID)) {
                        id = currentMovie.getLong(KEY_ID);
                    }

                    if (currentMovie.has(KEY_TITLE) && !currentMovie.isNull(KEY_TITLE)) {
                        title = currentMovie.getString(KEY_TITLE);
                    }

                    if (currentMovie.has(KEY_RELEASE_DATES) && !currentMovie.isNull(KEY_RELEASE_DATES)) {
                        JSONObject objectreleasedate = currentMovie.getJSONObject(KEY_RELEASE_DATES);
                        if (objectreleasedate!=null && objectreleasedate.has(KEY_THEATER) && !objectreleasedate.isNull(KEY_THEATER)) {
                            releaseDate = objectreleasedate.getString(KEY_THEATER);
                        }

                    }


                    if (currentMovie.has(KEY_RATINGS) && !currentMovie.isNull(KEY_RATINGS)) {
                        JSONObject objectRatings = currentMovie.getJSONObject(KEY_RATINGS);
                        if (objectRatings!=null && objectRatings.has(KEY_AUDIENCE_SCORE) && !objectRatings.isNull(KEY_AUDIENCE_SCORE)) {
                            audienceScore = objectRatings.getInt(KEY_AUDIENCE_SCORE);
                        }

                    }


                    if (currentMovie.has(KEY_SYNOPSIS) && !currentMovie.isNull(KEY_SYNOPSIS)) {
                        synopsis = currentMovie.getString(KEY_SYNOPSIS);
                    }


                    if (currentMovie.has(KEY_POSTERS) && !currentMovie.isNull(KEY_POSTERS)){
                        JSONObject objectImages = currentMovie.getJSONObject(KEY_POSTERS);
                        if (objectImages!=null && objectImages.has(KEY_THUMBNAIL) && !objectImages.isNull(KEY_THUMBNAIL)) {
                            urlThumbnail = objectImages.getString(KEY_THUMBNAIL);
                        }
                    }


                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);
                    Date date = null;
                    try {
                        date = dateFormat.parse(releaseDate);
                    } catch (ParseException e) {

                    }
                    movie.setReleaseDateTheater(date);
                    movie.setAudienceScore(audienceScore);
                    movie.setSynopsis(synopsis);
                    movie.setUrlThumbnail(urlThumbnail);

                    if (id!=-1 && !title.equals("NA")) {
                        listMovie.add(movie);
                    }


//                data.append(id + " " + title + " " + releaseDate + " " + audienceScore + "\n");
                }
//            Toast.makeText(getActivity(), listMovie.toString(), Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return listMovie;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exclusive, container, false);
        textVolleyError = (TextView) view.findViewById(R.id.textVolleyError);
        listMovieHits = (RecyclerView) view.findViewById(R.id.listMovieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);
        sendJsonRequest();
        return view;
    }


}
