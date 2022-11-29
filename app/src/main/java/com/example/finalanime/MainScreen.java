package com.example.finalanime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finalanime.Account.LogIn;
import com.example.finalanime.Adapter.RecyclerFirebaseAdapter;
import com.example.finalanime.Adapter.RecyclerFirebaseAdapterCate;
import com.example.finalanime.Adapter.RecyclerFirebaseAdapterMP;
import com.example.finalanime.Adapter.RecyclerViewClickInterface;
import com.example.finalanime.data.Datas;
import com.example.finalanime.data.DatasCate;
import com.example.finalanime.data.DatasMp;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class MainScreen extends AppCompatActivity implements RecyclerViewClickInterface, NavigationView.OnNavigationItemSelectedListener {

    RecyclerView featureRecycleView, mostPopularRecycleView, categoriesRecycleView;
    RecyclerFirebaseAdapter recyclerFirebaseAdapter;
    RecyclerFirebaseAdapterMP recyclerFirebaseAdapterMp;
    RecyclerFirebaseAdapterCate recyclerFirebaseAdapterCate;

    TextView searchText;
    ImageView searchImg, menuIcon;
    LinearLayout content;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        searchText = findViewById(R.id.search_text);
        searchImg = findViewById(R.id.search_img);

        content = findViewById(R.id.content);

        menuIcon = findViewById(R.id.menu_icon);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        navigationDrawer();

        featureRecycleView = findViewById(R.id.featured_recycler);
        mostPopularRecycleView = findViewById(R.id.most_popular);
        categoriesRecycleView = findViewById(R.id.categories);

        featureRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mostPopularRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        FirebaseRecyclerOptions<Datas> options =
                new FirebaseRecyclerOptions.Builder<Datas>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Anime").child("feature"), Datas.class)
                        .build();

        recyclerFirebaseAdapter = new RecyclerFirebaseAdapter(options, this);
        featureRecycleView.setAdapter(recyclerFirebaseAdapter);


        FirebaseRecyclerOptions<DatasCate> options2 =
                new FirebaseRecyclerOptions.Builder<DatasCate>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Anime").child("categories"), DatasCate.class)
                        .build();

        recyclerFirebaseAdapterCate = new RecyclerFirebaseAdapterCate(options2, this);
        categoriesRecycleView.setAdapter(recyclerFirebaseAdapterCate);


        FirebaseRecyclerOptions<DatasMp> options3 =
                new FirebaseRecyclerOptions.Builder<DatasMp>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Anime").child("mostPop"), DatasMp.class)
                        .build();

        recyclerFirebaseAdapterMp = new RecyclerFirebaseAdapterMP(options3, this);
        mostPopularRecycleView.setAdapter(recyclerFirebaseAdapterMp);
    }

    private void navigationDrawer() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animatedNaviagationDrawer();
    }

    private void animatedNaviagationDrawer() {

        drawerLayout.setScrimColor(getResources().getColor(R.color.mainColor));

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - 0.7f);
                final float offsetScale = 1 - diffScaledOffset;
                content.setScaleX(offsetScale);
                content.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = content.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                content.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerFirebaseAdapter.startListening();
        recyclerFirebaseAdapterCate.startListening();
        recyclerFirebaseAdapterMp.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerFirebaseAdapter.stopListening();
        recyclerFirebaseAdapterCate.stopListening();
        recyclerFirebaseAdapterMp.stopListening();
    }

    public void search(View v) {
        Intent intent = new Intent(getApplicationContext(), Search.class);

        Pair[] pair = new Pair[1];
        pair[0] = new Pair<View, String>(searchText, "search_text_trans");

        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(MainScreen.this, pair);

        startActivity(intent, options.toBundle());
    }

    public void viewAllCate(View v) {
        Intent intent = new Intent(getApplicationContext(), Categories.class);
        intent.putExtra("id", "0");
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position, String type, DataSnapshot dataSnapshot) {
        if (type.equals("categories")) {
            Intent intent = new Intent(getApplicationContext(), Categories.class);
            intent.putExtra("id", String.valueOf(position));
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), EpisodeSelector.class);
            DatasMp ss = dataSnapshot.getValue(DatasMp.class);
            intent.putExtra("type", type);
            intent.putExtra("ep_no", ss.getEp_no());
            intent.putExtra("name", ss.getName());
            intent.putExtra("image", ss.getImage());
            intent.putExtra("id", String.valueOf(position));
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_login:{
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                intent.putExtra("info", "login");
                startActivity(intent);
                break;
            }

            case R.id.nav_profile:{
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                intent.putExtra("info", "profile");
                startActivity(intent);
                break;
            }

            case R.id.home:{
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

            case R.id.cate:{
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), Categories.class);
                intent.putExtra("id", "0");
                startActivity(intent);
                break;
            }
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    public void Account(View v){
        Intent intent = new Intent(getApplicationContext(), LogIn.class);
        intent.putExtra("info", "profile");
        startActivity(intent);
    }
}