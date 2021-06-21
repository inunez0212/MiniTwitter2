package com.example.minitwitter.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.minitwitter.response.ResponseAuth;

import retrofit2.Response;

public class SharedPreferencesManager {

    private static final String APP_FILE_SETTINGS = "APP_FILE_SETTINGS";

    private SharedPreferencesManager(){

    }

    private static SharedPreferences getSharedPreference(){
        return MyApp.getconContext().getSharedPreferences(APP_FILE_SETTINGS,
                Context.MODE_PRIVATE);
    }

    public static void setString(String label, String data){
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(label, data);
        editor.commit();
    }

    public static void setBoolean(String label, Boolean data){
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putBoolean(label, data);
        editor.commit();
    }

    public static String getString(String label){
        return getSharedPreference().getString(label, null);
    }

    public static Boolean getBoolean(String label){
        return getSharedPreference().getBoolean(label, false);
    }

    public static void setLoginSharedPreferences(Response<ResponseAuth> response) {
        SharedPreferencesManager.setString(Constants.PREF_TOKEN_LOGIN,
                response.body().getToken());
        SharedPreferencesManager.setString(Constants.PREF_USER_LOGIN,
                response.body().getUsername());
        SharedPreferencesManager.setString(Constants.PREF_EMAIL_LOGIN,
                response.body().getEmail());
        SharedPreferencesManager.setString(Constants.PREF_PHOTO_URL_LOGIN,
                response.body().getPhotoUrl());
        SharedPreferencesManager.setString(Constants.PREF_CREATED_LOGIN,
                response.body().getCreated());
        SharedPreferencesManager.setBoolean(Constants.PREF_ACTIVE_LOGIN,
                response.body().getActive());
    }

    public static ResponseAuth getLoginSharedPreferences(Response<ResponseAuth> response) {
        ResponseAuth responseAuth = new ResponseAuth();
        responseAuth.setToken(SharedPreferencesManager.getString(Constants.PREF_TOKEN_LOGIN));
        responseAuth.setUsername(SharedPreferencesManager.getString(Constants.PREF_USER_LOGIN));
        responseAuth.setEmail(SharedPreferencesManager.getString(Constants.PREF_EMAIL_LOGIN));
        responseAuth.setPhotoUrl(SharedPreferencesManager.getString(Constants.PREF_PHOTO_URL_LOGIN));
        responseAuth.setCreated(SharedPreferencesManager.getString(Constants.PREF_CREATED_LOGIN));
        responseAuth.setActive(SharedPreferencesManager.getBoolean(Constants.PREF_ACTIVE_LOGIN));
        return responseAuth;
    }


}
