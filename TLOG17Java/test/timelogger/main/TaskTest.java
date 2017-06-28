package timelogger.main;

import java.time.LocalTime;
import org.junit.Test;
import static org.junit.Assert.*;
import timelogger.exceptions.*;

public class TaskTest {
    
    public TaskTest() {
    }
    
    @Test(expected = NotExpectedTimeOrder.class)
    public void testCase1(){
        Task task = new Task("1234","", "08:45", "07:30");
    }
    
    @Test
    public void testCase3(){
        Task task = new Task("1234","", "07:30", "08:45");
        assertEquals(75, task.getMinPerTask());
    }
    
    @Test(expected = InvalidTaskIdException.class)
    public void testCase4(){
        Task task = new Task("154858","", "00:00","00:00");
    }
    
    @Test(expected = InvalidTaskIdException.class)
    public void testCase5(){
        Task task = new Task("LT-154858","","00:00","00:00");
    }
    
    @Test(expected = NoTaskIdException.class)
    public void testCase6(){
        Task task = new Task("","", "00:00","00:00");
    }
    
    @Test
    public void testCase7(){
        Task task = new Task("1234","", "07:30", "08:45");
        assertEquals("", task.getComment());
    }
    
    @Test
    public void testCase8(){
        Task task = new Task("1234","", "07:30", "07:50");
        assertEquals("07:45", task.getEndTime().toString());
    }
    
    //Also tests, that the duration cannot become zero
    @Test
    public void testCase9_1(){
        Task task = new Task("1234","","00:00","01:00");
        task.setStartTime("00:53");
        assertEquals("01:08", task.getEndTime().toString());
    }
    
    @Test
    public void testCase9_2(){
        Task task = new Task("1234","","00:00","01:00");
        task.setStartTime(0,53);
        assertEquals("01:08", task.getEndTime().toString());
    }
    
    @Test
    public void testCase10_1(){
        Task task = new Task("1234","","00:00","01:00");
        task.setEndTime("00:12");
        assertEquals("00:15", task.getEndTime().toString());
    }
    
    @Test
    public void testCase10_2(){
        Task task = new Task("1234","","00:15","01:00");
        task.setEndTime(0,33);
        assertEquals("00:30", task.getEndTime().toString());
    }
    
    @Test(expected = NoTaskIdException.class)
    public void testCase11(){
        Task task = new Task("1234","", "00:00","00:00");
        task.setTaskId("");
    }
    
    @Test(expected = InvalidTaskIdException.class)
    public void testCase12_1(){
        Task task = new Task("1234","", "00:00","00:00");
        task.setTaskId("12343532");
    }
    
    @Test(expected = InvalidTaskIdException.class)
    public void testCase12_2(){
        Task task = new Task("1234","", "00:00","00:00");
        task.setTaskId("LT-12343532");
    }
    
    @Test(expected = NotExpectedTimeOrder.class)
    public void testCase13_1(){
        Task task = new Task("1234","", "12:00","13:00");
        task.setStartTime("14:00");
    }
    
    @Test(expected = NotExpectedTimeOrder.class)
    public void testCase13_2(){
        Task task = new Task("1234","", "12:00","13:00");
        task.setStartTime(14,0);
    }
    
    @Test(expected = NotExpectedTimeOrder.class)
    public void testCase14_1(){
        Task task = new Task("1234","", "12:00","13:00");
        task.setEndTime("11:00");
    }
    
    @Test(expected = NotExpectedTimeOrder.class)
    public void testCase14_2(){
        Task task = new Task("1234","", "12:00","13:00");
        task.setEndTime(11,0);
    }
    
    @Test(expected = EmptyTimeFieldException.class)
    public void testCase15(){
        Task task = new Task("1234");
        task.getMinPerTask();
    }
    
    @Test
    public void testCase16(){
        Task task = new Task("1234","","07:30","07:45");
        task.setStartTime("07:00");
        assertEquals("07:00", task.getStartTime().toString());
    }

    @Test
    public void testCase17(){
        Task task = new Task("1234","","07:30","07:45");
        task.setEndTime("08:00");
        assertEquals("08:00", task.getEndTime().toString());
    }
    
    @Test
    public void testCase18(){
        Task task = new Task("1234","description","07:30","07:45");
        assertEquals("1234", task.getTaskId());
        assertEquals("description", task.getComment());
        assertEquals("07:30", task.getStartTime().toString());
        assertEquals("07:45", task.getEndTime().toString());
    }
}
