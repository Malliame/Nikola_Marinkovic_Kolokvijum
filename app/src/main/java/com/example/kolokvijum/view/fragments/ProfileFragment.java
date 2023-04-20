package com.example.kolokvijum.view.fragments;

import static com.example.kolokvijum.view.fragments.LoginFragment.CREDENTIALS_FILE_NAME;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProfileFragment extends Fragment {

    private Button logout;
    private Button changePw;

    private TextView username;
    private TextView email;

    private TextView newPasswordTv;
    private TextView newPassword2Tv;

    private EditText changePwEdt;
    private EditText changePwConfirmEdt;

    private String usernameCred = "";
    private String passwordCred = "";
    private String emailCred = "";

    private boolean pwChanging = false;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
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
        initCredentials();
        initView(view);
        initListeners(view);
    }

    private void initView(View view){
        username = view.findViewById(R.id.profileNameTv);
        username.setText(usernameCred);
        email = view.findViewById(R.id.profileEmailTv);
        email.setText(emailCred);
        changePw = view.findViewById(R.id.changePwBtn);
        changePwEdt = view.findViewById(R.id.paswrodChangeEdt);
        changePwConfirmEdt = view.findViewById(R.id.passwordChangeConfirmEdt);
        logout = view.findViewById(R.id.logoutBtn);
        newPasswordTv = view.findViewById(R.id.pwChangeLable);
        newPassword2Tv = view.findViewById(R.id.pwChange2Lable);
    }

    private void initListeners(View view){
        logout.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
            sharedPreferences
                    .edit()
                    .putBoolean(MainActivity.LOGIN_KEY, false)
                    .apply();

            FragmentTransaction transaction = getParentFragment().getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.addFragmentFcv, new LoginFragment());//TODO is not replacin just putting over it
            transaction.commit();
            Toast.makeText(getActivity(), R.string.logut_successful, Toast.LENGTH_LONG).show();
        });

        changePw.setOnClickListener(v ->{
            if(pwChanging){

                String newPw1 = changePwEdt.getText().toString().trim();
                String newPw2 = changePwConfirmEdt.getText().toString().trim();


                if(newPw1.isEmpty() && newPw2.isEmpty()){
                    pwChanging = false;
                    changePwEdt.setVisibility(View.GONE);
                    changePwConfirmEdt.setVisibility(View.GONE);
                    newPasswordTv.setVisibility(View.GONE);
                    newPassword2Tv.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), R.string.change_of_pw_canceled,Toast.LENGTH_SHORT).show();
                }
                else if(newPw1.equals(newPw2)){
                    if(newPw1.length() > 4 && containsCapital(newPw1) == 1 && containsNumber(newPw1) == 1 && containsForbidden(newPw1) == 0){
                        if(newPw1.equals(passwordCred))Toast.makeText(getActivity(), R.string.new_pass_cant_be_same_as_last,Toast.LENGTH_SHORT).show();
                        else {
                            changePwInCredentials(newPw1);
                            pwChanging = false;
                            changePwEdt.setVisibility(View.GONE);
                            changePwConfirmEdt.setVisibility(View.GONE);
                            newPasswordTv.setVisibility(View.GONE);
                            newPassword2Tv.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), R.string.change_of_pw_success,Toast.LENGTH_SHORT).show();
                        }
                    }else Toast.makeText(getActivity(), R.string.password_tooltip,Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getActivity(), R.string.change_of_pw_error,Toast.LENGTH_SHORT).show();
            }
            else{
                pwChanging = true;
                changePwEdt.setVisibility(View.VISIBLE);
                changePwConfirmEdt.setVisibility(View.VISIBLE);
                newPasswordTv.setVisibility(View.VISIBLE);
                newPassword2Tv.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initCredentials(){

            InputStream is = this.getResources().openRawResource(R.raw.credentials);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            try {
                emailCred = bufferedReader.readLine();
                usernameCred = bufferedReader.readLine();
                passwordCred = bufferedReader.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void changePwInCredentials(String newPassowrd){
        File path = getActivity().getApplicationContext().getFilesDir();

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(path, CREDENTIALS_FILE_NAME));
            String credentials = "raf@raf.rs\nRAF\n" + newPassowrd;
            outputStream.write(credentials.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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


}
