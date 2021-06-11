package com.example.projectfinal.menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.projectfinal.R;
import com.example.projectfinal.adapter.MovieAdapter;
import com.example.projectfinal.api.ApiClient;
import com.example.projectfinal.api.ApiInterface;
import com.example.projectfinal.modelMovie.ResponseMovie;
import com.example.projectfinal.modelMovie.ResultMovie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Upcoming#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Upcoming extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Upcoming() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Upcoming.
     */
    // TODO: Rename and change types and number of parameters
    public static Upcoming newInstance(String param1, String param2) {
        Upcoming fragment = new Upcoming();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    List<ResultMovie> mlist;
    RecyclerView rv_content;
    TextView tv_statusSearch;
    private MovieAdapter adapter;
    String APIKEY = "dca21bd484ff27d3aeb164c084961d08";
    String lang = "en-US";
    String category = "upcoming";
    int PAGE = 1;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String language;

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager grid = new GridLayoutManager(getContext(), 2);

        sharedPreferences = this.getActivity().getSharedPreferences("FavoriteMovie", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("lang", "en");

        tv_statusSearch = view.findViewById(R.id.tv_search_status);
        rv_content = view.findViewById(R.id.rv_now_playing);
        rv_content.setHasFixedSize(true);
        rv_content.setLayoutManager(grid);

        rv_content.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                System.out.println(dy);
                if (dy > 0) { //check for scroll down
                    visibleItemCount = grid.getChildCount();
                    totalItemCount = grid.getItemCount();
                    pastVisiblesItems = grid.findFirstVisibleItemPosition();

//                    System.out.println(visibleItemCount + " " + totalItemCount + " " + pastVisiblesItems);

                    if (loading) {

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            // Do pagination.. i.e. fetch new data
                            PAGE += 1;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                                    Call<ResponseMovie> call = apiInterface.getMovie(category, APIKEY, lang, PAGE);

                                    call.enqueue(new Callback<ResponseMovie>() {
                                        @Override
                                        public void onResponse(Call<ResponseMovie> call, retrofit2.Response<ResponseMovie> response) {
//                                    mlist = response.body().getResultMovies();
                                            mlist.addAll(response.body().getResultMovies());
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseMovie> call, Throwable t) {

                                        }
                                    });
                                }
                            }, 2000);


                            loading = true;
                        }
                    }
                }
            }
        });

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseMovie> call = apiInterface.getMovie(category, APIKEY, lang, PAGE);

        call.enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, retrofit2.Response<ResponseMovie> response) {
                mlist = response.body().getResultMovies();
                adapter = new MovieAdapter(getContext(), mlist);
                rv_content.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {

            }
        });
        setHasOptionsMenu(true);
        return view;
//        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponseMovie> call = apiInterface.getIndividualsMovie(APIKEY, s);

                call.enqueue(new Callback<ResponseMovie>() {
                    @Override
                    public void onResponse(Call<ResponseMovie> call, retrofit2.Response<ResponseMovie> response) {
                        List<ResultMovie> mlist = response.body().getResultMovies();
                        if (mlist.isEmpty()) {
                            tv_statusSearch.setVisibility(View.VISIBLE);
                            tv_statusSearch.setText(language.equals("en") ? "Movie Not Found" : "Film Tidak Ditemukan");
                            rv_content.setVisibility(View.GONE);
                        } else {
                            tv_statusSearch.setVisibility(View.GONE);
                            rv_content.setVisibility(View.VISIBLE);
                            adapter = new MovieAdapter(getContext(), mlist);
                            rv_content.setAdapter(adapter);
                            System.out.println(s);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMovie> call, Throwable t) {
                        System.out.println(t.getMessage());

                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                Toast j = Toast.makeText(getContext(), s, Toast.LENGTH_SHORT);
//                j.show();
                return false;
            }
        });
        menuItem.setActionView(searchView);
        super.onCreateOptionsMenu(menu, inflater);
    }
}