package com.example.minitwitter.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.minitwitter.R;
import com.example.minitwitter.common.Constants;
import com.example.minitwitter.common.MyApp;
import com.example.minitwitter.common.SharedPreferencesManager;
import com.example.minitwitter.data.TweetViewModel;

import org.jetbrains.annotations.NotNull;

public class NuevoTweetDialogFragment  extends DialogFragment implements View.OnClickListener {

    ImageView newTweetAvatar, imageViewCloseNew;
    Button buttonTwittear;
    EditText editTextNewTeweet;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullSreenDialog);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.nuevo_tweet_dialog, container, false);

        imageViewCloseNew = view.findViewById(R.id.imageViewCloseNew);
        newTweetAvatar = view.findViewById(R.id.newTweetAvatar);
        editTextNewTeweet = view.findViewById(R.id.editTextNewTeweet);
        buttonTwittear = view.findViewById(R.id.buttonTwittear);

        buttonTwittear.setOnClickListener(this);
        imageViewCloseNew.setOnClickListener(this);

        if(!SharedPreferencesManager.getString(Constants.PREF_PHOTO_URL_LOGIN).isEmpty()){
            Glide.with(getActivity()).load(Constants.MINITWITTER_IMAGE_URL +
                    SharedPreferencesManager.getString(Constants.PREF_PHOTO_URL_LOGIN))
                    .into(newTweetAvatar);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id==R.id.buttonTwittear){
            if(!editTextNewTeweet.getText().toString().isEmpty()){
                TweetViewModel tweetVieModel = new ViewModelProvider(requireActivity()).get(
                        TweetViewModel.class);
                tweetVieModel.createNewTweet(editTextNewTeweet.getText().toString());
                getDialog().dismiss();

            }else{
                Toast.makeText(getActivity(), "No hay el texto", Toast.LENGTH_LONG).show();
            }

        }else if (id==R.id.imageViewCloseNew){
            if(!editTextNewTeweet.getText().toString().isEmpty()){
                showDialogConfirm("Se perderán todos los cambios, esta seguro de salir?",
                        "Cerrar");
            }else{
                getDialog().dismiss();
            }
        }
    }

    private void showDialogConfirm(String msg, String title){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setMessage(msg).setTitle(title);
        alertBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getDialog().dismiss();
            }
        });
        alertBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getDialog().dismiss();
            }
        });

        alertBuilder.create().show();
    }

}
