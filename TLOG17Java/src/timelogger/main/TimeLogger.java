package timelogger.main;

import java.util.*;
import timelogger.exceptions.NotNewMonthException;

/**
 * A timelogger is represented by the WorkMonth's within it.
 * 
 * @author Kristóf Dan
 */

@lombok.Getter

public class TimeLogger{
    private List<WorkMonth> months;

    public TimeLogger() {
        months = new LinkedList<>();
    }
    
    public void addMonth(WorkMonth month)
        throws Exception
    {
        if (isNewMonth(month)) months.add(month);
        else throw new NotNewMonthException("Error: cannot add this month to logger, a month with this date already exists");
    }
    
    public boolean isNewMonth(WorkMonth monthToCompare){
        for(WorkMonth month : months){
            if (monthToCompare.getDate().equals(month.getDate())){
                return false;
            }
        }
        return true;
    }
}