package com.example.kolokvijum.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kolokvijum.R;
import com.example.kolokvijum.models.Week;
import com.example.kolokvijum.view.recycler.adapter.WeekAdapter;
import com.example.kolokvijum.view.recycler.differ.WeekDiffItemCallback;
import com.example.kolokvijum.viewmodels.WeekRecyclerViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import timber.log.Timber;

public class CalendarFragment extends Fragment {

    private TextView monthTv;
    private RecyclerView recyclerView;

    private WeekRecyclerViewModel weekRecyclerViewModel;

    private WeekAdapter weekAdapter;

    private boolean lastScrolledDown = true;

    private int weekToLookForMonth = 100;

    public CalendarFragment() {
        super(R.layout.fragment_calendar);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        weekRecyclerViewModel = new ViewModelProvider(getParentFragment().getActivity()).get(WeekRecyclerViewModel.class);

        init(view);
    }

    private void init(View view){
        initView(view);
        initListeners(view);
        initObservers(view);
        initRecycler(view);
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.weekRv);
        monthTv = view.findViewById(R.id.monthTv);
    }

    private void initListeners(View view){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {

                    lastScrolledDown = true;

                } else if (dy < 0) {
                    lastScrolledDown = false;
                } else {
                    System.out.println("No Vertical Scrolled");
                }
            }
        });
        recyclerView.addRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
               // Timber.e("View recycled");
               // Timber.e("" + recyclerView.getChildCount());
                LiveData<List<Week>> weeks = weekRecyclerViewModel.getWeeks();
                if(lastScrolledDown) weekToLookForMonth++;
                else weekToLookForMonth--;

                LocalDate current = weeks.getValue().get(weekToLookForMonth).getThurstday().getDate();

                monthTv.setText(current.getMonth().toString() + "/" + current.getYear());
            }
        });
    }

    private void initObservers(View view){
        weekRecyclerViewModel.getWeeks().observe(this, weeks ->{
            weekAdapter.submitList(weeks);
        });
    }

    private void initRecycler(View view){
        weekAdapter = new WeekAdapter(new WeekDiffItemCallback(),this,week -> {


        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(weekAdapter);


        LiveData<List<Week>> weeks = weekRecyclerViewModel.getWeeks();

        monthTv.setText(LocalDate.now().getMonth().toString() + "/" + LocalDate.now().getYear());

        recyclerView.scrollToPosition(97);

    }

    public void showDay(LocalDate date){
        //Timber.e("3");
        weekRecyclerViewModel.setUsingDate(date);
        ((MainFragment)this.getParentFragment()).changeNavigation(date);
    }

}
