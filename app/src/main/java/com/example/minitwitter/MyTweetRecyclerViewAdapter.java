package com.example.minitwitter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.minitwitter.common.Constants;
import com.example.minitwitter.common.SharedPreferencesManager;
import com.example.minitwitter.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.minitwitter.databinding.FragmentTweetBinding;
import com.example.minitwitter.response.Like;
import com.example.minitwitter.response.Tweet;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 */
public class MyTweetRecyclerViewAdapter extends RecyclerView.Adapter<MyTweetRecyclerViewAdapter.ViewHolder> {

    private List<Tweet> mValues;
    private Context ctx;
    String userLogin;

    public MyTweetRecyclerViewAdapter(Context context, List<Tweet> items) {
        ctx = context;
        mValues = items;
        userLogin = SharedPreferencesManager.getString(Constants.PREF_USER_LOGIN);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentTweetBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.userName.setText(holder.mItem.getUser().getUsername());
        holder.message.setText(holder.mItem.getMensaje());
        holder.likeCount.setText(String.valueOf(holder.mItem.getLikes().size()));
        if(!holder.mItem.getUser().getPhotoUrl().equals("")){
            Glide.with(ctx).load("https://www.minitwitter.com/apiv1/uploads/photos/"+
                    holder.mItem.getUser().getPhotoUrl())
                    .into(holder.userImage);
        }

        for(Like like : holder.mItem.getLikes()){
            if(like.getUsername().equals(this.userLogin)){
                Glide.with(ctx).load(R.drawable.ic_baseline_favorite_pink)
                        .into(holder.likeImage);
                holder.likeCount.setTextColor(ctx.getResources().getColor(R.color.pink,
                        ctx.getTheme()));
                holder.likeCount.setTypeface(null, Typeface.BOLD);

                break;
            }

        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView userName;
        public final TextView message;
        public final TextView likeCount;
        public final ImageView likeImage;
        public final ImageView userImage;
        public Tweet mItem;

        public ViewHolder(FragmentTweetBinding binding) {
            super(binding.getRoot());
            userName = binding.textViewUserName;
            message = binding.textViewMessage;
            likeCount = binding.textViewLikeCount;
            likeImage = binding.imageViewLike;
            userImage = binding.imageViewAvatar;
        }



        @Override
        public String toString() {
            return super.toString() + " '" + message.getText() + "'";
        }
    }
}