package com.example.kolokvijum.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kolokvijum.R;
import com.example.kolokvijum.models.Day;
import com.example.kolokvijum.models.Errand;
import com.example.kolokvijum.models.Week;
import com.example.kolokvijum.view.recycler.adapter.ErrandAdapter;
import com.example.kolokvijum.view.recycler.adapter.WeekAdapter;
import com.example.kolokvijum.view.recycler.differ.ErrandDiffItemCallback;
import com.example.kolokvijum.view.recycler.viewholders.WeekViewHolder;
import com.example.kolokvijum.view.viewpager.PagerAdapter;
import com.example.kolokvijum.viewmodels.ErrandRecyclerViewModel;
import com.example.kolokvijum.viewmodels.WeekRecyclerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.ArrayList;

import timber.log.Timber;

public class ErrandFragment extends Fragment {
    private TextView dateTv;

    private TextView low;
    private TextView mid;
    private TextView high;

    private ConstraintLayout constraintLayout;

    boolean lowChecked = false;
    boolean midChecked = false;
    boolean highChecked = false;
    private FloatingActionButton fab;
    private EditText filterEdt;
    private CheckBox checkBox;
    private RecyclerView recyclerView;

    private ErrandAdapter errandAdapter;

    private WeekRecyclerViewModel weekRecyclerViewModel;
    private ErrandRecyclerViewModel errandRecyclerViewModel;
    private LocalDate dateOfErrands = LocalDate.now();

    private String filterString = "";

    private boolean isBefore = false;
    private boolean initialized = false;

    public ErrandFragment() {
        super(R.layout.fragment_daily_plan);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.e("OnViewCreated Errand");
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
        weekRecyclerViewModel = new ViewModelProvider(getParentFragment().getActivity()).get(WeekRecyclerViewModel.class);
        errandRecyclerViewModel = new ViewModelProvider(getParentFragment().getActivity()).get(ErrandRecyclerViewModel.class);
    }

    private void initView(View view){
        constraintLayout = view.findViewById(R.id.dailyPlanLayout);
        dateTv = view.findViewById(R.id.dateTv);
        low = view.findViewById(R.id.lowPriority);
        mid = view.findViewById(R.id.midPriority);
        high = view.findViewById(R.id.highPriority);
        checkBox = view.findViewById(R.id.checkbox);
        checkBox.setChecked(isBefore);
        filterEdt = view.findViewById(R.id.filter);
        fab = view.findViewById(R.id.FAB);
        recyclerView = view.findViewById(R.id.errandRV);
    }

    private void initListeners(View view){

        filterEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    filterString = editable.toString();
                if(lowChecked)
                    errandRecyclerViewModel.filterByDate(dateOfErrands, 1, filterString,isBefore);
                else if(midChecked)
                    errandRecyclerViewModel.filterByDate(dateOfErrands, 2, filterString,isBefore);
                else if(highChecked)
                    errandRecyclerViewModel.filterByDate(dateOfErrands, 3, filterString,isBefore);
                else
                    errandRecyclerViewModel.filterByDate(dateOfErrands, 4, filterString,isBefore);

            }
        });

        fab.setOnClickListener(v->{
            FragmentTransaction transaction = getParentFragment().getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.addFragmentFcv, new AddErrandFragment(dateOfErrands));
            transaction.addToBackStack(null);
            transaction.commit();
        });
        low.setOnClickListener(v->{
            Timber.e("low " + lowChecked);
            if(!lowChecked){
                lowChecked = true;
                highChecked = false;
                midChecked = false;
                errandRecyclerViewModel.filterByDate(dateOfErrands, 1, filterString, isBefore);
            }
            else{
                lowChecked = false;
                highChecked = false;
                midChecked = false;
                errandRecyclerViewModel.filterByDate(dateOfErrands, 4, filterString, isBefore);
            }

        });
        mid.setOnClickListener(v->{
            Timber.e("mid " + midChecked);
            if(!midChecked){
                lowChecked = false;
                highChecked = false;
                midChecked = true;
                errandRecyclerViewModel.filterByDate(dateOfErrands, 2, filterString,isBefore);
            }
            else{
                lowChecked = false;
                highChecked = false;
                midChecked = false;
                errandRecyclerViewModel.filterByDate(dateOfErrands, 4, filterString,isBefore);
            }
        });
        high.setOnClickListener(v->{
            Timber.e("high " + highChecked);
            if(!highChecked){
                lowChecked = false;
                highChecked = true;
                midChecked = false;
                errandRecyclerViewModel.filterByDate(dateOfErrands, 3, filterString,isBefore);
            }
            else{
                lowChecked = false;
                highChecked = false;
                midChecked = false;
                errandRecyclerViewModel.filterByDate(dateOfErrands, 4, filterString,isBefore);
            }
        });
        checkBox.setOnClickListener(v->{
            isBefore = !isBefore;
            if(lowChecked)
                errandRecyclerViewModel.filterByDate(dateOfErrands, 1, filterString,isBefore);
            else if(midChecked)
                errandRecyclerViewModel.filterByDate(dateOfErrands, 2, filterString,isBefore);
            else if(highChecked)
                errandRecyclerViewModel.filterByDate(dateOfErrands, 3, filterString,isBefore);
            else
                errandRecyclerViewModel.filterByDate(dateOfErrands, 4, filterString,isBefore);

        });

    }

    private void initObservers(View view){
        weekRecyclerViewModel.getUsingDate().observe(getViewLifecycleOwner(), (localDate) -> {
            //Timber.e("Hey");
            dateOfErrands = localDate;
            dateTv.setText(dateOfErrands.toString());
            errandRecyclerViewModel.filterByDate(dateOfErrands, 4, filterString,isBefore);
        });
        weekRecyclerViewModel.getWeeks().observe(this, weeks -> {
            if(errandRecyclerViewModel.isEmpty()) {
                for (Week week : weeks) {
                    for (Errand errand : week.getMonday().getErrandList())
                        errandRecyclerViewModel.addErrand(errand);
                    for (Errand errand : week.getTuesday().getErrandList())
                        errandRecyclerViewModel.addErrand(errand);
                    for (Errand errand : week.getWednesday().getErrandList())
                        errandRecyclerViewModel.addErrand(errand);
                    for (Errand errand : week.getThurstday().getErrandList())
                        errandRecyclerViewModel.addErrand(errand);
                    for (Errand errand : week.getFriday().getErrandList())
                        errandRecyclerViewModel.addErrand(errand);
                    for (Errand errand : week.getSaturday().getErrandList())
                        errandRecyclerViewModel.addErrand(errand);
                    for (Errand errand : week.getSunday().getErrandList())
                        errandRecyclerViewModel.addErrand(errand);
                }
                initialized = true;
            }
        });

        errandRecyclerViewModel.getErrands().observe(this, errands -> {
            errandAdapter.submitList(errands);
        });
    }


    private void initRecycler(View view){

        errandAdapter = new ErrandAdapter(new ErrandDiffItemCallback(), this, errand ->{
            FragmentTransaction transaction = getParentFragment().getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.addFragmentFcv, new DetailErrandFragment(errand));
            transaction.commit();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(errandAdapter);

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //Timber.e("Ovde sam");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        lowChecked = midChecked = highChecked = false;
    }

    public void deleteErrand(Errand errand){

        Snackbar.make(constraintLayout, R.string.delete_confirm, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.confirm_deletion, view -> {
                            errandRecyclerViewModel.removeErrand(errand);

                            if(lowChecked)
                                errandRecyclerViewModel.filterByDate(dateOfErrands, 1, filterString,isBefore);
                            else if(midChecked)
                                errandRecyclerViewModel.filterByDate(dateOfErrands, 2, filterString,isBefore);
                            else if(highChecked)
                                errandRecyclerViewModel.filterByDate(dateOfErrands, 3, filterString,isBefore);
                            else
                                errandRecyclerViewModel.filterByDate(dateOfErrands, 4, filterString,isBefore);

                            weekRecyclerViewModel.removeErrandFromDate(errand);})//TODO NOTWORKING
                        .show();



    }



}


