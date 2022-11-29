package com.example.finalanime.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;

    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";

    private static final String IS_LOGIN = "IsLoggedIn";

    // user session variable

    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_USERNAME = "usename";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENUMBER = "phobneNumber";
    public static final String KEY_PASSWORD = "password";

    // remember me variable

    private static final String IS_REMEMBERME = "IsRememberMe";
    public static final String KEY_SESSIONUSERNAME = "usename";
    public static final String KEY_SESSIONPASSWORD = "password";

    public SessionManager(Context _context, String sessionName) {
        context = _context;
        usersSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = usersSession.edit();
    }

    public void createLoginSession(String fullName, String username, String email, String phoneNo, String password) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONENUMBER, phoneNo);
        editor.putString(KEY_PASSWORD, password);

        editor.commit();
    }

    public HashMap<String, String> getUserDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_FULLNAME, usersSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_USERNAME, usersSession.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, usersSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PHONENUMBER, usersSession.getString(KEY_PHONENUMBER, null));
        userData.put(KEY_PASSWORD, usersSession.getString(KEY_PASSWORD, null));

        return userData;
    }

    public Boolean checkLogin() {
        if (usersSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else return false;
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }

    public void createRememberMeSession(String username, String password) {

        editor.putBoolean(IS_REMEMBERME, true);
        editor.putString(KEY_SESSIONUSERNAME, username);
        editor.putString(KEY_SESSIONPASSWORD, password);

        editor.commit();
    }

    public HashMap<String, String> getRememberDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_SESSIONUSERNAME, usersSession.getString(KEY_SESSIONUSERNAME, null));
        userData.put(KEY_SESSIONPASSWORD, usersSession.getString(KEY_SESSIONPASSWORD, null));

        return userData;
    }

    public Boolean checkRememberMe() {
        if (usersSession.getBoolean(IS_REMEMBERME, false)) {
            return true;
        } else return false;
    }
}
