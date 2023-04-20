package com.example.kolokvijum.view.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.kolokvijum.R;
import com.example.kolokvijum.view.activities.MainActivity;
import com.example.kolokvijum.view.viewpager.PagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;

import timber.log.Timber;

public class MainFragment extends Fragment {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private BottomNavigationView bottomNavigationView;
    private int createdOn;

    public MainFragment(int i) {
        super(R.layout.fragment_main_app);
        createdOn = i;
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
        initObservers(view);
        initRecycler(view);
        initViewPager(view);
        initNavigation(view);
    }

    private void initView(View view){
        bottomNavigationView = view.findViewById(R.id.bottomNavigation);
    }

    private void initListeners(View view){

    }

    private void initObservers(View view){

    }

    private void initRecycler(View view){

    }

    private void initViewPager(View view) {
        viewPager = view.findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(createdOn-1);
        updateNavigationBarState(createdOn-1);
    }

    private void initNavigation(View view) {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                // setCurrentItem metoda viewPager samo obavesti koji je Item trenutno aktivan i onda metoda getItem u adapteru setuje odredjeni fragment za tu poziciju
                case R.id.navigation_1: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_1, false); updateNavigationBarState(PagerAdapter.FRAGMENT_1); break;
                case R.id.navigation_2: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2, false); updateNavigationBarState(PagerAdapter.FRAGMENT_2); break;
                case R.id.navigation_3: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_3, false); updateNavigationBarState(PagerAdapter.FRAGMENT_3); break;
                //default: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2, false); break;
            }
            return true;
        });
    }

    public void changeNavigation(LocalDate date){
        updateNavigationBarState(PagerAdapter.FRAGMENT_2);
        viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2);
    }

    private void updateNavigationBarState(int actionId){
        Menu menu = bottomNavigationView.getMenu();

        menu.getItem(actionId).setChecked(true);
    }


}
