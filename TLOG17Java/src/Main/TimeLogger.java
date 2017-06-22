package Main;

import java.util.*;

class TimeLogger{
    private List<WorkMonth> months;

    public List<WorkMonth> getMonths() {
        return months;
    }
    
    public boolean isNewMonth(WorkMonth monthToCompare){
        for(WorkMonth month : months){
            if (monthToCompare.getDate().equals(month.getDate())){
                return false;
            }
        }
        return true;
    }
    
    public void addMonth(WorkMonth month){
        if (isNewMonth(month)) months.add(month);
    }
}