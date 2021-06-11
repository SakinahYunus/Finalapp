package com.example.projectfinal.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectfinal.R;
import com.example.projectfinal.adapter.TVShowAdapater;
import com.example.projectfinal.api.ApiClient;
import com.example.projectfinal.api.ApiInterface;
import com.example.projectfinal.modelTVShow.ResponseTVShow;
import com.example.projectfinal.modelTVShow.ResultTVShow;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TvShow#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TvShow extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TvShow() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TvShow.
     */
    // TODO: Rename and change types and number of parameters
    public static TvShow newInstance(String param1, String param2) {
        TvShow fragment = new TvShow();
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

    // Variable
    RecyclerView rv_content;
    // https://api.themoviedb.org/3/movie/now_playing?api_key=d8d7d751910a84dbcde954c01050ac8f&language=en-US&page=1
    private TVShowAdapater adapter;
    String APIKEY = "d8d7d751910a84dbcde954c01050ac8f";
    String lang = "en-US";
    String category = "airing_today";
    int PAGE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager grid = new GridLayoutManager(getContext(), 2);

        rv_content = view.findViewById(R.id.rv_now_playing);
        rv_content.setHasFixedSize(true);
        rv_content.setLayoutManager(grid);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseTVShow> call = apiInterface.getTVShow(category, APIKEY, lang, PAGE);

        call.enqueue(new Callback<ResponseTVShow>() {
            @Override
            public void onResponse(Call<ResponseTVShow> call, Response<ResponseTVShow> response) {
                List<ResultTVShow> mlist = response.body().getResultTVShows();
                adapter = new TVShowAdapater(getContext(), mlist);
                rv_content.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseTVShow> call, Throwable t) {

            }
        });

        return view;
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }
}