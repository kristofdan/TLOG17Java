package Main;

import java.time.*;
import java.time.temporal.ChronoField;
import java.util.regex.*;

public class Task {
    private String taskId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String comment;
    
    public Task(String taskId, String comment, int startHour, int startMinute, int endHour, int endMinute){
        this.taskId = taskId;
        this.comment = comment;
        startTime = LocalTime.of(startHour, startMinute);
        endTime = LocalTime.of(endHour, endMinute);
    }
    
    public Task(String taskId, String comment, String startTime, String endTime){
        this.taskId = taskId;
        this.comment = comment;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
    }

    public Task(String taskId) {
        this.taskId = taskId;
    }
    
    public long getMinPerTask() {
        return Util.minPerTask(startTime, endTime);
    }
    public boolean isValidTaskId(){
        return isValidRedmineTaskId() || isValidLTTaskId();
    }
    
    public boolean isValidRedmineTaskId(){
        return Pattern.matches("^\\d\\d\\d\\d$", comment);
    }
    
    public boolean isValidLTTaskId(){
        return Pattern.matches("^LT-\\d\\d\\d\\d$", comment);
    }

    public String getTaskId() {return taskId;}
    
    public LocalTime getStartTime() {return startTime;}
    
    public LocalTime getEndTime() {return endTime;}
    
    public String getComment() {return comment;}
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setStartTime(int hour, int minute) {
        this.startTime = LocalTime.of(hour, minute);
    }
    
    public void setStartTime(String startTimeAsString) {
        this.startTime = LocalTime.parse(startTimeAsString);
    }

    public void setEndTime(int hour, int minute) {
        this.endTime = LocalTime.of(hour, minute);
    }
    
    public void setEndTime(String endTimeAsString) {
        this.endTime = LocalTime.parse(endTimeAsString);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}