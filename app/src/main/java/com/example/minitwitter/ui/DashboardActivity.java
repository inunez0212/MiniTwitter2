package com.example.minitwitter.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.minitwitter.R;
import com.example.minitwitter.common.Constants;
import com.example.minitwitter.common.SharedPreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class DashboardActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    ImageView toolbarImageView;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            =(item -> {
        Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        fragment = TweetListFragment.newInstance(Constants.TWEET_LIST_ALL);
                        floatingActionButton.show();
                        break;
                    case R.id.navigation_dashboard:
                        fragment = TweetListFragment.newInstance(Constants.TWEET_LIST_FAVORITOS);
                        floatingActionButton.hide();
                        break;
                    case R.id.navigation_notifications:
                        floatingActionButton.hide();
                        break;
                }
                if(fragment != null){
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.nav_host_fragment,
                            fragment).commit();
                    return true;
                }

        return false;
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        getSupportActionBar().hide();

        floatingActionButton = findViewById(R.id.fab);
        toolbarImageView =findViewById(R.id.toolbarImageView);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,
                TweetListFragment.newInstance(Constants.TWEET_LIST_ALL)).commit();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NuevoTweetDialogFragment nuevoTweetDialogFragment = new NuevoTweetDialogFragment();
                nuevoTweetDialogFragment.show(getSupportFragmentManager(),
                        "NuevoTweetDialogFragment");

            }
        });

        if(!SharedPreferencesManager.getString(Constants.PREF_PHOTO_URL_LOGIN).isEmpty()){
            Glide.with(this).load(Constants.MINITWITTER_IMAGE_URL +
                    SharedPreferencesManager.getString(Constants.PREF_PHOTO_URL_LOGIN))
                    .into(toolbarImageView);
        }
    }

}