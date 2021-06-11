package com.example.projectfinal.modelMovie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMovie {
    @SerializedName("dates")
    @Expose
    private DatesMovie datesMovie;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<ResultMovie> resultMovies = null;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    public DatesMovie getDatesMovie() {
        return datesMovie;
    }

    public void setDatesMovie(DatesMovie datesMovie) {
        this.datesMovie = datesMovie;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<ResultMovie> getResultMovies() {
        return resultMovies;
    }

    public void setResultMovies(List<ResultMovie> resultMovies) {
        this.resultMovies = resultMovies;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}
