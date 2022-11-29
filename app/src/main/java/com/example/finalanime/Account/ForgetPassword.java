package com.example.finalanime.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finalanime.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class ForgetPassword extends AppCompatActivity {

    TextInputLayout phoneNo, usernameFP;

    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        phoneNo = findViewById(R.id.phone_no);

        countryCodePicker = findViewById(R.id.countryCodeFP);
        usernameFP = findViewById(R.id.usernameFP);
    }

    public void submit(View v){
        String phone = "+91" + phoneNo.getEditText().getText().toString();
        String username = usernameFP.getEditText().getText().toString();
        Intent intent = new Intent(getApplicationContext(), VerifyPhoneNo.class);
        intent.putExtra("phoneNo", phone);
        intent.putExtra("username", username);
        intent.putExtra("page", "forgetPass");
        startActivity(intent);
    }
}