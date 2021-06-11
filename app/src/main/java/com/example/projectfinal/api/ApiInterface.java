package com.example.projectfinal.api;

import com.example.projectfinal.modelMov.ResponseMov;
import com.example.projectfinal.modelMovie.ResponseMovie;
//import com.example.movieproject.modelPeople.ResultPeople;
import com.example.projectfinal.modelTVShow.ResponseTVShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/3/movie/{category}")
    Call<ResponseMovie> getMovie(
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("language") String lang,
            @Query("page") int page
    );

    @GET("/3/search/movie/")
    Call<ResponseMovie> getIndividualsMovie(
            @Query("api_key") String api_key,
            @Query("query") String query
    );

    @GET("/3/movie/{id}")
    Call<ResponseMov> getMovieById(
            @Path("id") String id,
            @Query("api_key") String api_key
    );

//    @GET("/3/movie/{id}/videos")
//    Call<ResponseVideo> getTrailerById(
//            @Path("id") String id,
//            @Query("api_key") String api_key
//    );

//    @GET("/3/movie/{id}/credits")
//    Call<ResultPeople> getCreditsById(
//            @Path("id") String id,
//            @Query("api_key") String api_key
//    );

    @GET("/3/tv/{category}")
    Call<ResponseTVShow> getTVShow(
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("language") String lang,
            @Query("page") int page
    );
}
