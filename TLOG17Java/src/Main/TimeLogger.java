package Main;

import java.util.*;

public class TimeLogger{
    private List<WorkMonth> months;

    public TimeLogger() {
        months = new LinkedList<>();
    }
    
    public void addMonth(WorkMonth month){
        if (isNewMonth(month)) months.add(month);
        else System.out.println("This month already exists");
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