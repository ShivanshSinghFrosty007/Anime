package com.example.finalanime.Account;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalanime.Helper.SessionManager;
import com.example.finalanime.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Profile extends AppCompatActivity {

    TextView profileUsername, profileName;
    ImageView profileImage;

    private int REQUEST_STORAGE = 111;
    private int REQUEST_FILE = 222;

    private Intent iData;

    Uri imageUrl;
    FirebaseStorage storage;
    StorageReference reference;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileUsername = findViewById(R.id.profile_username);
        profileName = findViewById(R.id.profile_name);
        profileImage = findViewById(R.id.profile_image);

        username = getIntent().getStringExtra("username");

        profileUsername.setText(username);
        profileName.setText(getIntent().getStringExtra("name"));

        storage = FirebaseStorage.getInstance();
        reference = storage.getReference().child(username + "/Profile");

        try {
            final File localFile = File.createTempFile("Profile", "jpeg");
            reference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            profileImage.setImageBitmap(bitmap);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Profile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE);
                } else {
                    Intent galary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(galary, REQUEST_FILE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FILE && resultCode == RESULT_OK) {


            if (data != null) {
                imageUrl = data.getData();
                iData = data;
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUrl);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    profileImage.setImageBitmap(bitmap);
                    // image upload
                    reference.putFile(imageUrl);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void Logout(View v) {
        SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_REMEMBERME);
        sessionManager.logoutUserFromSession();
        Intent intent = new Intent(getApplicationContext(), LogIn.class);
        startActivity(intent);
        finish();
    }
}