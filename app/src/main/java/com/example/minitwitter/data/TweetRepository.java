package com.example.minitwitter.data;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.minitwitter.common.Constants;
import com.example.minitwitter.common.MyApp;
import com.example.minitwitter.common.SharedPreferencesManager;
import com.example.minitwitter.request.RequestCreateTweet;
import com.example.minitwitter.response.Like;
import com.example.minitwitter.response.Tweet;
import com.example.minitwitter.retrofit.MiniTwitterClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetRepository {

    MutableLiveData<List<Tweet>> liveDataAllTweets;

    MutableLiveData<List<Tweet>> liveDataFavTweets;

    String userName;

    TweetRepository(){

        liveDataAllTweets = getAllTweets();
        userName = SharedPreferencesManager.getString(Constants.PREF_USER_LOGIN);

    }

    public MutableLiveData<List<Tweet>> getAllTweets(){

        if(liveDataAllTweets == null){
            liveDataAllTweets = new MutableLiveData<>();
        }

        Call<List<Tweet>> allTweetsCall = MiniTwitterClient.getInstance()
                .getMiniTwitterServiceAuth().getAllTweets();

        allTweetsCall.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                if(response.isSuccessful()){
                    liveDataAllTweets.setValue(response.body());
                }else{
                    showToastError("Algo ha ido mal al cargar los tweets");
                }
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                showToastError("Error en llamada al WS");
            }
        });

        return liveDataAllTweets;
    }

    public MutableLiveData<List<Tweet>> getFavTweets(){

        if(liveDataFavTweets ==null){
            liveDataFavTweets = new MutableLiveData<>();
        }
        List<Tweet> newFavList = new ArrayList<>();
        for(Tweet tweet : liveDataAllTweets.getValue()){
            List<Like> likesList = tweet.getLikes();
            for(Like like : likesList){
                if(like.getUsername().equals(userName)){
                    newFavList.add(tweet);
                    break;
                }
            }
        }
        liveDataFavTweets.setValue(newFavList);
        return  liveDataFavTweets;
    }


    public void createTweet(String msg){
        RequestCreateTweet request = new RequestCreateTweet();
        request.setMensaje(msg);
        Call<Tweet> call = MiniTwitterClient.getInstance().getMiniTwitterServiceAuth().
                createTweet(request);
        call.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> call, Response<Tweet> response) {
                if (response.isSuccessful()) {
                    List<Tweet> listClone = new ArrayList<>();
                    listClone.add(response.body());
                    if (liveDataAllTweets.getValue() != null) {
                        for (Tweet tweet : liveDataAllTweets.getValue()) {
                            listClone.add(new Tweet(tweet.getId(),
                                    tweet.getMensaje(), tweet.getLikes(),
                                    tweet.getUser()));
                        }
                    }
                    liveDataAllTweets.setValue(listClone);
                    getFavTweets();
                } else {
                    showToastError("Algo ha ido mal al crear el tweet");
                }
            }

            @Override
            public void onFailure(Call<Tweet> call, Throwable t) {
                showToastError("Error en llamada al WS para crear ws");
            }
        });

    }

    public void likeTweet(Integer idTweet){
        Call<Tweet> call = MiniTwitterClient.getInstance().getMiniTwitterServiceAuth().
                likeTweet(idTweet);
        call.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> call, Response<Tweet> response) {
                if (response.isSuccessful()) {
                    List<Tweet> listClone = new ArrayList<>();
                    if (liveDataAllTweets.getValue() != null) {
                        for (Tweet tweet : liveDataAllTweets.getValue()) {
                            if(tweet.getId().equals(idTweet)){
                                listClone.add(new Tweet(response.body().getId(),
                                        response.body().getMensaje(), response.body().getLikes(),
                                        response.body().getUser()));
                            }else{
                                listClone.add(new Tweet(tweet.getId(),
                                        tweet.getMensaje(), tweet.getLikes(),
                                        tweet.getUser()));
                            }
                        }
                    }
                    liveDataAllTweets.setValue(listClone);
                    getFavTweets();
                } else {
                    Log.e("likeTweet","Error click en like");
                    showToastError("Algo ha ido mal");
                }
            }

            @Override
            public void onFailure(Call<Tweet> call, Throwable t) {
                showToastError("Error en llamada al WS para crear ws");
            }
        });

    }

    private void showToastError(String s) {
        Toast.makeText(MyApp.getconContext(), s, Toast.LENGTH_LONG).show();
    }

}
