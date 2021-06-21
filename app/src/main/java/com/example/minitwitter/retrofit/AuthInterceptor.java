package com.example.minitwitter.retrofit;

import com.example.minitwitter.common.Constants;
import com.example.minitwitter.common.SharedPreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        String token = SharedPreferencesManager.getString(Constants.PREF_TOKEN_LOGIN);
        Request request = chain.request().newBuilder().addHeader("Authorization",
                "Bearer "+token).build();
        return chain.proceed(request);
    }
}
