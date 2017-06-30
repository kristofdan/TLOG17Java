package timelogger.main;

import java.util.*;
import java.time.*;
import timelogger.exceptions.*;

@lombok.Getter
public class WorkDay {
    private List<Task> tasks;
    private LocalDate actualDay;
    private long requiredMinPerDay;
    private long sumPerDay;

//5 constructors for default arguments, default requiredMinPerDay: 450, actualDay: today
    
    public WorkDay()
        throws Exception
    {
        this(450, LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }
    
    public WorkDay(long requiredMinPerDay)
        throws Exception
    {
        this(requiredMinPerDay, LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }
    
    public WorkDay(int year, int month, int day)
        throws Exception
    {
        this(450, year, month, day);
    }
    
    public WorkDay(long requiredMinPerDay, int year, int month, int day)
        throws Exception
    {
        if (requiredMinPerDay < 0)
        {
            throw new NegativeMinutesOfWorkException(
                    "Error: cannot construct day with negative required minutes");
        }else {
            this.requiredMinPerDay = requiredMinPerDay;
        }
        
        LocalDate newActualDay = LocalDate.of(year, month, day);
        if (newActualDay.isAfter(LocalDate.now())){
            throw new FutureWorkException("Error: cannot construct day with date later than today");
        }else {
            actualDay = newActualDay;
        }
        tasks = new LinkedList<>();
    }
    
    public void addTask(Task t)
        throws Exception
    {
        if (Util.isSeparatedTime(t,tasks)){
            tasks.add(t);
        }
        else {
            throw new NotSeparatedTimesException("Error: task cannot be added to day, it overlaps with other tasks within the day");
        }
    }
    
    private void calculateSumPerDay()
        throws Exception
    {
       sumPerDay = 0;
       for (Task currentTask : tasks) {
           sumPerDay += currentTask.getMinPerTask();
       }
   }
   
    public long getExtraMinPerDay()
        throws Exception
    {
        calculateSumPerDay();
        return sumPerDay - requiredMinPerDay;
    }
    
    //DIfferent from requested: If there are no tasks, returns 00:00 (more convenient to use)
    public LocalTime getLatestEndTime()
        throws Exception
    {
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

    public long getSumPerDay()
        throws Exception
    {
        calculateSumPerDay();
        return sumPerDay;
    }
    
    public void setActualDay(int year, int month, int day) 
        throws Exception
    {
        LocalDate newActualDay = LocalDate.of(year, month, day);
        if (newActualDay.isAfter(LocalDate.now())){
            throw new FutureWorkException("Error: cannot set a day's date to a date later than today");
        }else {
            actualDay = newActualDay;
        }
    }

    public void setRequiredMinPerDay(long requiredMinPerDay)
        throws Exception
    {
        if (requiredMinPerDay < 0)
        {
            throw new NegativeMinutesOfWorkException(
                    "Error: cannot set a day's reuired minutes to negative value");
        }else { 
            this.requiredMinPerDay = requiredMinPerDay;
        }
    }
    
    
}
