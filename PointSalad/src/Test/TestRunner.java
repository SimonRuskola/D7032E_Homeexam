package PointSalad.src.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

// This class runs the tests in pointSaladTests.java

public class TestRunner {

    public static void main(String[] args) {
      Result result = JUnitCore.runClasses(pointSaladTests.class);
		
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println("\n"+result.wasSuccessful());

      if(result.wasSuccessful()){
         System.out.println("-------------------------------");
         System.out.println("All tests passed");
         System.out.println("-------------------------------");

      }
        
     
   }
    
}
