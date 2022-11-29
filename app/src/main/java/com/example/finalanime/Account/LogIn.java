package com.example.finalanime.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalanime.Helper.SessionManager;
import com.example.finalanime.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LogIn extends AppCompatActivity {

    Button callSignUp, login_btn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;
    EditText usernameEditText, passwordEditText;
    CheckBox rememberMe;
    String accInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        callSignUp = findViewById(R.id.signUp);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.signin);
        rememberMe = findViewById(R.id.remember_me);
        usernameEditText = findViewById(R.id.Login_username_editText);
        passwordEditText = findViewById(R.id.Login_password_editText);

        accInfo = getIntent().getStringExtra("info");

        SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()){
            HashMap<String, String> rememberMeDetails = sessionManager.getRememberDetailFromSession();
            usernameEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONUSERNAME));
            passwordEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPASSWORD));
            if (accInfo.equals("profile")){
                isIUser();
                finish();
            }
        }

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);

                Pair[] pair = new Pair[7];

                pair[0] = new Pair<View, String>(image, "logo_image");
                pair[1] = new Pair<View, String>(logoText, "logo_text");
                pair[2] = new Pair<View, String>(sloganText, "logo_desc");
                pair[3] = new Pair<View, String>(username, "username_tran");
                pair[4] = new Pair<View, String>(password, "password_tran");
                pair[5] = new Pair<View, String>(login_btn, "signin_tran");
                pair[6] = new Pair<View, String>(callSignUp, "signup_tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LogIn.this, pair);
                startActivity(intent, options.toBundle());
            }
        });
    }

    public boolean validationUsername() {
        String val = username.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            username.setError("Feild cannot be Empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validationPassword() {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Feild cannot be Empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View v) {

        if (!isConnected(this)) {
            showCustomDialog();
        }

        if (!validationUsername() | !validationPassword()) {
            return;
        } else {
            isIUser();
        }
    }

    private void isIUser() {
        String userEnteredUsername = username.getEditText().getText().toString();
        String userEnteredPassword = password.getEditText().getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        if (rememberMe.isChecked()){
            SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberMeSession(userEnteredUsername, userEnteredPassword);
        }

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)) {

                        password.setError(null);
                        password.setErrorEnabled(false);

                        String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);

                        SessionManager sessionManager = new SessionManager(getApplicationContext(), "SESSION_USERSESSION");
                        sessionManager.createLoginSession(nameFromDB, usernameFromDB, phoneNoFromDB, emailFromDB, passwordFromDB);

                        Intent intent = new Intent(getApplicationContext(), Profile.class);

                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("name", nameFromDB);

                        startActivity(intent);
                        finish();
                    } else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }
                else {
                    username.setError("No Such User exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean isConnected(LogIn login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else
            return false;
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Please Connect to the Internet")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), LogIn.class));
                        finish();
                    }
                });
    }

    public void forgetPass(View v){
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);
    }

}