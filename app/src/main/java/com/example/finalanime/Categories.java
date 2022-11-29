package com.example.finalanime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.finalanime.Adapter.ViewPagerAdapter;
import com.example.finalanime.data.SearchData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.FirebaseDatabase;

public class Categories extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        id = Integer.parseInt(getIntent().getStringExtra("id"));

        viewPager = findViewById(R.id.viewpager_id);
        tabLayout = findViewById(R.id.tab_layout);

        FirebaseRecyclerOptions<SearchData> options1 =
                new FirebaseRecyclerOptions.Builder<SearchData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Anime").child("categories").child("0").child("item"), SearchData.class)
                        .build();

        FirebaseRecyclerOptions<SearchData> options2 =
                new FirebaseRecyclerOptions.Builder<SearchData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Anime").child("categories").child("1").child("item"), SearchData.class)
                        .build();

        FirebaseRecyclerOptions<SearchData> options3 =
                new FirebaseRecyclerOptions.Builder<SearchData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Anime").child("categories").child("2").child("item"), SearchData.class)
                        .build();

        FirebaseRecyclerOptions<SearchData> options4 =
                new FirebaseRecyclerOptions.Builder<SearchData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Anime").child("categories").child("3").child("item"), SearchData.class)
                        .build();

        tabLayout.addTab(tabLayout.newTab().setText("Action"));
        tabLayout.addTab(tabLayout.newTab().setText("Thriller"));
        tabLayout.addTab(tabLayout.newTab().setText("Fantasy"));
        tabLayout.addTab(tabLayout.newTab().setText("Romance"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        adapter = new ViewPagerAdapter(this, new FirebaseRecyclerOptions[]{options1, options2, options3, options4});
        viewPager.setAdapter(adapter);

        selectPage(id);
    }

    void selectPage(int pageIndex) {
        tabLayout.setScrollPosition(pageIndex, 0f, true);
        viewPager.setCurrentItem(pageIndex);
    }
}