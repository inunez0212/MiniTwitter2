package com.example.minitwitter.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.minitwitter.response.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetViewModel extends AndroidViewModel {

    TweetRepository tweetRepository;
    MutableLiveData<List<Tweet>> liveDataAllTweets;
    MutableLiveData<List<Tweet>> liveDataFavTweets;

    public TweetViewModel(@NonNull Application application) {
        super(application);
        tweetRepository = new TweetRepository();
        liveDataAllTweets = tweetRepository.getAllTweets();
        liveDataFavTweets = tweetRepository.getFavTweets();
    }

    public LiveData<List<Tweet>> getLiveDataAllTweets(){
        return liveDataAllTweets;
    }

    public LiveData<List<Tweet>> getLiveDataFavTweets(){
        return liveDataFavTweets;
    }


    public void createNewTweet(String msg){
        tweetRepository.createTweet(msg);
    }

    public LiveData<List<Tweet>> getNewLiveDataAllTweets() {
        liveDataAllTweets = tweetRepository.getAllTweets();
        return liveDataAllTweets;
    }

    public LiveData<List<Tweet>> getNewFavDataAllTweets() {
        getNewLiveDataAllTweets();
        return getLiveDataFavTweets();
    }

    public void likeTweet(Integer id){
        tweetRepository.likeTweet(id);

    }
}
