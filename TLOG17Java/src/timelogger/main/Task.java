package timelogger.main;

import java.time.*;
import java.util.regex.*;
import timelogger.exceptions.*;

public class Task {
    private String taskId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String comment;
    
    public Task(String taskId, String comment, int startHour, int startMinute, int endHour, int endMinute)
    /*throws NoTaskIdException, InvalidTaskIdException*/ {
        LocalTime startTime = LocalTime.of(startHour, startMinute);
        LocalTime endTime = LocalTime.of(endHour, endMinute);
        checkIfValidTimeOrder(startTime,endTime);
        setTaskIdIfValid(taskId);
        this.comment = comment;
        this.startTime = startTime;
        setEndTimeSoThatDurationIsMultipleQuarterHour(startTime,endTime);
    }
    
    public Task(String taskId, String comment, String startTimeString, String endTimeString)
    /*throws NoTaskIdException, InvalidTaskIdException*/ {
        LocalTime startTime = LocalTime.parse(startTimeString);
        LocalTime endTime = LocalTime.parse(endTimeString);
        checkIfValidTimeOrder(startTime,endTime);
        setTaskIdIfValid(taskId);
        this.comment = comment;
        this.startTime = startTime;
        setEndTimeSoThatDurationIsMultipleQuarterHour(startTime,endTime);
    }

    public Task(String taskId) /*throws NoTaskIdException, InvalidTaskIdException*/ {
        setTaskIdIfValid(taskId);
    }
    
    //throws NotExpectedTimeOrder
    private void checkIfValidTimeOrder(LocalTime startTime, LocalTime endTime){
        if (startTime.compareTo(endTime) > 0){
            throw new NotExpectedTimeOrder();
        }
        
    }
    
    private void setTaskIdIfValid(String taskId)
    /*throws NoTaskIdException, InvalidTaskIdException*/ {
        if (isValidTaskId(taskId)){
            this.taskId = taskId;
        }else if (taskId.equals("")){
            throw new NoTaskIdException();
        }
        else {
            throw new InvalidTaskIdException();
        }
    }
    
    private void setEndTimeSoThatDurationIsMultipleQuarterHour(LocalTime startTime, LocalTime endTime){
        this.endTime = Util.roundToMultipleQuarterHour(startTime, endTime);
    }
    
    public long getMinPerTask() {
        if (startTime == null || endTime == null){
            throw new EmptyTimeFieldException();
        }else {
            return Util.minPerTask(startTime, endTime);
        }
    }
    public boolean isValidTaskId(String taskId){
        return isValidRedmineTaskId(taskId) || isValidLTTaskId(taskId);
    }
    
    private boolean isValidRedmineTaskId(String taskId){
        return Pattern.matches("^\\d\\d\\d\\d$", taskId);
    }
    
    private boolean isValidLTTaskId(String taskId){
        return Pattern.matches("^LT-\\d\\d\\d\\d$", taskId);
    }
    
    public String toString(){
        return taskId + " " + comment;
    }

    public String getTaskId() {
        return taskId;
    }
    
    public LocalTime getStartTime() {
        if (startTime == null || endTime == null){
            throw new EmptyTimeFieldException();
        }else {
            return startTime;
        }
    }
    
    public LocalTime getEndTime() {
        if (startTime == null || endTime == null){
            throw new EmptyTimeFieldException();
        }else {
            return endTime;
        }
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setTaskId(String taskId) {
        setTaskIdIfValid(taskId);
    }

    public void setStartTime(int hour, int minute) {
        LocalTime newStartTime = LocalTime.of(hour, minute);
        checkIfValidTimeOrder(newStartTime,endTime);
        startTime = newStartTime;
        setEndTimeSoThatDurationIsMultipleQuarterHour(startTime, endTime);
    }
    
    public void setStartTime(String startTimeAsString) {
        LocalTime newStartTime = LocalTime.parse(startTimeAsString);
        checkIfValidTimeOrder(newStartTime,endTime);
        startTime = newStartTime;
        setEndTimeSoThatDurationIsMultipleQuarterHour(startTime, endTime);
    }

    public void setEndTime(int hour, int minute) {
        LocalTime newEndTime = LocalTime.of(hour, minute);
        checkIfValidTimeOrder(startTime,newEndTime);
        endTime = newEndTime;
        setEndTimeSoThatDurationIsMultipleQuarterHour(startTime, endTime);
    }
    
    public void setEndTime(String endTimeAsString) {
        LocalTime newEndTime = LocalTime.parse(endTimeAsString);
        checkIfValidTimeOrder(startTime,newEndTime);
        endTime = newEndTime;
        setEndTimeSoThatDurationIsMultipleQuarterHour(startTime, endTime);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}