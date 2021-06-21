package com.example.minitwitter.common;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    private static MyApp instancia;

    public static MyApp getInstancia() {
        return instancia;
    }

    public static Context getconContext(){
        return  instancia;
    }

    @Override
    public void onCreate(){
        instancia = this;
        super.onCreate();
    }
}
