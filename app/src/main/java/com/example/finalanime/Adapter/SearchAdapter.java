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
import com.example.finalanime.data.SearchData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SearchAdapter extends FirebaseRecyclerAdapter<SearchData, SearchAdapter.myViewHolder> {

    private RecyclerViewClickInterface recyclerViewClickInterface;

    public SearchAdapter(@NonNull FirebaseRecyclerOptions<SearchData> options, RecyclerViewClickInterface recyclerViewClickInterface) {
        super(options);
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull SearchData model) {
        holder.name.setText(model.getName());
        holder.epNo.setText("Ep no. "+model.getEp_no());
        Glide.with(holder.img.getContext()).load(model.getImage()).into(holder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list, parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name, epNo;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView2);
            name = itemView.findViewById(R.id.src_name);
            epNo = itemView.findViewById(R.id.src_epno);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    recyclerViewClickInterface.onItemClick(position, "search", getSnapshots().getSnapshot(position));
                }
            });
        }
    }


}
