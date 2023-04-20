package com.example.kolokvijum.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.kolokvijum.models.Week;

public class WeekDiffItemCallback extends DiffUtil.ItemCallback<Week> {


    @Override
    public boolean areItemsTheSame(@NonNull Week oldItem, @NonNull Week newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Week oldItem, @NonNull Week newItem) {

        return oldItem.getMonday().equals(newItem.getMonday()) &&
                oldItem.getTuesday().equals(newItem.getTuesday()) &&
                oldItem.getWednesday().equals(newItem.getWednesday()) &&
                oldItem.getThurstday().equals(newItem.getThurstday()) &&
                oldItem.getFriday().equals(newItem.getFriday()) &&
                oldItem.getSaturday().equals(newItem.getSaturday()) &&
                oldItem.getSunday().equals(newItem.getSunday());
    }
}
