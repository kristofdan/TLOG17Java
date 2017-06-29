package timelogger.main;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoField;
import timelogger.exceptions.EmptyTimeFieldException;
import timelogger.exceptions.NotExpectedTimeOrder;

public class Util {
    public static boolean isWeekday(LocalDate day){
        DayOfWeek dayOfWeek = day.getDayOfWeek();
        return (dayOfWeek != DayOfWeek.SATURDAY) && (dayOfWeek != DayOfWeek.SUNDAY);
    }
    
    public static boolean isMultipleQuarterHour(LocalTime startTime, LocalTime endTime){
        return (minPerTask(startTime, endTime) % 15 == 0);
    }
    
    public static boolean isSeparatedTime(Task taskToCompare, List<Task> groupOfTasks){
        for (Task currentTask : groupOfTasks) {
            boolean areOverlappingTasks = areOverlappingTasks(taskToCompare, currentTask);
            if (areOverlappingTasks){
                return false;
            }
        }
        return true;
    }
    
    private static boolean areOverlappingTasks(Task first, Task second){
        return startOfFirstTaskIsDuringSecond(first,second) || endOfFirstTaskIsDuringSecond(first, second);
    }
    
    private static boolean startOfFirstTaskIsDuringSecond(Task first, Task second){
        boolean startOfFirstIsLaterThanStartOfSecond =
                first.getStartTime().compareTo(second.getStartTime()) >= 0;
        boolean startOfFirstIsEarlierThanEndOfSecond =
                first.getStartTime().compareTo(second.getEndTime()) < 0;
        return startOfFirstIsLaterThanStartOfSecond && startOfFirstIsEarlierThanEndOfSecond;
    }
    
    private static boolean endOfFirstTaskIsDuringSecond(Task first, Task second){
        boolean endOfFirstIsLaterThanStartOfSecond =
                first.getEndTime().compareTo(second.getStartTime()) > 0;      
        boolean endOfFirstIsEarlierThanEndOfSecond =
                first.getEndTime().compareTo(second.getEndTime()) <= 0;
        return endOfFirstIsLaterThanStartOfSecond && endOfFirstIsEarlierThanEndOfSecond;
    }
    
    //EndTime can be rounded past midnight
    public static LocalTime roundToMultipleQuarterHour(LocalTime startTime, LocalTime endTime){
        long duration = minPerTask(startTime, endTime);
        long remainder = duration % 15;
        boolean wouldBeRoundedToZeroLength = remainder<8 && endTime.minusMinutes(remainder).equals(startTime);
        if (remainder<8 && !wouldBeRoundedToZeroLength){
            return endTime.minusMinutes(remainder);
        }else {
            return endTime.plusMinutes(15 - remainder);
        }
    }
    
    public static long minPerTask(LocalTime startTime, LocalTime endTime){
        if (startTime == null || endTime == null){
            throw new EmptyTimeFieldException();
        }
        if (startTime.compareTo(endTime) > 0){
            throw new NotExpectedTimeOrder();
        }
        
        long hourDiff =
                endTime.getLong(ChronoField.HOUR_OF_DAY) - startTime.getLong(ChronoField.HOUR_OF_DAY);
        long minuteDiff =
                endTime.getLong(ChronoField.MINUTE_OF_HOUR) - startTime.getLong(ChronoField.MINUTE_OF_HOUR);
        return hourDiff * 60 + minuteDiff;
    }
}