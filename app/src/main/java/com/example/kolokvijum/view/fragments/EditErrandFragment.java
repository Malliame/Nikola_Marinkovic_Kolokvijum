package com.example.kolokvijum.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.kolokvijum.R;
import com.example.kolokvijum.models.Errand;
import com.example.kolokvijum.viewmodels.ErrandRecyclerViewModel;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class EditErrandFragment extends Fragment {


    private EditText titleEdt, descEdt;
    private TextView lowTv,midTv,highTv, dateTv;
    private boolean low = false, mid = false, high = false;
    private TimePicker tpStart, tpEnd;
    private Button saveBtn, cancleBtn;


    private ErrandRecyclerViewModel errandRecyclerViewModel;
    private Errand errand;

    public EditErrandFragment(Errand errand) {
        super(R.layout.fragment_edit_errand);
        this.errand = errand;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }



    private void init(View view){
        initViewModels();
        initView(view);
        initValues();
        initListeners(view);
        initRecycler(view);
    }


    private void initViewModels(){
        errandRecyclerViewModel = new ViewModelProvider(getActivity()).get(ErrandRecyclerViewModel.class);
    }

    private void initView(View view){
        tpStart = view.findViewById(R.id.editStartTimeTp);
        tpEnd = view.findViewById(R.id.editEndTimeTp);
        lowTv = view.findViewById(R.id.editLowTv);
        midTv = view.findViewById(R.id.editMidTv);
        highTv = view.findViewById(R.id.editHightTv);
        dateTv = view.findViewById(R.id.editDateTv);
        titleEdt = view.findViewById(R.id.editTitleEdt);
        descEdt = view.findViewById(R.id.editDescEdt);
        saveBtn = view.findViewById(R.id.saveBtn);
        cancleBtn = view.findViewById(R.id.editCancleBtn);
    }

    private void initValues(){
        dateTv.setText(errand.getDayStart().toLocalDate().toString());
        titleEdt.setText(errand.getTitle());
        descEdt.setText(errand.getDescr());
        tpStart.setHour(errand.getDayStart().getHour());
        tpStart.setMinute(errand.getDayStart().getMinute());
        tpEnd.setHour(errand.getDayEnd().getHour());
        tpEnd.setMinute(errand.getDayEnd().getMinute());
        switch (errand.getLevel()){
            case 1:
            {lowTv.setBackgroundColor(getResources().getColor(R.color.green));
                break;}
            case 2:
            {midTv.setBackgroundColor(getResources().getColor(R.color.yellow));
                break;}
            default:
            {highTv.setBackgroundColor(getResources().getColor(R.color.red));
                break;}
        }
    }

    private void initListeners(View view){
            saveBtn.setOnClickListener(v -> {
                    int id = errand.getId();
                    int level;
                    if(low) level = 1;
                    else if(mid) level =2;
                    else level = 3;
                    String title = titleEdt.getText().toString();
                    String desc = descEdt.getText().toString();
                    LocalDateTime start = LocalDateTime.of(errand.getDayStart().toLocalDate(), LocalTime.of(tpStart.getHour(),tpStart.getMinute()));
                    LocalDateTime end = LocalDateTime.of(errand.getDayStart().toLocalDate(), LocalTime.of(tpEnd.getHour(),tpEnd.getMinute()));

                    Errand errand1 = new Errand(id, level, title,desc,start,end);

                    errandRecyclerViewModel.updateErrands(errand1);


                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.addFragmentFcv, new DetailErrandFragment(errand1));
                transaction.commit();

            });
            cancleBtn.setOnClickListener(v ->{

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.addFragmentFcv, new DetailErrandFragment(errand));
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

    private void initRecycler(View view){

    }



}
