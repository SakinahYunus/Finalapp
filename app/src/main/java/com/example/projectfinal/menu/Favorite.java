package com.example.projectfinal.menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectfinal.R;
import com.example.projectfinal.adapter.MovAdapter;
import com.example.projectfinal.api.ApiClient;
import com.example.projectfinal.api.ApiInterface;
import com.example.projectfinal.modelMov.ResponseMov;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Favorite#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favorite extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Favorite() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favorite.
     */
    // TODO: Rename and change types and number of parameters
    public static Favorite newInstance(String param1, String param2) {
        Favorite fragment = new Favorite();
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

    String APIKEY = "d8d7d751910a84dbcde954c01050ac8f";
    String lang = "en-US";
    String category = "now_playing";
    int PAGE = 1;
    List<ResponseMov> list = new ArrayList<ResponseMov>();
    MovAdapter adapter;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView tv_status;
    RecyclerView rv_favorite;

    String language;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager grid = new GridLayoutManager(getContext(), 2);


        rv_favorite = view.findViewById(R.id.rv_favorite);
        rv_favorite.setHasFixedSize(true);
        rv_favorite.setLayoutManager(grid);

        preferences = getActivity().getSharedPreferences("FavoriteMovie", Context.MODE_PRIVATE);
        editor = preferences.edit();

        language = preferences.getString("lang", "en");

        tv_status = view.findViewById(R.id.tv_status);
        rv_favorite = view.findViewById(R.id.rv_favorite);

        if (preferences.getString("favorite", "").equals("")) {
            tv_status.setVisibility(View.VISIBLE);
            tv_status.setText(language.equals("en") ? "Favorite Movie Not Found": "Film Favorite Tidak Ditemukan");
            rv_favorite.setVisibility(View.GONE);
        } else {
            rv_favorite.setVisibility(View.VISIBLE);
            tv_status.setVisibility(View.GONE);

//            System.out.println(preferences.getString("favorite", "Kosong"));

//            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

//            String[] allFav = preferences.getString("favorite", "").split(" ");
//            System.out.println("On Create All Fav : " + preferences.getString("favorite", "") + " End");
//            list.clear();
//
//            for (int i = 0; i < allFav.length; i++) {
////                System.out.println(allFav[i]);
//                Call<ResponseMov> call = apiInterface.getMovieById(allFav[i], APIKEY);
//                int x = i;
//                call.enqueue(new Callback<ResponseMov>() {
//                    @Override
//                    public void onResponse(Call<ResponseMov> call, retrofit2.Response<ResponseMov> response) {
////                        list.clear();
//                        list.add(response.body());
////                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseMov> call, Throwable t) {
//
//                    }
//                });
//            }
//            adapter = new MovAdapter(getContext(), list);
//            rv_favorite.setAdapter(adapter);





        }
        // Inflate the layout for this fragment
        return view;
    }

//    @Override
    public void onResume() {
        if (preferences.getString("favorite", "").equals("")) {
            tv_status.setVisibility(View.VISIBLE);
            tv_status.setText(language.equals("en") ? "Favorite list is still empty" : "Daftar Favorit Masih Kosong");
            rv_favorite.setVisibility(View.GONE);
        } else {
            rv_favorite.setVisibility(View.VISIBLE);
            tv_status.setVisibility(View.GONE);
//            System.out.println(preferences.getString("favorite", "Kosong"));

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            String[] allFav = preferences.getString("favorite", "").split(" ");
            System.out.println("On Resume All Fav : " + preferences.getString("favorite", "") + " End");

            list.clear();
            for (int i = 0; i < allFav.length; i++) {
//                System.out.println(allFav[i]);
                Call<ResponseMov> call = apiInterface.getMovieById(allFav[i], APIKEY);
                int x = i;
                call.enqueue(new Callback<ResponseMov>() {
                    @Override
                    public void onResponse(Call<ResponseMov> call, retrofit2.Response<ResponseMov> response) {
//                        list.clear();
                        list.add(response.body());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ResponseMov> call, Throwable t) {

                    }
                });
            }
            adapter = new MovAdapter(getContext(), list);
            rv_favorite.setAdapter(adapter);
        }
        super.onResume();
    }
}