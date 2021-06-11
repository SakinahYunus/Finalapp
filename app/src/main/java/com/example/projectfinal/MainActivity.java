package com.example.projectfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projectfinal.menu.Favorite;
import com.example.projectfinal.menu.NowPlaying;
import com.example.projectfinal.menu.Popular;
import com.example.projectfinal.menu.Upcoming;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnv_nav_menu;
    boolean isFavorite = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("FavoriteMovie", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("lang", "en");

        bnv_nav_menu = findViewById(R.id.bnv_bottom_nav);

        bnv_nav_menu.getMenu().findItem(R.id.nav_bottom_now_playing).setTitle(sharedPreferences.getString("lang", "en").equals("en") ? "Now Playing" : "Sedang Tayang");
        bnv_nav_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_bottom_now_playing :
//                        getSupportActionBar().setTitle("Now Playing");
                        getSupportActionBar().setTitle(language.equals("en") ? "Now Playing" : "Sedang Tayang");
                        item.setTitle(language.equals("en") ? "Now Playing" : "Sedang Tayang");
                        isFavorite = false;
                        fragment = new NowPlaying();
                        break;
                    case R.id.nav_bottom_popular :
//                        getSupportActionBar().setTitle("Popular");
                        getSupportActionBar().setTitle(language.equals("en") ? "Popular" : "Populer");
                        item.setTitle(language.equals("en") ? "Popular" : "Populer");
                        isFavorite = false;
                        fragment = new Popular();
                        break;
                    case R.id.nav_bottom_upcoming :
//                        getSupportActionBar().setTitle("Upcoming");
                        getSupportActionBar().setTitle(language.equals("en") ? "Upcoming" : "Akan Tayang");
                        item.setTitle(language.equals("en") ? "Upcoming" : "Akan Tayang");
                        isFavorite = false;
                        fragment = new Upcoming();
                        break;
                    case R.id.nav_favorite :
//                        getSupportActionBar().setTitle("Favorite");
                        getSupportActionBar().setTitle(language.equals("en") ? "Favorite" : "Favorit");
                        item.setTitle(language.equals("en") ? "Favorite" : "Favorit");
                        isFavorite = true;
                        fragment = new Favorite();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
                return true;
            }
        });


        Fragment f = new NowPlaying();
        getSupportActionBar().setTitle(language.equals("en") ? "Now Playing" : "Sedang Tayang");
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, f).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adtional_menu, menu);
        menu.findItem(R.id.detail_changeLang).setTitle(language.equals("en") ? "Change Language" : "Ganti Bahasa");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String tes = "";
        switch (item.getItemId()) {
            case  R.id.detail_changeLang :
                changeLanguage();
                break;
        }
        Toast c = Toast.makeText(getApplicationContext(), tes, Toast.LENGTH_SHORT);
//        c.show();
        return super.onOptionsItemSelected(item);
    }

    private void changeLanguage() {
        sharedPreferences = getSharedPreferences("FavoriteMovie", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String [] lang = {"English", "Indonesia"};
        String [] xlang = {"en", "ina"};
        final int[] location = {0};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose Your Language");
        builder.setSingleChoiceItems(lang, (language.equals("en") ? 0 : 1), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                location[0] = i;
            }
        });
        builder.setPositiveButton(language.equals("en") ? "Confirm" : "Konfirmasi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editor.putString("lang", xlang[location[0]]);
                editor.apply();
                language = xlang[location[0]];
//                Toast k = Toast.makeText(getApplicationContext(), lang[location[0]], Toast.LENGTH_SHORT);
//                k.show();
                Intent a = new Intent(MainActivity.this, MainActivity.class);
                startActivity(a);
                finish();
            }
        });
        builder.show();
    }
}