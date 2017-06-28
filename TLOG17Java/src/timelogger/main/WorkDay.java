package timelogger.main;

import java.util.*;
import java.time.*;
import timelogger.exceptions.*;

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
    
    public WorkDay(int year, int month, int day){
        this(450, year, month, day);
    }
    
    public WorkDay(long requiredMinPerDay, int year, int month, int day) {
        if (requiredMinPerDay < 0)
        {
            throw new NegativeMinutesOfWorkException();
        }else {
            this.requiredMinPerDay = requiredMinPerDay;
        }
        
        LocalDate newActualDay = LocalDate.of(year, month, day);
        if (newActualDay.isAfter(LocalDate.now())){
            throw new FutureWorkException();
        }else {
            actualDay = newActualDay;
        }
        tasks = new LinkedList<>();
    }
    
    public void addTask(Task t){
        if (Util.isSeparatedTime(t,tasks)){
            tasks.add(t);
        }
        else {
            throw new NotSeparatedTimesException();
        }
    }
    
    private void calculateSumPerDay(){
       sumPerDay = 0;
       for (Task currentTask : tasks) {
           sumPerDay += currentTask.getMinPerTask();
       }
   }
   
    public long getExtraMinPerDay(){
        calculateSumPerDay();
        return sumPerDay - requiredMinPerDay;
    }
    
    //DIfferent from requested: If there are no tasks, returns 00:00 (more convenient to use)
    public LocalTime getLatestEndTime(){
        if (tasks.isEmpty()){
            return LocalTime.of(0, 0);
        }else {
            LocalTime max = tasks.get(0).getEndTime();
            for (Task task : tasks){
                if (task.getEndTime().compareTo(max) > 0){
                    max = task.getEndTime();
                }
            }
            return max;
        }
    }

    public LocalDate getActualDay() {
        return actualDay;
    }

    public long getRequiredMinPerDay() {
        return requiredMinPerDay;
    }

    public long getSumPerDay() {
        calculateSumPerDay();
        return sumPerDay;
    }

    public List<Task> getTasks() {
        return tasks;
    }
    
    public void setActualDay(int year, int month, int day) {
        LocalDate newActualDay = LocalDate.of(year, month, day);
        if (newActualDay.isAfter(LocalDate.now())){
            throw new FutureWorkException();
        }else {
            actualDay = newActualDay;
        }
    }

    public void setRequiredMinPerDay(long requiredMinPerDay) {
        if (requiredMinPerDay < 0)
        {
            throw new NegativeMinutesOfWorkException();
        }else {
            this.requiredMinPerDay = requiredMinPerDay;
        }
    }
    
    
}
