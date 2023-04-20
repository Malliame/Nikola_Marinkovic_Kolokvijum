package com.example.kolokvijum.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.kolokvijum.R;
import com.example.kolokvijum.view.activities.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class LoginFragment extends Fragment {

    private Button loginBtn;
    private EditText emailEdt;
    private EditText usernameEdt;
    private EditText passwordEdt;
    private TextView emailError;
    private TextView usernameError;
    private TextView passwordError;
    public static final String CREDENTIALS_FILE_NAME = "credentials.txt";


    public LoginFragment() {
        super(R.layout.fragment_login_menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view){
        initView(view);
        initListeners(view);
    }

    private void initView(View view){
        loginBtn = view.findViewById(R.id.loginBtn);
        emailEdt = view.findViewById(R.id.emailEdt);
        usernameEdt = view.findViewById(R.id.usernameEdt);
        passwordEdt = view.findViewById(R.id.passwordEdt);
        emailError = view.findViewById(R.id.emailErrorText);
        usernameError = view.findViewById(R.id.usernameErrorText);
        passwordError = view.findViewById(R.id.passwordErrorText);
    }

    private void initListeners(View view){
        loginBtn.setOnClickListener(v -> {
            ////Timber.e("check");

            String email = null;
            String username = null;
            String password = null;

            email = emailEdt.getText().toString();
            username = usernameEdt.getText().toString();
            password = passwordEdt.getText().toString();

            //Timber.e(email.length() + "");
            if(email.trim().isEmpty() || email.length() == 0) emailError.setVisibility(View.VISIBLE);
            else emailError.setVisibility(View.GONE);

            //Timber.e(username.length() + "");
            if(username.trim().isEmpty() || username.length() == 0) usernameError.setVisibility(View.VISIBLE);
            else usernameError.setVisibility(View.GONE);

            //Timber.e(password.length() + "");
            if(password.trim().isEmpty() || password.length() == 0) passwordError.setVisibility(View.VISIBLE);
            else passwordError.setVisibility(View.GONE);

            //TODO don't show toasts if something is red

            if(isEmail(email) == 1)
                if(password.length() > 4 && containsCapital(password) == 1 && containsNumber(password) == 1 && containsForbidden(password) == 0){
                    if(checkCredentials(username,password,email) == 1){
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
                        sharedPreferences
                                .edit()
                                .putBoolean(MainActivity.LOGIN_KEY, true)
                                .apply();

                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.addFragmentFcv, new MainFragment(1));
                        transaction.commit();
                        Toast.makeText(getActivity(), R.string.login_success, Toast.LENGTH_LONG).show();
                    }
                    else Toast.makeText(getActivity(), R.string.credentials_valid_error, Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(getActivity(), R.string.password_tooltip, Toast.LENGTH_LONG).show();
            else Toast.makeText(getActivity(), R.string.email_format_error, Toast.LENGTH_LONG).show();

        });
    }

    private int containsCapital(String s){

        for(int i = 0; i < s.length(); i++){
            if(Character.isUpperCase(s.charAt(i))){
                //Timber.e("Nasao veliko slovo");
                return 1;
            }
        }
        //Timber.e("Nije nasao veliko slovo");
        return 0;
    }

    private int containsNumber(String s){

        for(int i = 0; i < s.length(); i++){
            if(Character.isDigit(s.charAt(i))) {

                return 1;
            }
        }
        //Timber.e("Nije nasao cifru");
        return 0;
    }

    private int containsForbidden(String s){
        if(s.contains("`") || s.contains("#") || s.contains("^") || s.contains("|") || s.contains("$") ||
                s.contains("%") || s.contains("&") || s.contains("*") || s.contains("!")) {
            //Timber.e("Nasao zabranjeno");
            return 1;
        }
        //Timber.e("Nije nasao zabranjeno");
        return 0;
    }

    private int isEmail(String s){
        String rest;
        ////Timber.e(s);
        if(s.contains("@")){
            rest = s.substring(0, s.indexOf("@"));
            ////Timber.e(rest);
            if(rest.length() == 0) return 0;
            s = s.substring(s.indexOf("@"));
            ////Timber.e(s);
            if(s.contains(".")){
                rest = s.substring(0, s.indexOf("."));
                ////Timber.e(rest);
                if(rest.length() == 1) return 0;
                rest = s.substring(s.indexOf("."));
                ////Timber.e(rest);
                if(rest.length() == 1) return 0;
                //if(rest.contains(".")) return 0;
                return 1;
            }
        }

        return 0;
    }

    private int checkCredentials(String username, String password, String email){

        String usernameCheck = null;
        String passwordCheck = null;
        String emailCheck = null;

        File path = getActivity().getApplicationContext().getFilesDir();

        try {
            File readFrom = new File(path, CREDENTIALS_FILE_NAME);
            FileInputStream inputStream = new FileInputStream(readFrom);
            byte[] content = new byte[(int)readFrom.length()];
            inputStream.read(content);
            String credentials = new String(content);

            emailCheck = credentials.substring(0, credentials.indexOf("\n"));
            credentials = credentials.substring(credentials.indexOf("\n") + 1);
            usernameCheck = credentials.substring(0, credentials.indexOf("\n"));
            credentials = credentials.substring(credentials.indexOf("\n") + 1);
            passwordCheck = credentials.substring(0);


        } catch (FileNotFoundException e) {
            try {
                FileOutputStream outputStream = new FileOutputStream(new File(path, CREDENTIALS_FILE_NAME));
                String credentials = "raf@raf.rs\nRAF\nRaf123";
                outputStream.write(credentials.getBytes());
                outputStream.close();
                emailCheck = "raf@raf.rs";
                usernameCheck = "RAF";
                password = "Raf123";
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(username.equals(usernameCheck) && password.equals(passwordCheck) && email.equals(emailCheck)) return 1;
        else return 0;
    }
}
