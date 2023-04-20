package com.example.kolokvijum.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kolokvijum.models.Day;
import com.example.kolokvijum.models.Errand;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.Era;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import timber.log.Timber;

public class ErrandRecyclerViewModel extends ViewModel {

    MutableLiveData<List<Errand>> errands = new MutableLiveData<>();
    List<Errand> errandList;
    private static int id = 0;


    public ErrandRecyclerViewModel() {
        errandList = new ArrayList<Errand>();
        errands.setValue(errandList);
    }

    public  void addErrand(Errand errand){
        errandList.add(errand);
        errands.setValue(errandList);
    }

    public static int getLatestID(){
        return id++;
    }

    public  void removeErrand(Errand errand){
        if(errandList.contains(errand))errandList.remove(errand);
        errands.setValue(errandList);
    }

    public boolean isEmpty(){
        if(errandList.isEmpty()) return  true;
        return  false;
    }

    public void updateErrands(Errand errand){

        List<Errand> filterList = new ArrayList<>(errandList);

        for(Errand e: filterList){
            if(e.getId() == errand.getId()){
                errandList.remove(e);
                errandList.add(errand);
                continue;
            }
        }

        List<Errand> listToSubmit = new ArrayList<>(errandList);

        errands.setValue(listToSubmit);
    }

    public void filterByDate(LocalDate date, int level, String name, boolean isBefore){
        //Timber.e(date.toString());
        List<Errand> filteredList3 = new ArrayList<Errand>();
        List<Errand> filteredList2 = new ArrayList<Errand>();
        List<Errand> filteredList1 = new ArrayList<Errand>();
        List<Errand> filteredList = new ArrayList<Errand>();
        for(Errand e : errandList){
            //Timber.e(e.getDayStart().toLocalDate().toString());
            if(e.getDayStart().toLocalDate().isEqual(date)) filteredList3.add(e);
        }
        if(level !=4){
            for(Errand e : filteredList3){
                //Timber.e(e.getDayStart().toLocalDate().toString());
                if(e.getLevel() == level) filteredList2.add(e);
            }
        }
        else filteredList2 = filteredList3;
        if(!name.trim().equals("")){
            for(Errand e : filteredList2){
                //Timber.e(e.getDayStart().toLocalDate().toString());
                if(e.getTitle().toLowerCase().startsWith(name.toLowerCase())) filteredList1.add(e);
            }
        }
        else filteredList1 = filteredList2;

        if (isBefore) {
            for(Errand e : filteredList1){
                //Timber.e(e.getDayStart().toLocalDate().toString());
                if(!e.getDayEnd().isBefore(LocalDateTime.now())) filteredList.add(e);
            }
        }
        else filteredList = filteredList1;




        errands.setValue(filteredList);
    }

    public LiveData<List<Errand>> getErrands(){
        return errands;
    }

}
