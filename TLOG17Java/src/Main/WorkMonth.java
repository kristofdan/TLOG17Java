package Main;

import java.util.*;
import java.time.*;

public class WorkMonth {
    private List<WorkDay> days;
    private YearMonth date;
    private long sumPerMonth;
    private long requiredMinPerMonth;

//Where do we calculate the two other fields?
    WorkMonth(int year, int month){
        date = YearMonth.of(year, month);
    }

    public List<WorkDay> getDays() {
        return days;
    }

    public YearMonth getDate() {
        return date;
    }

    public long getSumPerMonth() {
        return sumPerMonth;
    }

    public long getRequiredMinPerMonth() {
        return requiredMinPerMonth;
    }
    
//Can return negative value
    public long getExtraMinPerMonth(){
        return requiredMinPerMonth - sumPerMonth;
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
    
    public void addWorkDay(WorkDay wd, boolean isWeekendEnabled){
        if ((wd.isWeekday() || isWeekendEnabled) && isSameMonth(wd) && isNewDate(wd)){
            days.add(wd);
        }
    }
    
    public void addWorkDay(WorkDay wd){
        addWorkDay(wd, false);
    }
}
