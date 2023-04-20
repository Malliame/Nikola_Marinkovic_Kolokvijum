package com.example.kolokvijum.view.recycler.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kolokvijum.R;
import com.example.kolokvijum.models.Errand;
import com.example.kolokvijum.view.recycler.adapter.ErrandAdapter;

import java.util.function.Consumer;

import timber.log.Timber;

public class ErrandViewHolder extends RecyclerView.ViewHolder {

    private Consumer<Integer> onItemClicked;
    private ErrandAdapter errandAdapter;

    public ErrandViewHolder(@NonNull View itemView,   Consumer<Integer> onItemClicked) {
        super(itemView);
        this.onItemClicked = onItemClicked;

        itemView.setOnClickListener(v -> {
            if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                onItemClicked.accept(getBindingAdapterPosition());
            }
        });
    }

    public void Bind(Errand errand){
        TextView time = itemView.findViewById(R.id.time);
        TextView title = itemView.findViewById(R.id.title);
        TextView priority = itemView.findViewById(R.id.priority);
        TextView edit = itemView.findViewById(R.id.edit);
        TextView delete = itemView.findViewById(R.id.delete);

        switch (errand.getLevel()){
            case 1: priority.setBackgroundResource(R.color.green); break;
            case 2: priority.setBackgroundResource(R.color.yellow); break;
            case 3: priority.setBackgroundResource(R.color.red); break;
        }

        time.setText(errand.getDayStart().getHour() + ":" + errand.getDayStart().getMinute() + " - " +
                errand.getDayEnd().getHour() + ":" + errand.getDayEnd().getMinute());

        title.setText(errand.getTitle());

        edit.setOnClickListener(v->{
            Timber.e("clicked edit");
        });

        delete.setOnClickListener(v->{
            errandAdapter.deleteErrand(errand);
            Timber.e("clicked delete");
        });


    }

    public ErrandViewHolder linkAdapter(ErrandAdapter errandAdapter){
        this.errandAdapter = errandAdapter;
        return  this;
    }

}
