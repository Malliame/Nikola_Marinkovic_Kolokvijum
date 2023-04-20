package com.example.kolokvijum.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.kolokvijum.models.Errand;

public class ErrandDiffItemCallback extends DiffUtil.ItemCallback<Errand> {
    @Override
    public boolean areItemsTheSame(@NonNull Errand oldItem, @NonNull Errand newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Errand oldItem, @NonNull Errand newItem) {
        return oldItem.equals(newItem);
    }
}
