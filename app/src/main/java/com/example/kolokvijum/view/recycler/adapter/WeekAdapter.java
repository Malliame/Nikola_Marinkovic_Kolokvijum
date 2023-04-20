package com.example.kolokvijum.view.recycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kolokvijum.R;
import com.example.kolokvijum.models.Week;
import com.example.kolokvijum.view.fragments.CalendarFragment;
import com.example.kolokvijum.view.recycler.viewholders.WeekViewHolder;

import java.time.LocalDate;
import java.util.function.Consumer;

import timber.log.Timber;

public class WeekAdapter extends ListAdapter<Week, WeekViewHolder> {

    private final Consumer<Week> onWeekClicked;
    private CalendarFragment parentFragment;

    public WeekAdapter(@NonNull DiffUtil.ItemCallback<Week> diffCallback, CalendarFragment fragment, Consumer<Week> onWeekClicked) {
        super(diffCallback);
        this.parentFragment = fragment;
        this.onWeekClicked = onWeekClicked;
    }

    @NonNull
    @Override
    public WeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_list_item,parent,false);


        WeekViewHolder weekViewHolder = new WeekViewHolder(view, position -> {
            Week week = getItem(position);
            onWeekClicked.accept(week);
        });
        return weekViewHolder.linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekViewHolder holder, int position) {
        Week week = getItem(position);
        holder.Bind(week);
    }

    public void goToDailyPlan(LocalDate date){
        //Timber.e("2");
        ((CalendarFragment)parentFragment).showDay(date);
    }


}
