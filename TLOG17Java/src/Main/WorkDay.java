package Main;

import java.util.*;
import java.time.*;

public class WorkDay {
    private List<Task> tasks;
    private LocalDate actualDay;
    private long requiredMinPerDay;
    private long sumPerDay;

//5 constructors for default arguments, default requiredMinPerDay: 450, actualDay: today
    
    public WorkDay(long requiredMinPerDay, int year, int month, int day) {
        this.requiredMinPerDay = requiredMinPerDay;
        actualDay = LocalDate.of(year, month, day);
    }
    
    public WorkDay(long requiredMinPerDay, int year, int month) {
        this(requiredMinPerDay, year, month,
                LocalDate.now().getDayOfMonth());
    }
    
    public WorkDay(long requiredMinPerDay, int year) {
        this(requiredMinPerDay, year,
                LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }
    
    public WorkDay(long requiredMinPerDay) {
        this(requiredMinPerDay, LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }
    
    public WorkDay() {
        this(450, LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }

    public LocalDate getActualDay() {
        return actualDay;
    }

    public long getRequiredMinPerDay() {
        return requiredMinPerDay;
    }

    public long getSumPerDay() {
        return sumPerDay;
    }
    
//Can return negative value
    public long getExtraMinPerDay(){
        return requiredMinPerDay - sumPerDay;
    }
    
    public boolean isSeparatedTime(Task t){
        for (Task currentTask : tasks) {
            boolean startOfCurrentIsDuringT =
                    (currentTask.getStartTime().compareTo(t.getStartTime()) >= 0) &&
                    (currentTask.getStartTime().compareTo(t.getEndTime()) < 0);         //start = end is acceptable
            boolean endOfCurrentIsDuringT =
                    (currentTask.getEndTime().compareTo(t.getStartTime()) > 0) &&       //end = start is acceptable
                    (currentTask.getEndTime().compareTo(t.getEndTime()) <= 0);
                    
            if (startOfCurrentIsDuringT || endOfCurrentIsDuringT){
                return false;
            }
        }
        return true;
    }
    
    public void addTask(Task t){
        if (isSeparatedTime(t) && t.isMultipleQuarterHour()){
            tasks.add(t);
        }
        else {
            //To be implemented later
        }
    }
    
    public boolean isWeekday(){
        DayOfWeek dayOfWeek = actualDay.getDayOfWeek();
        return (dayOfWeek != DayOfWeek.SATURDAY) && (dayOfWeek != DayOfWeek.SUNDAY);
    }
    
}
