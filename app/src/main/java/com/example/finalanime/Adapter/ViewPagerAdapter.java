package com.example.finalanime.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.finalanime.EpisodeSelector2;
import com.example.finalanime.R;
import com.example.finalanime.data.PagerData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;


public class ViewPagerAdapter extends PagerAdapter implements RecyclerViewClickInterface {

    private RecyclerViewClickInterface recyclerViewClickInterface;

    Context context;

    FirebaseRecyclerOptions[] options;

    SearchAdapter searchAdapter;

    public ViewPagerAdapter(Context context, FirebaseRecyclerOptions[] options ) {
        this.context = context;
        this.options = options;

    }

    @Override
    public int getCount() {
        return options.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.recycler_view,container,false);
        container.addView(v);

        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));

        searchAdapter = new SearchAdapter(options[position], this);
        searchAdapter.startListening();
        recyclerView.setAdapter(searchAdapter);

        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ViewGroup)object);
    }

    @Override
    public void onItemClick(int position, String type, DataSnapshot dataSnapshot) {
        Intent intent = new Intent(context, EpisodeSelector2.class);

        PagerData ss = dataSnapshot.getValue(PagerData.class);

        intent.putExtra("type", "categories");
        intent.putExtra("p_id", ss.getP_id());
        intent.putExtra("image", ss.getImage());
        intent.putExtra("ep_no", ss.getEp_no());
        intent.putExtra("id", String.valueOf(position));

        context.startActivity(intent);
    }
}
