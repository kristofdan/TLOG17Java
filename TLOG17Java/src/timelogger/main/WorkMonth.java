package timelogger.main;

import java.util.*;
import java.time.*;
import timelogger.exceptions.NotNewDateException;
import timelogger.exceptions.NotTheSameMonthException;
import timelogger.exceptions.WeekendNotEnabledException;

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
        calculateSumPerMonth();
        calculateRequiredMinPerMonth();
        return sumPerMonth - requiredMinPerMonth;
    }
    
    public void addWorkDay(WorkDay wd){
        addWorkDay(wd, false);
    }
    
    public void addWorkDay(WorkDay wd, boolean isWeekendEnabled){
        if ((Util.isWeekday(wd.getActualDay()) || isWeekendEnabled) && isSameMonth(wd) && isNewDate(wd)){
            days.add(wd);
        }else {
            throwAppropriateException(wd,isWeekendEnabled);
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
    
    private void throwAppropriateException(WorkDay wd,boolean isWeekendEnabled){
       if (!Util.isWeekday(wd.getActualDay()) && !isWeekendEnabled){
           throw new WeekendNotEnabledException();
       }else if(!isSameMonth(wd)){
           throw new NotTheSameMonthException();
       }else {
           throw new NotNewDateException();
       }
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
