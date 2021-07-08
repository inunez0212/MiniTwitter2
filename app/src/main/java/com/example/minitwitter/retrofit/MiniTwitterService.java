package com.example.minitwitter.retrofit;

import com.example.minitwitter.request.RequestCreateTweet;
import com.example.minitwitter.request.RequestLogin;
import com.example.minitwitter.request.RequestSignUp;
import com.example.minitwitter.response.ResponseAuth;
import com.example.minitwitter.response.Tweet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MiniTwitterService {


    @POST("auth/login")
    Call<ResponseAuth> doLogin(@Body RequestLogin request);

    @POST("auth/signUp")
    Call<ResponseAuth> signUp(@Body RequestSignUp request);

    @GET("tweets/all")
    Call<List<Tweet>> getAllTweets();

    @POST("tweets/create")
    Call<Tweet> createTweet(@Body RequestCreateTweet tweet);

    @POST("tweets/like/{id}")
    Call<Tweet> likeTweet(@Path("id") Integer id);

}
