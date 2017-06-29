package timelogger.main;

import java.util.*;
import java.util.regex.Pattern;
import java.time.*;
import timelogger.exceptions.*;

public class TimeLoggerUI {
    
    public static void main(String[] args) {
        System.out.println(Util.minPerTask(LocalTime.of(0, 0), LocalTime.of(12,0)));
        TimeLoggerUI ui = new TimeLoggerUI();
        ui.logger = new TimeLogger();
        
        System.out.println("Time logger\n\n");
        int chosenEntryNumber = 1;
        while (chosenEntryNumber != 0){
            chosenEntryNumber = ui.askForEntryNumber();
            ui.performTaskCorrespondingTo(chosenEntryNumber);
        }
    }

    public TimeLoggerUI() {
        this.scanner = new Scanner(System.in);
    }

    protected void finalize() throws Throwable {
        scanner.close();
        super.finalize();
    }
    
    
    
    private int askForEntryNumber(){
        printMenu();
        int entryNumber = 0;
        boolean entryIsValid = false;
        while (!entryIsValid){
            System.out.print("Choose an entry: ");
            entryNumber = scanner.nextInt();
            entryIsValid = entryNumber >= 0 && entryNumber <= 10;
            if (!entryIsValid){
                System.out.println("Not an existing entry");
            }
        }
        return entryNumber;
    }
    
    private void printMenu(){
        System.out.println("Type the number of entry you want to choose\n\n");
        System.out.println("0. Exit\n");
        System.out.println("1. List months\n");
        System.out.println("2. List days\n");
        System.out.println("3. List task for a specific day\n");
        System.out.println("4. Add new month\n");
        System.out.println("5. Add day to specific month\n");
        System.out.println("6. Add a task for a day\n");
        System.out.println("7. Finish a specific task:\n");
        System.out.println("8. Delete a task\n");
        System.out.println("9. Modify a task\n");
        System.out.println("10. Print satistics of a month\n");
    }
    
    private void performTaskCorrespondingTo(int entryNumber){
        switch (entryNumber) {
            case 0: break;
            case 1: listMonths(); break;
            case 2: askForMonthThenListDays(); break;
            case 3: listTasksForChosenDay(); break;
            case 4: addNewMonth(); break;
            case 5: addDayToMonth(); break;
            case 6: addTaskToDay(); break;
            case 7: addTaskEndTimeToTask(); break;
            case 8: deleteTask(); break;
            case 9: modifyTask(); break;
            case 10: printStatisticsForMonth(); break;
        }
    }
    
//Methods for the menu entries (and their auxiliary methods)

    private void askForMonthThenListDays(){
        listMonths();
        if (loggerContainsMonths){
            chosenMonth = askForExistingMonth();
            chosenMonthContainsDays = !chosenMonth.getDays().isEmpty();
            if (chosenMonthContainsDays){
                listDaysOfChosenMonth();
            }else {
                System.out.println("This month contains no days");
            }
        }
    }
    
    private void listMonths(){
        loggerContainsMonths = !logger.getMonths().isEmpty();
        if (!loggerContainsMonths){
            System.out.println("No months added yet\n");
            return;
        }
        int serialNumber = 1;
        System.out.println("The months:\n\n");
        for (WorkMonth currentMonth : logger.getMonths()){
            System.out.println(serialNumber + " " + currentMonth.getDate());
            serialNumber++;
        }
    }
    
    private WorkMonth askForExistingMonth(){
        String input;
        int monthIndex = 0;
        boolean existingMonthNumber;
        do {
            System.out.print("Which month do you want to choose? ");
            input = scanner.nextLine();
            if (Pattern.matches("^\\d+$", input)){
                monthIndex = Integer.valueOf(input) - 1;
                existingMonthNumber = monthIndex >=0 && monthIndex < logger.getMonths().size();
                if (!existingMonthNumber){
                    System.out.println("Not an existing month");
                }
            }else {
                existingMonthNumber = false;
                System.out.println("Not a number");
            }
        }while(!existingMonthNumber);
        return logger.getMonths().get(monthIndex);
    }
    
    private void listDaysOfChosenMonth(){
        int serialNumber = 1;
        System.out.println("The days:");
        for (WorkDay currentDay : chosenMonth.getDays()) {
            System.out.println(serialNumber + " " +currentDay.getActualDay());
            serialNumber++;
        }
    }
    
    private void listTasksForChosenDay(){
        askForMonthThenListDays();
        if (loggerContainsMonths && chosenMonthContainsDays){
            chosenDay = askForExistingDay();
            boolean chosenDayContainsTasks = !chosenDay.getTasks().isEmpty();
            if (chosenDayContainsTasks){
                printTasksForChosenDay();
            }else {
                System.out.println("This day contains no tasks");
            }
        }
    } 
    
    private WorkDay askForExistingDay(){
        String input;
        int dayIndex = 0;
        boolean existingDayNumber;
        do {
            System.out.print("Which day's tasks do you want to list? ");
            input = scanner.nextLine();
            if (Pattern.matches("^\\d+$", input)){
                dayIndex = Integer.valueOf(input) - 1;
                existingDayNumber = dayIndex >= 0 && dayIndex < chosenMonth.getDays().size();
                if (!existingDayNumber){
                    System.out.println("Not an existing day");
                }
            }else {
                existingDayNumber = false;
                System.out.println("Not a number");
            }
        }while(!existingDayNumber);
        return chosenMonth.getDays().get(dayIndex);
    }
    
    private void printTasksForChosenDay(){
        System.out.println("The tasks:");
        for (Task currentTask : chosenDay.getTasks()) {
            System.out.print(currentTask.getTaskId() + " " + currentTask.getComment() + " " +
                    currentTask.getStartTime() + " ");
            if (currentTask.getEndTime().equals(currentTask.getStartTime())){
                System.out.print("end time not specified yet\n");
            }else {
                System.out.print(currentTask.getEndTime() + "\n");
            }
        }
    }
    
    private void addNewMonth(){
        int year, month;
        boolean isValidDate;
        do{
            System.out.print("Year: ");
            year = scanner.nextInt();
            System.out.print("Month: ");
            month = scanner.nextInt();
            isValidDate = year>=2000 && year<=2100 && month>=1 && month<=12;
            if (!isValidDate){
                System.out.println("Not a proper date");
            }
        }while(!isValidDate);
        WorkMonth newMonth = new WorkMonth(year, month);
        logger.addMonth(newMonth);
    }
    
    private void addDayToMonth(){
        listMonths();
        if (loggerContainsMonths){
            chosenMonth = askForExistingMonth();
            int day = askForDayInChosenMonth();
            int requiredMinutes = askForRequiredWorkingMinutes();
            WorkDay newDay = new WorkDay(requiredMinutes,chosenMonth.getDate().getYear(),
                    chosenMonth.getDate().getMonthValue(),day);
            try{
                chosenMonth.addWorkDay(newDay);
            }catch(Exception e){
                System.out.println("Error: this day cannot be added to this month");
            }
        }
        
    }
    
    private int askForDayInChosenMonth(){
        String input;
        int day = 0;
        int lengthOfChosenMonth = chosenMonth.getDate().getMonth().length(
                chosenMonth.getDate().isLeapYear());
        boolean isValidDay;
        do{
            System.out.print("Day: ");
            input = scanner.nextLine();
            if (Pattern.matches("^\\d+$", input)){
                day = Integer.valueOf(input);
                isValidDay = day>=1 && day<=lengthOfChosenMonth;
                if (!isValidDay){
                    System.out.println("Not a valid day for this month");
                }
            }else {
                isValidDay = false;
                System.out.println("Not a number");
            }
        }while(!isValidDay);
        return day;
    }
    
    private int askForRequiredWorkingMinutes(){
        String input;
        int requiredMinutes = 0;
        boolean isValidValue;
        do{
            System.out.print("Required working minutes: ");
            input = scanner.nextLine();
            if (Pattern.matches("^\\d+$", input)){
                requiredMinutes = Integer.valueOf(input);
                isValidValue = requiredMinutes>=1 && requiredMinutes<=960;      //960 min = 16 hour
                if (!isValidValue){
                    System.out.println("Not a valid value, should be between 1 and 960 (16 hour)");
                }
            }else {
                isValidValue = false;
                System.out.println("Not a number");
            }
        }while(!isValidValue);
        return requiredMinutes;
    }
    
    //Sets endTime to startTime (couldn't insert it if endTime would be empty)
    private void addTaskToDay(){
        listTasksForChosenDay();
        String taskID = askForTaskID();
        System.out.println("Description: ");
        String comment = scanner.nextLine();
        printLatestEndTimeOfTheDay();
        String startTime = askForStartTime(true);
        if (startTime.equals("")){
            startTime = chosenDay.getLatestEndTime().toString();
        }
        try{
            Task newTask = new Task(taskID,comment,startTime,startTime);
            chosenDay.addTask(newTask);
        }catch (NoTaskIdException e){
            System.out.println("Error: empty task ID");
        }catch (InvalidTaskIdException e){
            System.out.println("Error: invalid task ID");
        }catch (NotExpectedTimeOrder e){
            System.out.println("Error: End time is earlier than start time");
        }
    }

    private String askForTaskID(){
        System.out.print("New task ID: ");
        String taskID = scanner.nextLine();
        return taskID;
    }
    
    private void printLatestEndTimeOfTheDay(){
            System.out.println(
                    "Latest end time of the day: (" + chosenDay.getLatestEndTime().toString() + ")");
    }
    
    //Will also accept empty input if the chosen day contains tasks (for entry 6)
    private String askForStartTime(boolean canBeZero){
        
        String input;
        int hour = 0;
        int minute = 0;
        boolean isValidTime;
        do{
            System.out.print("Starting time (HH:MM): ");
            input = scanner.nextLine();
            boolean isValidForm = Pattern.matches("^\\d\\d:\\d\\d$",input) ||
                    (canBeZero && input.equals(""));
            if (isValidForm && !input.equals("")){
                hour = Integer.valueOf(input.substring(0,1));
                minute = Integer.valueOf(input.substring(3,4));
            }else if (!isValidForm){
                System.out.println("Not a valid format");
            }
            isValidTime = isValidForm && hour>=0 && hour<24 && minute>=0 && minute<60;
        }while(!isValidTime);
        return input;
    }
    
    private void addTaskEndTimeToTask(){
        askForMonthThenListDays();
        if (loggerContainsMonths && chosenMonthContainsDays){
            chosenDay = askForExistingDay();
            boolean chosenDayContainsTasks = !chosenDay.getTasks().isEmpty();
            if (chosenDayContainsTasks){
                printUnfinishedTasksForChosenDay();
                Task chosenTask = askForExistingTaskWithUnfinishedTime();
                String endTime = askForEndTime(false);
                chosenTask.setEndTime(endTime);
            }else {
                System.out.println("This day contains no tasks");
            }
        }
    }
    
    private void printUnfinishedTasksForChosenDay(){
        System.out.println("The unfinished tasks:");
        for (Task currentTask : chosenDay.getTasks()) {
            if (currentTask.getEndTime().equals(currentTask.getStartTime())){
                System.out.println(currentTask.getTaskId() + " " +
                    currentTask.getComment() + " " + currentTask.getStartTime() +
                    " end time not specified yet\n");
            }
        }
    }
    
    private Task askForExistingTaskWithUnfinishedTime(){
        String chosenTaskID;
        while(true){
            System.out.println("The task ID: ");
            chosenTaskID = scanner.nextLine();
            for (Task currentTask : chosenDay.getTasks()) {
                if (currentTask.getEndTime().equals(currentTask.getStartTime()) &&
                        currentTask.getTaskId().equals(chosenTaskID)){
                    return currentTask;
                }
            }
            System.out.println("Not an existing task ID or the end time is already specified");
        }
    }
    
    private String askForEndTime(boolean canBeEmpty){
        String input;
        int hour = 0;
        int minute = 0;
        boolean isValidTime;
        do{
            System.out.print("Endtime (HH:MM): ");
            input = scanner.nextLine();
            boolean isValidForm = Pattern.matches("^\\d\\d:\\d\\d$",input) ||
                    (canBeEmpty && input.equals(""));
            if (isValidForm && !input.equals("")){
                hour = Integer.valueOf(input.substring(0,1));
                minute = Integer.valueOf(input.substring(3,4));
            }else if (!isValidForm){
                System.out.println("Not a valid format");
            }
            isValidTime = isValidForm && hour>=0 && hour<24 && minute>=0 && minute<60;
        }while(!isValidTime);    
        return input;
    }
    
    private void deleteTask(){
        listTasksForChosenDay();
        Task taskToDelete = askForExistingTask();
        chosenDay.getTasks().remove(taskToDelete);
    }
    
    private Task askForExistingTask(){
        String chosenTaskID;
        while(true){
            System.out.print("The (existing) task ID: ");
            chosenTaskID = scanner.nextLine();
            for (Task currentTask : chosenDay.getTasks()) {
                if (currentTask.getTaskId().equals(chosenTaskID)){
                    return currentTask;
                }
            System.out.println("Not an existing task ID");
            }
        }
    }
    
    private void modifyTask(){
        listTasksForChosenDay();
        Task taskToModify = askForExistingTask();
        chosenDay.getTasks().remove(taskToModify);  //In the valid values part, we check if the time interval ovarlaps with an existing one
        Task modifiedTask = askForValidNewTaskValuesFor(taskToModify);
        chosenDay.addTask(modifiedTask);
    }
    
    private Task askForValidNewTaskValuesFor(Task taskToModify)
    /*throws NoTaskIdException, InvalidTaskIdException*/{
        Task modifiedTask = null;
        System.out.println("Leave a field empty if you do not want to change it\n");
        System.out.println("Current ID: (" + taskToModify.getTaskId() + ")");
        String newID = askForTaskID();
        if (newID.equals("")){
            newID = taskToModify.getTaskId();
        }
        System.out.println("Current descripton: (" + taskToModify.getComment() + ")");
        System.out.print("New description: ");
        String newComment = scanner.nextLine();
        if (newComment.equals("")){
            newComment = taskToModify.getComment();
        }
        boolean timeIsValid = false;
        do{
            System.out.println("Current start time: (" + taskToModify.getStartTime()+ ")");
            String newStartTime = askForStartTime(true);
            if (newStartTime.equals("")){
                newStartTime = taskToModify.getStartTime().toString();
            }
            System.out.println("Current endtime: (" + taskToModify.getEndTime()+ ")");
            String newEndTime = askForEndTime(true);
            if (newEndTime.equals("")){
                newEndTime = taskToModify.getEndTime().toString();
            }
            try{
                modifiedTask = new Task(newID, newComment, newStartTime, newEndTime);
            }catch (NoTaskIdException e){
                System.out.println("Error: empty task ID");
                continue;
            }catch (InvalidTaskIdException e){
                System.out.println("Error: invalid task ID");
                continue;
            }catch (NotExpectedTimeOrder e){
                System.out.println("Error: End time is earlier than start time");
                continue;
            }
            timeIsValid = Util.isSeparatedTime(modifiedTask, chosenDay.getTasks()) &&
                    Util.isMultipleQuarterHour(modifiedTask.getStartTime(),modifiedTask.getEndTime());
            if (!timeIsValid){
                System.out.println("Not a valid time interval");
            }
        }while(!timeIsValid);
        return modifiedTask;
    }
    
    private void printStatisticsForMonth(){
        listMonths();
        chosenMonth = askForExistingMonth();
        printMonthStatisticsForChosenMonth();
        printDayStatisticsForChosenMonth();
    }
    
    private void printMonthStatisticsForChosenMonth(){
        System.out.println("The month's statistics:\n");
        System.out.println(chosenMonth.getDate()+ " " + chosenMonth.getSumPerMonth()+ " " + 
                    chosenMonth.getRequiredMinPerMonth() + " " + chosenMonth.getExtraMinPerMonth() + "\n");
    }

    private void printDayStatisticsForChosenMonth(){
        System.out.println("The daily statistics:\n");
        for (WorkDay currentDay : chosenMonth.getDays()) {
            System.out.println(currentDay.getActualDay() + " " + currentDay.getSumPerDay() + " " + 
                    currentDay.getRequiredMinPerDay() + " 800 " + currentDay.getExtraMinPerDay());
        }
    }
    
    private final Scanner scanner;
    private TimeLogger logger;
    private boolean loggerContainsMonths;
    private boolean chosenMonthContainsDays;
    private WorkMonth chosenMonth;
    private WorkDay chosenDay;
    
}


//TODO: 