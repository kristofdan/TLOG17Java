package Main;

import java.time.*;
import java.time.temporal.ChronoField;
import java.util.regex.*;

public class Task {
    private String taskId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String comment;
    
    Task(String taskId, String comment, int startHour, int startMinute, int endHour, int endMinute){
        this.taskId = taskId;
        this.comment = comment;
        startTime = LocalTime.of(startHour, startMinute);
        endTime = LocalTime.of(endHour, endMinute);
    }
    Task(String taskId, String comment, String startTime, String endTime){
        this.taskId = taskId;
        this.comment = comment;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
    }
    public String getTaskId() {return taskId;}
    public LocalTime getStartTime() {return startTime;}
    public LocalTime getEndTime() {return endTime;}
    public String getComment() {return comment;}
    public long getMinPerTask() {
        long hourDiff =
                endTime.getLong(ChronoField.HOUR_OF_DAY) - startTime.getLong(ChronoField.HOUR_OF_DAY);
        long minuteDiff =
                endTime.getLong(ChronoField.MINUTE_OF_DAY) - startTime.getLong(ChronoField.MINUTE_OF_DAY);
        return hourDiff * 60 + minuteDiff;
    }
    public boolean isValidTaskId(){
        boolean isExactlyFourDigits = Pattern.matches("^\\d\\d\\d\\d$", comment);
        boolean isExactlyLtAndFourDigits = Pattern.matches("^LT-\\d\\d\\d\\d$", comment);
        return isExactlyFourDigits || isExactlyLtAndFourDigits;
    }
    public boolean isMultipleQuarterHour(){
        return (getMinPerTask() % 15 == 0);
    }
}