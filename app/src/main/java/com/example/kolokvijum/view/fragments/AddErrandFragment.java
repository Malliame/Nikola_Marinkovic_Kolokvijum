package com.example.kolokvijum.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.kolokvijum.R;
import com.example.kolokvijum.models.Errand;
import com.example.kolokvijum.viewmodels.ErrandRecyclerViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AddErrandFragment extends Fragment {

    private EditText titleEdt, descEdt;
    private TextView lowTv,midTv,highTv, dateTv;
    private boolean low = false, mid = false, high = false;
    private TimePicker tpStart, tpEnd;
    private Button saveBtn, cancleBtn;
    private ErrandRecyclerViewModel errandRecyclerViewModel;

    private LocalDate ld;

    public AddErrandFragment(LocalDate date) {
        super(R.layout.fragment_add_errand);
        ld = date;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }



    private void init(View view){
        initViewModels();
        initView(view);
        initListeners(view);
        initObservers(view);
        initRecycler(view);
    }

    private void initViewModels(){
        errandRecyclerViewModel = new ViewModelProvider(getActivity()).get(ErrandRecyclerViewModel.class);
    }

    private void initView(View view){
        tpStart = view.findViewById(R.id.addStartTimeTp);
        tpEnd = view.findViewById(R.id.addEndTimeTp);
        lowTv = view.findViewById(R.id.addLowTv);
        lowTv.setBackgroundColor(getResources().getColor(R.color.green));
        low = true;
        midTv = view.findViewById(R.id.addMidTv);
        highTv = view.findViewById(R.id.addHightTv);
        dateTv = view.findViewById(R.id.addDateTv);
        titleEdt = view.findViewById(R.id.addTitleEdt);
        descEdt = view.findViewById(R.id.addDescEdt);
        saveBtn = view.findViewById(R.id.createBtn);
        cancleBtn = view.findViewById(R.id.addCancleBtn);

    }

    private void initListeners(View view){
        saveBtn.setOnClickListener(v -> {
            int id = ErrandRecyclerViewModel.getLatestID();
            int level;
            if(low) level = 1;
            else if(mid) level =2;
            else level = 3;
            String title = titleEdt.getText().toString();
            String desc = descEdt.getText().toString();
            LocalDateTime start = LocalDateTime.of(ld, LocalTime.of(tpStart.getHour(),tpStart.getMinute()));
            LocalDateTime end = LocalDateTime.of(ld, LocalTime.of(tpEnd.getHour(),tpEnd.getMinute()));

            if(title.trim().isEmpty()) Toast.makeText(getActivity(), R.string.title_missing_error, Toast.LENGTH_SHORT).show();
            else {

                Errand errand1 = new Errand(id, level, title, desc, start, end);

                errandRecyclerViewModel.addErrand(errand1);


                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.addFragmentFcv, new MainFragment(2));
                transaction.commit();
            }

        });
        cancleBtn.setOnClickListener(v ->{

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.addFragmentFcv, new MainFragment(2));
            transaction.commit();


        });

        lowTv.setOnClickListener(v -> {
            low = true;
            mid = false;
            high = false;
            lowTv.setBackgroundColor(getResources().getColor(R.color.green));
            midTv.setBackgroundColor(getResources().getColor(R.color.white));
            highTv.setBackgroundColor(getResources().getColor(R.color.white));
        });
        midTv.setOnClickListener(v -> {
            low = false;
            mid = true;
            high = false;
            lowTv.setBackgroundColor(getResources().getColor(R.color.white));
            midTv.setBackgroundColor(getResources().getColor(R.color.yellow));
            highTv.setBackgroundColor(getResources().getColor(R.color.white));
        });
        highTv.setOnClickListener(v -> {
            low = false;
            mid = false;
            high = true;
            lowTv.setBackgroundColor(getResources().getColor(R.color.white));
            midTv.setBackgroundColor(getResources().getColor(R.color.white));
            highTv.setBackgroundColor(getResources().getColor(R.color.red));
        });
    }

    private void initObservers(View view){

    }

    private void initRecycler(View view){

    }
}
