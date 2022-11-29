package com.example.finalanime.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalanime.R;
import com.example.finalanime.data.DatasMp;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecyclerFirebaseAdapterMP extends FirebaseRecyclerAdapter<DatasMp, RecyclerFirebaseAdapterMP.recycleViewHolder> {

    RecyclerViewClickInterface recyclerViewClickInterface;

    public RecyclerFirebaseAdapterMP(@NonNull FirebaseRecyclerOptions options2, RecyclerViewClickInterface recyclerViewClickInterface) {
        super(options2);
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @Override
    protected void onBindViewHolder(@NonNull recycleViewHolder holder, int position, @NonNull DatasMp model) {
        holder.name.setText(model.getName());
        Glide.with(holder.icon.getContext()).load(model.getImage()).into(holder.icon);
        holder.ratingBar.setRating(Float.parseFloat(model.getRating()));
    }

    @NonNull
    @Override
    public recycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapterview3, parent, false);
        return new recycleViewHolder(view);
    }

    class recycleViewHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView name;
        RatingBar ratingBar;

        public recycleViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.mp_image);
            name = itemView.findViewById(R.id.mp_text);
            ratingBar = itemView.findViewById(R.id.mp_ratingBar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    recyclerViewClickInterface.onItemClick(position, "mostPop", getSnapshots().getSnapshot(position));
                }
            });
        }
    }
}
