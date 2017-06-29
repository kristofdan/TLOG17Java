package timelogger.main;

import java.util.*;
import timelogger.exceptions.NotNewMonthException;

public class TimeLogger{
    private List<WorkMonth> months;

    public TimeLogger() {
        months = new LinkedList<>();
    }
    
    public void addMonth(WorkMonth month){
        if (isNewMonth(month)) months.add(month);
        else throw new NotNewMonthException();
    }
    
    public boolean isNewMonth(WorkMonth monthToCompare){
        for(WorkMonth month : months){
            if (monthToCompare.getDate().equals(month.getDate())){
                return false;
            }
        }
        return true;
    }
    
    public List<WorkMonth> getMonths() {
        return months;
    }
}