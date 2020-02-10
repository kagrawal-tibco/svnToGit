package com.tibco.cep.decision.table.constraintpane;

import java.io.InputStream;
import java.util.HashMap;

import org.xml.sax.InputSource;

import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;

/**
 * User: ssubrama
 * Creation Date: Aug 1, 2008
 * Creation Time: 7:19:19 PM
 * <p/>
 * $LastChangedDate$
 * $Rev$
 * $LastChangedBy$
 * $URL$
 */
@SuppressWarnings({"rawtypes" , "unchecked"})
public class DecisionTableFactory {

    public static DecisionTable createDecisionTable(InputStream is1, InputStream is2) throws Exception {

        XiParser parser = XiParserFactory.newInstance();
        long t1 = System.nanoTime();
        //XiNode node = parser.parse(new InputSource(is));
        DecisionTableContentHandler_Pass1 dtch1 = new DecisionTableContentHandler_Pass1();
        parser.parse(new InputSource(is1), dtch1);
        HashMap cm = dtch1.getColumnMap();
        DecisionTableContentHandler_Pass2 dtch2 = new DecisionTableContentHandler_Pass2(cm);
        
        parser.parse(new InputSource(is2), dtch2);
        long t2 = System.nanoTime();
        System.out.println("Time required to parse the 65MB of XML is = " + ((t2-t1)/1000000) + "ms");
        return  dtch2.getDecisionTable();
    }

//    public static void main(String[] args) {
//        try {
//            String fileName = "C:\\Documents and Settings\\ssubrama\\Desktop\\Virtual_RF\\rows_19k.rulefunctionimpl";
//            //String fileName = "C:\\Software\\Projects\\AllState\\Virtual_RF\\rows_19k.rulefunctionimpl";
//            InputStream is1 = new FileInputStream(fileName);
//            InputStream is2 = new FileInputStream(fileName);
//            DecisionTable dt = DecisionTableFactory.createDecisionTable(is1, is2);
//            //dt.optimize();
//            dt.printStats();
//
//            HashMap input = new HashMap();
//            input.put("groupControlData.PolicyState", "XX");
//            input.put("groupControlData.Company", "065");
//            input.put("groupControlData.LineCode", "070");
//            input.put("groupControlData.BusinessType", "B");
//            input.put("groupControlData.Algorithm", "7");
//            input.put("groupControlData.Score", 495);
//
//            long t1 = System.nanoTime();
//            HashSet resultantRules = dt.evaluateConditionsOptimized(input);
//            long t2 = System.nanoTime();
//            System.out.println("Time required to evaluate optimized " + ((t2-t1)/1000000) + "ms");
//            System.out.println("#Nos of rules to be fired - " + resultantRules.size());
//            System.out.println("The following rules have to be fired :" + resultantRules.toString() );
//
//            input.put("groupControlData.PolicyState", "AR");
//            input.put("groupControlData.Company", "ENX");
//            input.put("groupControlData.LineCode", "AUT");
//            input.put("groupControlData.BusinessType", "B");
//            input.put("groupControlData.Algorithm", "E");
//            input.put("groupControlData.Score", 289);
//
//            t1 = System.nanoTime();
//            resultantRules = dt.evaluateConditionsOptimized(input);
//            t2 = System.nanoTime();
//            System.out.println("Time required to evaluate optimized " + ((t2-t1)/1000000) + "ms");
//            System.out.println("#Nos of rules to be fired - " + resultantRules.size());
//            System.out.println("The following rules have to be fired :" + resultantRules.toString() );
//
//
//            input.put("groupControlData.PolicyState", "AR");
//            input.put("groupControlData.Company", "065");
//            input.put("groupControlData.LineCode", "070");
//            input.put("groupControlData.BusinessType", "B");
//            input.put("groupControlData.Algorithm", "7");
//            input.put("groupControlData.Score", 495);
//
//            t1 = System.nanoTime();
//            resultantRules = dt.evaluateConditionsOptimized(input);
//            t2 = System.nanoTime();
//            System.out.println("Time required to evaluate optimized " + ((t2-t1)/1000000) + "ms");
//            System.out.println("#Nos of rules to be fired - " + resultantRules.size());
//            System.out.println("The following rules have to be fired :" + resultantRules.toString() );
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

