package com.example.kolokvijum.application;

import android.app.Application;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import timber.log.Timber;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

}
