package com.example.finalanime.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finalanime.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {

    String usernameFP;

    TextInputLayout newPasswordLayout, confirmPasswordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        newPasswordLayout = findViewById(R.id.newPassword);
        confirmPasswordLayout = findViewById(R.id.confirmPassword);

        usernameFP = getIntent().getStringExtra("username");
    }

    public void ok(View v){
        String newPassword = newPasswordLayout.getEditText().getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(usernameFP).child("password").setValue(newPassword);

        startActivity(new Intent(getApplicationContext(), Profile.class));
        finish();
    }
}