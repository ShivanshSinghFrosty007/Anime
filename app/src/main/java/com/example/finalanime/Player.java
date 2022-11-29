package com.example.finalanime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Player extends AppCompatActivity {

    String id, type, btnId;
    String cases, p_id;
    ImageView btFullScreen;

    private SimpleExoPlayer player;
    PlayerView playerView;

    ProgressBar progressBar;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    View hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        btnId = getIntent().getStringExtra("btn_id");

        hide = getWindow().getDecorView();
        hideSystemUi();

        playerView = findViewById(R.id.player);
//        btFullScreen = playerView.findViewById(R.id.bt_fullscreen);

        p_id = getIntent().getStringExtra("p_id");

        cases = getIntent().getStringExtra("case");

        if (cases != null && cases.equals("category")) {
            myRef = database.getReference("Anime").child(type).child(p_id).child("item").child(id).child(btnId);
        } else {
            myRef = database.getReference("Anime").child(type).child(id).child(btnId);
        }

        Player();

    }

    private void Player() {
        progressBar = findViewById(R.id.player_progress);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        (trackSelector).setParameters(
                (trackSelector).getParameters().buildUpon().setMaxVideoSizeSd()
                        .setPreferredAudioLanguage("en")
                        .setPreferredTextLanguage("en")
                        .build());
        player = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).build();
        playerView.setPlayer(player);
        myRef.child("link").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MediaItem mediaItem = MediaItem.fromUri(snapshot.getValue(String.class));
                player.setMediaItem(mediaItem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        player.prepare();
        playerView.setKeepScreenOn(true);
        player.setPlayWhenReady(true);

        player.addListener(new com.google.android.exoplayer2.Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == player.STATE_BUFFERING) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                else if (playbackState == player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hide.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    private void hideSystemUi() {
        hide.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    hide.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                }
            }
        });
    }
}