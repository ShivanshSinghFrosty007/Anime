package com.example.finalanime.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalanime.R;
import com.example.finalanime.data.DatasCate;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecyclerFirebaseAdapterCate extends FirebaseRecyclerAdapter<DatasCate, RecyclerFirebaseAdapterCate.recycleViewHolder>{

    RecyclerViewClickInterface recyclerViewClickInterface;

    public RecyclerFirebaseAdapterCate(@NonNull FirebaseRecyclerOptions<DatasCate> options, RecyclerViewClickInterface recyclerViewClickInterface) {
        super(options);
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @Override
    protected void onBindViewHolder(@NonNull recycleViewHolder holder, int position, @NonNull DatasCate model) {
        holder.name.setText(model.getName());
        Glide.with(holder.icon.getContext()).load(model.getImage()).into(holder.icon);
    }

    @NonNull
    @Override
    public recycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapterview2, parent, false);
        return new recycleViewHolder(view);
    }

    class recycleViewHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView name;

        public recycleViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.cate_image);
            name = itemView.findViewById(R.id.cate_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    recyclerViewClickInterface.onItemClick(position, "categories", getSnapshots().getSnapshot(position));
                }
            });
        }
    }

}
