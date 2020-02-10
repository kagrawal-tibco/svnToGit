package com.tibco.rta.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class AggTestRunner {
	
	 	
	public static void main(String[] args) {
	      Result result = JUnitCore.runClasses(AggtestSuite.class);
	      for (Failure failure : result.getFailures()) {
	         System.out.println("Failure results - " + failure.toString());
	      }
	      System.out.println("Test case was success[true]- " + result.wasSuccessful());
	   }
		   

}
