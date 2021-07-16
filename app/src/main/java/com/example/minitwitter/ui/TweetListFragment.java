package com.example.minitwitter.ui;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minitwitter.R;
import com.example.minitwitter.common.Constants;
import com.example.minitwitter.data.TweetViewModel;
import com.example.minitwitter.response.Tweet;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class TweetListFragment extends Fragment {

    private Integer tweetListType = Constants.TWEET_LIST_ALL;
    RecyclerView recyclerView;
    MyTweetRecyclerViewAdapter adapter;
    private List<Tweet> listTweet;
    private TweetViewModel tweetVieModel;
    SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TweetListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TweetListFragment newInstance(Integer twwetListType) {
        TweetListFragment fragment = new TweetListFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(Constants.TWEET_LIST_TYPE, twwetListType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetVieModel = new ViewModelProvider(requireActivity()).get(TweetViewModel.class);
        if (getArguments() != null) {
            tweetListType = getArguments().getInt(Constants.TWEET_LIST_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet_list, container, false);

        // Set the adapter
            Context context = view.getContext();
            recyclerView = view.findViewById(R.id.list);
            swipeRefreshLayout = view.findViewById(R.id.swiperefReshLayout);
            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAzul,
                    context.getTheme()));
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(true);
                    if(tweetListType==Constants.TWEET_LIST_FAVORITOS){
                        loadNewFavTweets();
                    }else{
                        loadNewAllTweets();
                    }
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            if(tweetListType==Constants.TWEET_LIST_FAVORITOS){
                loadFavTweets();
            }else{
                loadAllTweets();
            }

        return view;
    }

    private void loadNewFavTweets() {
        tweetVieModel.getNewFavDataAllTweets().observe(requireActivity(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
                listTweet =tweets;
                adapter.setData(listTweet);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadFavTweets() {
        tweetVieModel.getLiveDataFavTweets().observe(requireActivity(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
                listTweet =tweets;
                adapter.setData(listTweet);
                swipeRefreshLayout.setRefreshing(false);
                tweetVieModel.getNewLiveDataAllTweets().removeObserver(this);
            }
        });
    }

    private void loadAllTweets() {
        adapter = new MyTweetRecyclerViewAdapter(getActivity(), listTweet, requireActivity());
        recyclerView.setAdapter(adapter);

        tweetVieModel.getLiveDataAllTweets().observe(requireActivity(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
                listTweet = tweets;
                adapter.setData(listTweet);
            }
        });
    }

    private void loadNewAllTweets() {
        adapter = new MyTweetRecyclerViewAdapter(getActivity(), listTweet, requireActivity());
        recyclerView.setAdapter(adapter);

        tweetVieModel.getNewLiveDataAllTweets().observe(requireActivity(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
                listTweet = tweets;
                adapter.setData(listTweet);
                swipeRefreshLayout.setRefreshing(false);
                tweetVieModel.getNewLiveDataAllTweets().removeObserver(this);
            }
        });
    }


}