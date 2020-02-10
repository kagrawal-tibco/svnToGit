package com.tibco.cep.bemm.management.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** This class gets the list of all the methods exposed for a given agent type */
public class EntityMethodsLayout {

    //class instance created just once when class is loaded
    private static EntityMethodsLayout instance = new EntityMethodsLayout();

    private static enum ENTITY_TYPE {PROCESS, AGENT};
    //Array with the relative path of the files containing the methods layout information.
    private static String[] xmlFileLocation = {"/agentmethodslayout.xml",
                                               "/processmethodslayout.xml"};

    //exposed as catalog function via MethodsHelper
    public static String getMethodsLayout(String entityType) {
        try {
            switch (ENTITY_TYPE.valueOf(entityType.toUpperCase())) {
                case AGENT:
                    return convertStreamToString(instance.getClass().getResourceAsStream(xmlFileLocation[0]));
                case PROCESS:
                    return convertStreamToString(instance.getClass().getResourceAsStream(xmlFileLocation[1]));
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }//getMethodsLayout

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;

        try {
           while ((line = reader.readLine()) != null)
              sb.append(line);
        } catch (IOException e) {
           e.printStackTrace();
       } finally {
          try {
             is.close();
            } catch (IOException e) {
               e.printStackTrace();
           }
       }
        //send the xml string in just one line (remove newlines and tabs)
        return sb.toString().replaceAll("\\t||\\n","");
    }//convertStreamToString

//    public static void main(String[] args) { //todo delete test main()
//        System.err.println("Printing the contents of XML file for IA");
//        System.err.println(getMethodsLayout("process") );
//        System.err.println("Requesting again");
//        System.err.println(getMethodsLayout("agent") );
//        System.err.println("Requesting again");
//        System.err.println(getMethodsLayout("another entity") );
//    }

} //class
