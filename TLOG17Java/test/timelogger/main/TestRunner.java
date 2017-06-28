package timelogger.main;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TaskTest.class,WorkDayTest.class);
	/*	
        for (Failure failure : result.getFailures()) {
           System.out.println(failure.toString());
        }
        if (result.wasSuccessful())
        {
            System.out.println("The result: Passed");
        }else {
            System.out.println("The result: Failed");
        } */
        
    }
    
}
