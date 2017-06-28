package timelogger.main;

import java.util.*;
import java.time.*;

public class WorkMonth {
    private List<WorkDay> days;
    private YearMonth date;
    private long sumPerMonth;
    private long requiredMinPerMonth;

    public WorkMonth(int year, int month){
        date = YearMonth.of(year, month);
        days = new LinkedList<>();
    }
    
    //Can return negative value
    public long getExtraMinPerMonth(){
        return sumPerMonth - requiredMinPerMonth;
    }
    
    public boolean addWorkDay(WorkDay wd){
        return addWorkDay(wd, false);
    }
    
    public boolean addWorkDay(WorkDay wd, boolean isWeekendEnabled){
        if ((Util.isWeekday(wd.getActualDay()) || isWeekendEnabled) && isSameMonth(wd) && isNewDate(wd)){
            days.add(wd);
            return true;
        }else {
            System.out.println("Not a proper day for this month");
            return false;
        }
    }
    
    public boolean isNewDate(WorkDay dayToCompare){
        for (WorkDay day : days){
            if (dayToCompare.getActualDay().equals(day.getActualDay())) return false;
        }
        return true;
    }
    
    public boolean isSameMonth(WorkDay day){
        if (day.getActualDay().getYear() == date.getYear() &&
                day.getActualDay().getMonth() == date.getMonth()){
            return true;
        }
        else return false;
    }
    
    private void calculateSumPerMonth(){
        sumPerMonth = 0;
        for (WorkDay currentDay : days) {
            sumPerMonth += currentDay.getSumPerDay();
        }
    }
    
    private void calculateRequiredMinPerMonth(){
        requiredMinPerMonth = 0;
        for (WorkDay currentDay : days) {
            requiredMinPerMonth += currentDay.getRequiredMinPerDay();
        }
    }
    
    public List<WorkDay> getDays() {
        return days;
    }

    public YearMonth getDate() {
        return date;
    }

    public long getSumPerMonth() {
        calculateSumPerMonth();
        return sumPerMonth;
    }

    public long getRequiredMinPerMonth() {
        calculateRequiredMinPerMonth();
        return requiredMinPerMonth;
    }
}
