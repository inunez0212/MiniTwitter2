package com.example.minitwitter.retrofit;

import com.example.minitwitter.request.RequestLogin;
import com.example.minitwitter.request.RequestSignUp;
import com.example.minitwitter.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MiniTwitterService {


    @POST("auth/login")
    Call<ResponseAuth> doLogin(@Body RequestLogin request);

    @POST("auth/signUp")
    Call<ResponseAuth> signUp(@Body RequestSignUp request);

}
