package com.example.kolokvijum.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kolokvijum.models.Day;
import com.example.kolokvijum.models.Errand;
import com.example.kolokvijum.models.Week;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import timber.log.Timber;

public class WeekRecyclerViewModel extends ViewModel {

    public static int errandID = 0;
    public static int counter = 250;
    private final MutableLiveData<List<Week>> weeks = new MutableLiveData<>();
    private MutableLiveData<LocalDate> usingDate = new MutableLiveData<>();
    private ArrayList<Week> weekList = new ArrayList<>();

    public WeekRecyclerViewModel() {
        LocalDateTime ld = LocalDateTime.now();
        LocalDateTime start = ld.minusDays(700);
        LocalDateTime end = ld.plusDays(1050);

        Day mon = null;
        Day tue = null;
        Day wed = null;
        Day thu = null;
        Day fri = null;
        Day sat = null;
        Day sun = null;

        int id = 0;

        //Timber.e("Usao");

        while(!start.equals(end)){

            List<Errand> errands = getRandomErrands(start);

            switch (start.getDayOfWeek()){
                case MONDAY:mon = new Day(getHightestLevel(errands),errands,start.toLocalDate()); break;
                case TUESDAY:tue = new Day(getHightestLevel(errands),errands,start.toLocalDate());break;
                case WEDNESDAY:wed = new Day(getHightestLevel(errands),errands,start.toLocalDate());break;
                case THURSDAY:thu = new Day(getHightestLevel(errands),errands,start.toLocalDate());break;
                case FRIDAY:fri  = new Day(getHightestLevel(errands),errands,start.toLocalDate());break;
                case SATURDAY:sat = new Day(getHightestLevel(errands),errands,start.toLocalDate());break;
                default:sun = new Day(getHightestLevel(errands),errands,start.toLocalDate());
                            if(mon!=null && tue!=null && wed!=null && thu!=null && fri!=null && sat!=null && sun!=null)
                                                            weekList.add(new Week(id++,mon,tue,wed,thu,fri,sat,sun));
                            mon=tue=wed=thu=fri=sat=sun=null;
                            //Timber.e("Dodao nedelju" + id);
            }

            start = start.plusDays(1);
        }

        ArrayList<Week> listToSubmit = new ArrayList<>(weekList);

        //Timber.e(weekList.toString());

        weeks.setValue(listToSubmit);

    }

    public LiveData<List<Week>> getWeeks(){
        return weeks;
    }

    public List<Week> removeErrandFromDate(Errand errand){

        LocalDate d = errand.getDayEnd().toLocalDate();

        List<Week> weekList1 = new ArrayList<Week>();

        for(Week week : weekList){

            Day mon = week.getMonday();

            Day tue = week.getTuesday();

            Day wed = week.getWednesday();

            Day thu = week.getThurstday();

            Day fri = week.getFriday();

            Day sat = week.getSaturday();

            Day sun = week.getSunday();

            if(week.getMonday().getDate().isEqual(d)){
                Timber.e("Mon" + d);
                List<Errand> el = week.getMonday().getErrandList();
                if(el.contains(errand))el.remove(errand);
                Day day;
                if(el.isEmpty()) day = new Day(4, el, d);
                else day = new Day(getHightestLevel(el), el, d);
                mon = day;
            };
            if(week.getTuesday().getDate().isEqual(d)){
                Timber.e("Tue" + d);
                List<Errand> el = week.getTuesday().getErrandList();
                if(el.contains(errand))el.remove(errand);
                Day day;
                if(el.isEmpty()) day = new Day(4, el, d);
                else day = new Day(getHightestLevel(el), el, d);
                tue = day;
            };
            if(week.getWednesday().getDate().isEqual(d)){
                Timber.e("Wed" + d);
                List<Errand> el = week.getWednesday().getErrandList();
                if(el.contains(errand))el.remove(errand);
                Day day;
                if(el.isEmpty()) day = new Day(4, el, d);
                else day = new Day(getHightestLevel(el), el, d);
                wed = day;
            };
            if(week.getThurstday().getDate().isEqual(d)){
                Timber.e("Thu" + d);
                List<Errand> el = week.getThurstday().getErrandList();
                if(el.contains(errand))el.remove(errand);
                Day day;
                if(el.isEmpty()) day = new Day(4, el, d);
                else day = new Day(getHightestLevel(el), el, d);
                thu = day;
            };
            if(week.getFriday().getDate().isEqual(d)) {
                Timber.e("Fri" + d);
                List<Errand> el = week.getFriday().getErrandList();
                if(el.contains(errand))el.remove(errand);
                Day day;
                if(el.isEmpty()) day = new Day(4, el, d);
                else day = new Day(getHightestLevel(el), el, d);
                fri = day;
            }
            if(week.getSaturday().getDate().isEqual(d)){
                Timber.e("Sat" + d);
                List<Errand> el = week.getSaturday().getErrandList();
                if(el.contains(errand))el.remove(errand);
                Day day;
                if(el.isEmpty()) day = new Day(4, el, d);
                else day = new Day(getHightestLevel(el), el, d);
                sat = day;
            };
            if(week.getSunday().getDate().isEqual(d)){
                Timber.e("Sun" + d);
                List<Errand> el = week.getSunday().getErrandList();
                if(el.contains(errand))el.remove(errand);
                Day day;
                if(el.isEmpty()) day = new Day(4, el, d);
                else day = new Day(getHightestLevel(el), el, d);
                sun = day;

            };

            weekList1.add(new Week(week.getId(), mon, tue, wed, thu, fri, sat, sun));
        }

        weekList = new ArrayList<>(weekList1);

        List<Week> listToSubmit = new ArrayList<>(weekList1);

        Timber.e("Got here");
        //weeks.setValue(listToSubmit);
        Timber.e("Here as well");
        return weekList;
    }

    private List<Errand> getRandomErrands(LocalDateTime date){

        ArrayList<Errand> errands = new ArrayList<>();

        Random random = new Random();

        int i = random.nextInt(3);

        for(int j = 0; j < i; j++){
            errands.add(getRandomErrand(date));
        }

        return errands;
    }

    private int getHightestLevel(List<Errand> errands){
        if(errands.isEmpty()) return 4;
        int i = 0;

        for(Errand e:errands){
            if(e.getLevel() > i) i = e.getLevel();
        }

        return  i;
    }

    private Errand getRandomErrand(LocalDateTime date){

        Random random = new Random();

        String name, desc;
        //Timber.e(date.toLocalDate().toString());

        int level = random.nextInt(3) + 1;
        switch (level){
            case 1: name = "Random Errand with lowest importance"; desc = "Random Errand with lowerst importance description";break;
            case 2: name = "Random Errand with medium importance"; desc = "Random Errand with medium importance description";break;
            default: name = "Random Errand with highest importance"; desc = "Random Errand with highest importance description"; break;
        }

        LocalTime timeOfErrand = LocalTime.of(11,0);

        LocalDateTime start = LocalDateTime.of(date.toLocalDate(), timeOfErrand);
        LocalDateTime end = LocalDateTime.of(date.toLocalDate(), timeOfErrand.plusHours(2));

        Errand errand = new Errand(ErrandRecyclerViewModel.getLatestID(), level, name, desc, start, end);

        //Timber.e(errand.getId() + " start:" + errand.getDayStart().toLocalDate() + " end:" + errand.getDayStart().toLocalDate());

        return errand;
    }

    public LiveData<LocalDate> getUsingDate() {
        return usingDate;
    }

    public void setUsingDate(LocalDate localDate) {
        this.usingDate.setValue(localDate);
    }
}
