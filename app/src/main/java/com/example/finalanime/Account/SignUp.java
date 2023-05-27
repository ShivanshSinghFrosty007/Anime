package com.example.finalanime.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finalanime.R;
import com.example.finalanime.data.UserHelperClass;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhone, regPassword;

    CountryCodePicker countryCodePicker;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.Username);
        regEmail = findViewById(R.id.email);
        regPhone = findViewById(R.id.phone_profile);
        regPassword = findViewById(R.id.password);
        countryCodePicker = findViewById(R.id.countryCode);
    }

    public boolean validationName() {
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError("Feild cannot be Empty");
            return false;
        }
        else {
            regName.setError(null);
            regName.setEnabled(false);
            return true;
        }
    }

    public boolean validationUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "(?=\\s+$)";

        if (val.isEmpty()) {
            regUsername.setError("Feild cannot be Empty");
            return false;
        }
        else if (val.length() > 15){
            regUsername.setError("Username is too long");
            return false;
        }
        else if (val.matches(noWhiteSpace)){
            regUsername.setError("White Spaces are not Allowed");
            return false;
        }
        else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validationEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Feild cannot be Empty");
            return false;
        }
        else if (!val.matches(emailPattern)){
            regEmail.setError("Invalid email address");
            return false;
        }
        else {
            regEmail.setError(null);
            return true;
        }
    }

    public boolean validationPhone() {
        String val = regPhone.getEditText().getText().toString();
        if (val.isEmpty()) {
            regPhone.setError("Feild cannot be Empty");
            return false;
        }
        else {
            regPhone.setError(null);
            return true;
        }
    }

    public boolean validationPassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Feild cannot be Empty");
            return false;
        }
        else if (val.matches(passwordVal)){
            regPassword.setError("password too weak");
            return false;
        }
        else {
            regPassword.setError(null);
            return true;
        }
    }

    public void registerUser(View v) {

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        if (!validationName() | !validationUsername() | !validationEmail() | !validationPhone() | !validationPassword()){
            return;
        }

        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
//        String phone = countryCodePicker.getFullNumberWithPlus() + regPhone.getEditText().getText().toString();
        String phone = "+91" + regPhone.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();

        Intent intent = new Intent(getApplicationContext(), Profile.class);
        intent.putExtra("name", name);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("phoneNo", phone);
        intent.putExtra("password", password);

        Intent intent1 = new Intent(getApplicationContext(), VerifyPhoneNo.class);
        intent1.putExtra("phoneNo", phone);
        intent1.putExtra("page", "newUser");
        startActivity(intent1);

        UserHelperClass helperClass = new UserHelperClass(name, username, email, phone, password);
        reference.child(username).setValue(helperClass);
    }

    public void back(View v){
        onBackPressed();
    }
}