package com.example.kolokvijum.view.recycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.kolokvijum.R;
import com.example.kolokvijum.models.Errand;
import com.example.kolokvijum.models.Week;
import com.example.kolokvijum.view.fragments.ErrandFragment;
import com.example.kolokvijum.view.recycler.viewholders.ErrandViewHolder;

import java.util.function.Consumer;

public class ErrandAdapter extends ListAdapter<Errand, ErrandViewHolder> {

    private final Consumer<Errand> onErrandClicked;
    private ErrandFragment parent;

    public ErrandAdapter(@NonNull DiffUtil.ItemCallback<Errand> diffCallback,ErrandFragment parent, Consumer<Errand> onErrandClicked) {
        super(diffCallback);
        this.parent = parent;
        this.onErrandClicked = onErrandClicked;
    }
    @NonNull
    @Override
    public ErrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.errand_list_item,parent,false);

        ErrandViewHolder errandViewHolder =  new ErrandViewHolder(view, position -> {
            Errand errand = getItem(position);
            onErrandClicked.accept(errand);
        });

        return errandViewHolder.linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ErrandViewHolder holder, int position) {
        Errand errand = getItem(position);
        holder.Bind(errand);
    }

    public void deleteErrand(Errand errand){
        parent.deleteErrand(errand);
    }

}
