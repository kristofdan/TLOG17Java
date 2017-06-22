package Main;

import java.util.*;
import java.time.*;

public class WorkDay {
    private List<Task> tasks;
    private LocalDate actualDay;
    private long requiredMinPerDay;
    private long sumPerDay;

//5 constructors for default arguments, default requiredMinPerDay: 450, actualDay: today
    
    public WorkDay() {
        this(450, LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }
    
    public WorkDay(long requiredMinPerDay) {
        this(requiredMinPerDay, LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }
    
    public WorkDay(long requiredMinPerDay, int year) {
        this(requiredMinPerDay, year,
                LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }
    
    public WorkDay(long requiredMinPerDay, int year, int month) {
        this(requiredMinPerDay, year, month,
                LocalDate.now().getDayOfMonth());
    } 
    
   public WorkDay(long requiredMinPerDay, int year, int month, int day) {
        this.requiredMinPerDay = requiredMinPerDay;
        actualDay = LocalDate.of(year, month, day);
    }

//Can return negative value
    public long getExtraMinPerDay(){
        return requiredMinPerDay - sumPerDay;
    }
    
    public void addTask(Task t){
        if (Util.isSeparatedTime(t,tasks) && Util.isMultipleQuarterHour(t.getStartTime(), t.getEndTime())){
            tasks.add(t);
        }
        else {
            //To be implemented later
        }
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

    public List<Task> getTasks() {
        return tasks;
    }
    
    public void setActualDay(int year, int month, int day) {
        this.actualDay = LocalDate.of(year, month, day);
    }

    public void setRequiredMinPerDay(long requiredMinPerDay) {
        this.requiredMinPerDay = requiredMinPerDay;
    }
    
    
}
