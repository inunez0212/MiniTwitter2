package com.example.minitwitter.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.minitwitter.response.Tweet;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TweetVieModel extends AndroidViewModel {

    TweetRepository tweetRepository;
    LiveData<List<Tweet>> liveDataAllTweets;

    public TweetVieModel(@NonNull @NotNull Application application) {
        super(application);
        tweetRepository = new TweetRepository();
        liveDataAllTweets = tweetRepository.getAllTweets();
    }

    public LiveData<List<Tweet>> getLiveDataAllTweets(){
        return liveDataAllTweets;
    }

}
