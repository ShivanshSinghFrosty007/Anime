package com.example.finalanime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EpisodeSelector extends AppCompatActivity {

    LinearLayout buttonLayout;

    int maxEp;
    String id, type, epNo, cases, position;
    ImageView imageView;
    TextView textView, desc;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_selector);

        imageView = findViewById(R.id.sele_img);
        textView = findViewById(R.id.sele_name);

        desc = findViewById(R.id.desc);

        textView.setText(getIntent().getStringExtra("name"));

        type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");
        epNo = getIntent().getStringExtra("ep_no");
        cases = getIntent().getStringExtra("case");
        position = getIntent().getStringExtra("position");

        if (cases!= null && cases.equals("item")){
            myRef = database.getReference("Anime").child("categories").child(position).child("item").child(id);
        }
        else {
            myRef = database.getReference("Anime").child(type).child(id);
        }

        myRef.child("desc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                desc.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Glide.with(this).load(getIntent().getStringExtra("image")).into(imageView);

        maxEp = Integer.parseInt(epNo);

        buttonLayout = findViewById(R.id.button_layout);

        buttonLayout.setWeightSum(4);

        for (int i = 1; i <= maxEp; i++) {

            Button button = new Button(this);
            Button button2 = new Button(this);
            Button button3 = new Button(this);
            Button button4 = new Button(this);

            LinearLayout linearLayout = new LinearLayout(this);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams layoutParams = new
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            LinearLayout.LayoutParams layoutParams1 = new
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            linearLayout.setLayoutParams(layoutParams1);

            if (i<=maxEp) {
                button.setId(i);
                button.setOnClickListener(getOnClick(String.valueOf(i)));
                button.setText(String.valueOf(i++));
                linearLayout.addView(button);
            }
            if (i<=maxEp) {
                button2.setId(i);
                button2.setOnClickListener(getOnClick(String.valueOf(i)));
                button2.setText(String.valueOf(i++));
                linearLayout.addView(button2);
            }
            if (i<=maxEp){
                button3.setId(i);
                button3.setOnClickListener(getOnClick(String.valueOf(i)));
                button3.setText(String.valueOf(i++));
                linearLayout.addView(button3);
            }
            if (i<=maxEp){
                button4.setId(i);
                button4.setOnClickListener(getOnClick(String.valueOf(i)));
                button4.setText(String.valueOf(i));
                linearLayout.addView(button4);
            }

            buttonLayout.addView(linearLayout, layoutParams);
        }
    }

    private View.OnClickListener getOnClick(String btnId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Player.class);
                intent.putExtra("id", id);
                intent.putExtra("type", type);
                intent.putExtra("btn_id", btnId);

                startActivity(intent);
            }
        };
    }
}