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
import com.example.finalanime.data.Datas;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecyclerFirebaseAdapter extends FirebaseRecyclerAdapter<Datas, RecyclerFirebaseAdapter.recycleViewHolder> {

    private RecyclerViewClickInterface recyclerViewClickInterface;

    String id, ep_no, img;

    public RecyclerFirebaseAdapter(@NonNull FirebaseRecyclerOptions<Datas> options, RecyclerViewClickInterface recyclerViewClickInterface) {
        super(options);
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @Override
    protected void onBindViewHolder(@NonNull recycleViewHolder holder, int position, @NonNull Datas model) {
        holder.name.setText(model.getName());
        holder.desc.setText(model.getSeason());
        id = model.getId();
        ep_no = model.getEp_no();
        Glide.with(holder.icon.getContext()).load(model.getImage()).into(holder.icon);
        img = model.getImage();
    }

    @NonNull
    @Override
    public recycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapterview, parent, false);
        return new recycleViewHolder(view);
    }

    class recycleViewHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView name;
        TextView desc;

        public recycleViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.logo);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    recyclerViewClickInterface.onItemClick(position, "feature", getSnapshots().getSnapshot(position));
                }
            });
        }
    }
}
