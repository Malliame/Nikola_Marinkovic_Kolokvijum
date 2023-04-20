package com.example.kolokvijum.view.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.kolokvijum.R;
import com.example.kolokvijum.view.fragments.LoginFragment;
import com.example.kolokvijum.view.fragments.MainFragment;
import com.example.kolokvijum.viewmodels.SplashViewModel;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    public final static String LOGIN_KEY = "loginKey";

    private SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        // Handle the splash screen transition.
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return false;
//            Boolean value = splashViewModel.isLoading().getValue();
//            if (value == null) return false;
//            return value;
        });

        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        init();
    }

    private void init() {
        initFragment();
    }

    private void initFragment() {

        Boolean isLogged;

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        isLogged = sharedPreferences.getBoolean(LOGIN_KEY, false);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(isLogged)transaction.add(R.id.addFragmentFcv, new MainFragment(1));
        else transaction.add(R.id.addFragmentFcv, new LoginFragment());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
    }
}
