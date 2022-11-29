package com.example.finalanime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.finalanime.Adapter.RecyclerViewClickInterface;
import com.example.finalanime.Adapter.SearchAdapter;
import com.example.finalanime.data.DatasMp;
import com.example.finalanime.data.SearchData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class Search extends AppCompatActivity implements RecyclerViewClickInterface {

    RecyclerView recview;
    SearchAdapter searchAdapter;
    androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recview = findViewById(R.id.recview);
        searchView = findViewById(R.id.search_bar);

        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<SearchData> options =
                new FirebaseRecyclerOptions.Builder<SearchData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Anime").child("search"), SearchData.class)
                        .build();

        searchAdapter = new SearchAdapter(options, this);
        recview.setAdapter(searchAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return false;
            }
        });
    }

    private void processSearch(String query) {

        String finalStr;

        if (query != null && !query.equals("")) {

            String firstLetStr = query.substring(0, 1);
            firstLetStr = firstLetStr.toUpperCase();
            String remLetStr = query.substring(1);
            finalStr = firstLetStr+remLetStr;
        }
        else {
            finalStr = query;
        }


        FirebaseRecyclerOptions<SearchData> options =
                new FirebaseRecyclerOptions.Builder<SearchData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Anime").child("search")
                                .orderByChild("name").startAt(finalStr).endAt(finalStr + "\uf9ff"), SearchData.class)
                        .build();
        searchAdapter = new SearchAdapter(options, this);
        searchAdapter.startListening();
        recview.setAdapter(searchAdapter);

    }

    protected void onStart() {
        super.onStart();
        searchAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        searchAdapter.stopListening();
    }

    @Override
    public void onItemClick(int position, String type, DataSnapshot dataSnapshot) {
        Intent intent = new Intent(getApplicationContext(), EpisodeSelector.class);

        DatasMp ss = dataSnapshot.getValue(DatasMp.class);

        intent.putExtra("type", type);
        intent.putExtra("ep_no", ss.getEp_no());
        intent.putExtra("image", ss.getImage());
        intent.putExtra("name", ss.getName());
        intent.putExtra("id", String.valueOf(position));

        startActivity(intent);
    }
}