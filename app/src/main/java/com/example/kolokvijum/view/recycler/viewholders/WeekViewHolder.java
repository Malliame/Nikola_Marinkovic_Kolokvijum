package com.example.kolokvijum.view.recycler.viewholders;

import android.content.Context;
import android.content.ContextParams;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kolokvijum.R;
import com.example.kolokvijum.models.Week;
import com.example.kolokvijum.view.recycler.adapter.WeekAdapter;

import java.util.function.Consumer;

import timber.log.Timber;

public class WeekViewHolder extends RecyclerView.ViewHolder {


    private Consumer<Integer> onItemClicked;

    private WeekAdapter weekAdapter;

    public WeekViewHolder(@NonNull View itemView,   Consumer<Integer> onItemClicked) {
        super(itemView);
        this.onItemClicked = onItemClicked;

        itemView.setOnClickListener(v -> {
            if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                onItemClicked.accept(getBindingAdapterPosition());
                }
            });
    }

    public void Bind(Week week){

        //Timber.e("Showing week" + week.getId());

        TextView mon = itemView.findViewById(R.id.mondayItemTv);
        TextView tue = itemView.findViewById(R.id.tuesdayItemTv);
        TextView wed = itemView.findViewById(R.id.wednesdayItemTv);
        TextView thu = itemView.findViewById(R.id.thurstdayItemTv);
        TextView fri = itemView.findViewById(R.id.fridayItemTv);
        TextView sat = itemView.findViewById(R.id.saturdayItemTv);
        TextView sun = itemView.findViewById(R.id.sundayItemTv);

        if(week.getMonday() != null){
            mon.setText(week.getMonday().getDate().getDayOfMonth()+"");
            switch (week.getMonday().getMaxLevel()){
                case 1:mon.setBackgroundResource(R.color.green);break;
                case 2:mon.setBackgroundResource(R.color.yellow);break;
                case 3:mon.setBackgroundResource(R.color.red);break;
                default:mon.setBackgroundResource(R.color.white);break;
            }
            mon.setOnClickListener(view -> {
                //Timber.e(week.getMonday().getDate().toString());
                //Timber.e("1");
                weekAdapter.goToDailyPlan(week.getMonday().getDate());
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }

            });
        }
        else mon.setText("OUT");
        if(week.getTuesday() != null){
            tue.setText(week.getTuesday().getDate().getDayOfMonth()+"");
            switch (week.getTuesday().getMaxLevel()){
                case 1:tue.setBackgroundResource(R.color.green);break;
                case 2:tue.setBackgroundResource(R.color.yellow);break;
                case 3:tue.setBackgroundResource(R.color.red);break;
                default:tue.setBackgroundResource(R.color.white);break;
            }
            tue.setOnClickListener(view -> {
                weekAdapter.goToDailyPlan(week.getTuesday().getDate());
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }
        else tue.setText("OUT");
        if(week.getWednesday() != null){
            wed.setText(week.getWednesday().getDate().getDayOfMonth()+"");
            switch (week.getWednesday().getMaxLevel()){
                case 1:wed.setBackgroundResource(R.color.green);break;
                case 2:wed.setBackgroundResource(R.color.yellow);break;
                case 3:wed.setBackgroundResource(R.color.red);break;
                default:wed.setBackgroundResource(R.color.white);break;
            }
            wed.setOnClickListener(view -> {
                weekAdapter.goToDailyPlan(week.getWednesday().getDate());
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }
        else wed.setText("OUT");
        if(week.getThurstday() != null){
            thu.setText(week.getThurstday().getDate().getDayOfMonth()+"");
            switch (week.getThurstday().getMaxLevel()){
                case 1:thu.setBackgroundResource(R.color.green);break;
                case 2:thu.setBackgroundResource(R.color.yellow);break;
                case 3:thu.setBackgroundResource(R.color.red);break;
                default:thu.setBackgroundResource(R.color.white);break;
            }
            thu.setOnClickListener(view -> {
                weekAdapter.goToDailyPlan(week.getThurstday().getDate());
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }
        else thu.setText("OUT");
        if(week.getFriday() != null){
            fri.setText(week.getFriday().getDate().getDayOfMonth()+"");
            switch (week.getFriday().getMaxLevel()){
                case 1:fri.setBackgroundResource(R.color.green);break;
                case 2:fri.setBackgroundResource(R.color.yellow);break;
                case 3:fri.setBackgroundResource(R.color.red);break;
                default:fri.setBackgroundResource(R.color.white);break;
            }
            fri.setOnClickListener(view -> {
                weekAdapter.goToDailyPlan(week.getFriday().getDate());
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }
        else fri.setText("OUT");
        if(week.getSaturday() != null){
            sat.setText(week.getSaturday().getDate().getDayOfMonth()+"");
            switch (week.getSaturday().getMaxLevel()){
                case 1:sat.setBackgroundResource(R.color.green);break;
                case 2:sat.setBackgroundResource(R.color.yellow);break;
                case 3:sat.setBackgroundResource(R.color.red);break;
                default:sat.setBackgroundResource(R.color.white);break;
            }
            sat.setOnClickListener(view -> {
                weekAdapter.goToDailyPlan(week.getSaturday().getDate());
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }
        else sat.setText("OUT");
        if(week.getSunday() != null){
            sun.setText(week.getSunday().getDate().getDayOfMonth()+"");
            switch (week.getSunday().getMaxLevel()){
                case 1:sun.setBackgroundResource(R.color.green);break;
                case 2:sun.setBackgroundResource(R.color.yellow);break;
                case 3:sun.setBackgroundResource(R.color.red);break;
                default:sun.setBackgroundResource(R.color.white);break;
            }
            sun.setOnClickListener(view -> {
                weekAdapter.goToDailyPlan(week.getSunday().getDate());
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }
        else sun.setText("OUT");


    }

    public WeekViewHolder linkAdapter(WeekAdapter weekAdapter){
        this.weekAdapter = weekAdapter;
        return  this;
    }

}
