package com.tibco.cep.studio.dbconcept.utils;
/**
 * 
 * @author schelwa
 *
 */
public class ExceptionOutput {

   // *****************************************************************
   // Output trace information.
   // *****************************************************************   
   public static void outputStackTrace(Exception ex, String source) {
      System.err.println("*** start: " + source + " ***");
      if (ex != null) ex.printStackTrace(); 
      System.err.println("***   end: " + source + " ***");
   }   
}
