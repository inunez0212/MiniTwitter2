package com.example.minitwitter.data;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.minitwitter.MyTweetRecyclerViewAdapter;
import com.example.minitwitter.common.MyApp;
import com.example.minitwitter.response.Tweet;
import com.example.minitwitter.retrofit.MiniTwitterClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetRepository {

    LiveData<List<Tweet>> liveDataAllTweets;

    TweetRepository(){
        liveDataAllTweets = getAllTweets();
    }

    public LiveData<List<Tweet>> getAllTweets(){
        final MutableLiveData<List<Tweet>> data = new MutableLiveData<>();
        Call<List<Tweet>> allTweetsCall = MiniTwitterClient.getInstance()
                .getMiniTwitterServiceAuth().getAllTweets();

        allTweetsCall.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }else{
                    Toast.makeText(MyApp.getconContext(), "Algo ha ido mal al cargar los tweets",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Toast.makeText(MyApp.getconContext(), "Error en llamada al WS",
                        Toast.LENGTH_LONG).show();
            }
        });

        return data;
    }
}
