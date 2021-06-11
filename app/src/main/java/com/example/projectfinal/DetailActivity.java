package com.example.projectfinal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projectfinal.api.ApiClient;
import com.example.projectfinal.api.ApiInterface;
//import com.example.movieproject.modelPeople.CastPeople;
//import com.example.movieproject.modelPeople.CrewPeople;
//import com.example.movieproject.modelPeople.ResultPeople;
import com.example.projectfinal.modelVideo.ResultVideo;

import java.text.DateFormatSymbols;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    ImageView iv_detailImage;
    ImageView iv_backDrop;
    TextView tv_detailTitle;
    TextView tv_detailOverview;
    TextView tv_detailYear;
    TextView tv_detailGenre;
//    TextView tv_detailCreditsCast;
//    TextView tv_detailCreditsCrew;

    TextView tv_overview;
//    TextView tv_credits;
//    TextView tv_trailer;

    RelativeLayout rl_detail;

    RatingBar rb_rating;
    ProgressBar pb_loading;

//    Button b_watchTrailer;

    String id;
    String language;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    List<ResultVideo> list;
//    List<CrewPeople> crewList;
//    List<CastPeople> castList;

    String APIKEY = "d8d7d751910a84dbcde954c01050ac8f";
    String lang = "en-US";
    String category = "now_playing";
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        rl_detail = findViewById(R.id.rl_detail1);
        pb_loading = findViewById(R.id.pb_loadingDetail);
        rl_detail.setVisibility(View.GONE);

        iv_detailImage = findViewById(R.id.iv_detailImage);
        iv_backDrop = findViewById(R.id.iv_backDrop);

        tv_detailTitle = findViewById(R.id.tv_detailTitle);
        tv_detailOverview = findViewById(R.id.tv_detailOverview);
        tv_detailYear = findViewById(R.id.tv_detailYear);
        tv_detailGenre = findViewById(R.id.tv_detailGenre);
//        tv_detailCreditsCast = findViewById(R.id.tv_detailCreditsCast);
//        tv_detailCreditsCrew = findViewById(R.id.tv_detailCreditsCrew);

        tv_overview = findViewById(R.id.tv_overview);
//        tv_credits = findViewById(R.id.tv_credits);
//        tv_trailer = findViewById(R.id.tv_trailer);



//        b_watchTrailer = findViewById(R.id.b_detailTrailler);

        preferences = getSharedPreferences("FavoriteMovie", Context.MODE_PRIVATE);
        language = preferences.getString("lang", "en");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        rb_rating = findViewById(R.id.rb_rating);

        Bundle bundle = getIntent().getExtras();
        String data [] = bundle.getStringArray("data");

        if (data[2] != null) {
            Glide.with(getApplicationContext())
                    .load("https://image.tmdb.org/t/p/w185" + data[2])
                    .into(iv_detailImage);
        }

        if (data[5] != null) {
            Glide.with(getApplicationContext())
                    .load("https://image.tmdb.org/t/p/w185" + data[5])
                    .into(iv_backDrop);
        }

        getSupportActionBar().setTitle(data[0]);
        id = data[6];

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<ResponseVideo> call = apiInterface.getTrailerById(id, APIKEY);

//        call.enqueue(new Callback<ResponseVideo>() {
//            @Override
//            public void onResponse(Call<ResponseVideo> call, Response<ResponseVideo> response) {
//                list = response.body().getResults();
//                System.out.println(id);
//                if (!response.body().getResults().isEmpty()) {
//                    url = response.body().getResults().get(0).getKey();
//                    b_watchTrailer.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + url));
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            Intent chooser = Intent.createChooser(intent, language.equals("en") ? "Open With" : "Buka Dengan");
//                            startActivity(chooser);
//                        }
//                    });
//                } else {
//                    b_watchTrailer.setEnabled(false);
//                    b_watchTrailer.setText(language.equals("en") ? "No Trailer Found :" : "Trailer Tidak Diemukan");
//                }
//            }

//            @Override
//            public void onFailure(Call<ResponseVideo> call, Throwable t) {
//
//            }
//        });

//         Call<ResultPeople> callP = apiInterface.getCreditsById(id, APIKEY);

//         callP.enqueue(new Callback<ResultPeople>() {
//             @Override
//             public void onResponse(Call<ResultPeople> call, Response<ResultPeople> response) {
//                 crewList = response.body().getCrew();
//                 castList = response.body().getCast();
//                 String cast = "";
//                 String crew = "";
//                 for (CrewPeople c : crewList) {
//                     crew += (crew.isEmpty() ? "" : ", ") + c.getName();
//                 }
//                 for (CastPeople c : castList) {
//                     cast += (cast.isEmpty() ? "" : ", ") + c.getName();
//                 }
//                 tv_detailCreditsCast.setText(cast + " " + Html.fromHtml("<b> (Cast) </b>"));
//                 tv_detailCreditsCrew.setText(crew + " " +  Html.fromHtml("<b> (Crew) </b>"));
//             }
//
//             @Override
//             public void onFailure(Call<ResultPeople> call, Throwable t) {
//
//             }
//
//
//         });
//        System.out.println(list.get(0).getKey());

        tv_overview.setText(language.equals("en") ? "Overview :" : "Sinopsis :");
//        tv_trailer.setText(language.equals("en") ? "Trailer :" : "Cuplikan :");

        tv_detailTitle.setText(data[0]);
        tv_detailOverview.setText(data[1]);
        rb_rating.setRating(Float.parseFloat(data[4]) / 2);

        if (data[7].isEmpty()) {
            tv_detailGenre.setText(language.equals("en") ? "Genre Not Found" : "Genre Tidak Ditemukan");
        } else {
            String [] allGenre = data[7].split(" ");
            String newGenre = "";
            for (String g : allGenre) {
                System.out.println("--- |" + g + "| ---");
                newGenre += (newGenre.isEmpty() ? "" : ", ") + getGenre(g);
            }
            System.out.println("--- |" + newGenre + "| ---");

            tv_detailGenre.setText(newGenre);
        }

        String [] year = data[3].split("-");
        tv_detailYear.setText(year[2] + " " + getMonth(Integer.parseInt(year[1])) + " " + year[0]);


//        b_watchTrailer.setText(language.equals("en") ? "Watch Trailer" : "Nonton Trailer");

        pb_loading.setVisibility(View.GONE);
        rl_detail.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.nav_favorite);
        if (containsLike(id)) {
            menuItem.setIcon(R.drawable.ic_baseline_favorite_24);
        } else {
            menuItem.setIcon(R.drawable.ic_baseline_favorite_border_24);
        }



        return super.onCreateOptionsMenu(menu);
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        preferences = getSharedPreferences("FavoriteMovie", Context.MODE_PRIVATE);
        editor = preferences.edit();
        switch (item.getItemId()) {
            case R.id.nav_favorite :
                if (item.getIcon().getConstantState().equals(getDrawable(R.drawable.ic_baseline_favorite_border_24).getConstantState())) {
                    Toast t = Toast.makeText(getApplicationContext(), "Favorite", Toast.LENGTH_SHORT);
                    editor.putString("favorite", preferences.getString("favorite", "") + (preferences.getString("favorite", "").equals("") ? "" : " " ) + id);
                    editor.apply();
                    t.show();
                    System.out.println(preferences.getString("favorite", ""));
                    item.setIcon(R.drawable.ic_baseline_favorite_24);
                } else {
                    Toast t = Toast.makeText(getApplicationContext(), "Unfavorite", Toast.LENGTH_SHORT);
                    t.show();
                    String [] allFav = preferences.getString("favorite", "").split(" ");
                    String newFav = "";
                    for (String fav: allFav) {
                        if (!id.equals(fav)) {
                            newFav += (newFav.isEmpty() ? "" : " ") + fav;
                        }
                    }
                    System.out.println(newFav);
                    editor.putString("favorite", newFav);
                    editor.apply();
                    item.setIcon(R.drawable.ic_baseline_favorite_border_24);
                }
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean containsLike(String id) {
        preferences = getSharedPreferences("FavoriteMovie", Context.MODE_PRIVATE);
        String [] all = preferences.getString("favorite", "").split(" ");

        return preferences.getString("favorite", "").contains(id);
    }

    private String getGenre(String id) {
        int [] idGenre = {28, 12, 16, 35, 80, 99, 18, 10751, 14, 36, 27, 10402, 9648, 10749, 878, 10770, 53, 10752, 37};
        String [] genre = {"Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary", "Drama", "Family", "Fantasy", "History", "Horror", "Music", "Mystery", "Romance", "Science Fiction", "TV Movie", "Thriller", "War", "Western"};
        int idx = 0;
        for (int i = 0; i < idGenre.length; i++) {
            if (idGenre[i] == Integer.parseInt(id)) {
                idx = i;
                break;
            }
        }
        return genre[idx];
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
}