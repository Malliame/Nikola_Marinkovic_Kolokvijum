package com.example.kolokvijum.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.kolokvijum.R;
import com.example.kolokvijum.models.Errand;
import com.example.kolokvijum.viewmodels.ErrandRecyclerViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

public class DetailErrandFragment extends Fragment {

    private Errand errand;

    private TextView titleTv;
    private TextView dateTv;
    private TextView timeTv;
    private TextView descTv;
    private Button editBtn;
    private Button deleteBtn;

    private Button backBtn;
    private ErrandRecyclerViewModel errandRecyclerViewModel;


    public DetailErrandFragment(Errand errand) {
        super(R.layout.fragment_detail_errand);
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
        initListeners(view);
        initObservers(view);
        initRecycler(view);
    }

    private void initView(View view){
        dateTv = view.findViewById(R.id.detailDateTv);
        dateTv.setText(errand.getDayStart().toLocalDate().toString());
        titleTv = view.findViewById(R.id.detailTitleTv);
        titleTv.setText(errand.getTitle());
        timeTv = view.findViewById(R.id.detailTimeTv);
        timeTv.setText(errand.getDayStart().getHour() + ":" + errand.getDayStart().getMinute() + " - " +
                errand.getDayEnd().getHour() + ":" + errand.getDayEnd().getMinute());
        descTv = view.findViewById(R.id.detailDescTv);
        descTv.setText(errand.getDescr());
        deleteBtn = view.findViewById(R.id.detailDeleteBtn);
        editBtn = view.findViewById(R.id.detailEditBtn);
        backBtn = view.findViewById(R.id.detailBackBtn);

        switch (errand.getLevel()){
            case 1:
            {dateTv.setBackgroundColor(getResources().getColor(R.color.green));
                break;}
            case 2:
            {dateTv.setBackgroundColor(getResources().getColor(R.color.yellow));
                break;}
            default:
            {dateTv.setBackgroundColor(getResources().getColor(R.color.red));
                break;}
        }

    }

    private void initViewModels(){
        errandRecyclerViewModel = new ViewModelProvider(getActivity()).get(ErrandRecyclerViewModel.class);
    }

    private void initListeners(View view){
        deleteBtn.setOnClickListener(v -> {
            Snackbar.make(view.findViewById(R.id.DetailConstrainLayout), R.string.delete_confirm, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.confirm_deletion, view1 -> {
                                errandRecyclerViewModel.removeErrand(errand);
                                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                                transaction.replace(R.id.addFragmentFcv, new MainFragment(2));
                                transaction.commit();
                            }).show();
        });
        editBtn.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.addFragmentFcv, new EditErrandFragment(errand));
            transaction.commit();
        });

        backBtn.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.addFragmentFcv, new MainFragment(2));
            transaction.commit();
        });

    }

    private void initObservers(View view){

    }

    private void initRecycler(View view){

    }



}
