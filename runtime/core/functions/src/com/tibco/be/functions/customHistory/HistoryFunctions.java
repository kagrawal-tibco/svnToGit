package com.tibco.be.functions.customHistory;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomDouble;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: hkarmark
 * Date: Oct 1, 2004
 * Time: 4:17:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class HistoryFunctions{
    @com.tibco.be.model.functions.BEFunction(
        name = "movingAverageDouble",
        synopsis = "Function to calculate a sample based moving average over a time interval,\nbest used on a pre-prepared concept.",
        signature = "double movingAverageDouble(PropertyAtomDouble doubleProp, long startTime, long endTime, int sampleIntervalSec)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "doubleProp", type = "Property", desc = "Property to be averaged"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "startTime", type = "long", desc = "Start time in milliseconds, often current time minus some interval"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "endTime", type = "long", desc = "End time in milliseconds, often current time"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sampleIntervalSec", type = "int", desc = "Time interval between samples in seconds")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static double movingAverageDouble(PropertyAtomDouble doubleProp, long startTime, long endTime, int sampleIntervalSec ) {
        double movAvg = 0;
        double sum = 0;
        int sampleIntervalMillis = sampleIntervalSec * 1000;
        long time = startTime;
        try {
            while (time < endTime) {
                sum += doubleProp.getDouble(time);
                time = time + sampleIntervalMillis;
            }
            movAvg = (sum * sampleIntervalMillis) / (endTime - startTime);
        } catch (Exception e) {
            System.out.print(e);
        }
        return movAvg;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "genMeanDbl",
        synopsis = "Function to calculate a sample based mean over the last numGenerations changes from the history",
        signature = "double genMeanDbl(PropertyAtomDouble doubleProp, int numGenerations)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "doubleProp", type = "Property", desc = "Property to be averaged"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "numGenerations", type = "int", desc = "Number of history points (changes) to average")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
   public static double genMeanDbl(PropertyAtomDouble doubleProp, int numGenerations) {
       double mean=0;
       double sum=0;
       Iterator iter= doubleProp.historyIterator();
       for (int i=0;i<numGenerations;i++){
           sum= ((Double)((PropertyAtom.History)iter.next()).value).doubleValue() + sum;
       }
       mean= sum/numGenerations;
       return mean;
   }

//   public static double genVwapDbl(PropertyAtomLong volume,PropertyAtomDouble price, int numGenerations){
//       double vwap=0;
//       int vol=0;
//       double pri=0;
//       double sum=0;
//       int totalVol=0;
//       Iterator volIter= volume.historyIterator();
//       Iterator priceIter= price.historyIterator();
//       for (int i=0;i<numGenerations;i++){
//           vol=((Integer)((PropertyAtom.History)volIter.next()).value).intValue();
//           pri=((Double)((PropertyAtom.History)volIter.next()).value).doubleValue();
//           totalVol+=vol;
//           sum+=(vol*pri);
//
//       }
//       vwap= sum/totalVol;
//       return vwap;
// }

    @com.tibco.be.model.functions.BEFunction(
        name = "genStDevDbl",
        synopsis = "Function to calculate a sample standard deviation over the last numGenerations points from the history",
        signature = "double genStDevDbl(PropertyAtomDouble doubleProp, int numGenerations)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "doubleProp", type = "Property", desc = "Property to calculate the standard deviation of"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "numGenerations", type = "int", desc = "Number of historical points to use in historical calculation")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Function to calculate a sample standard deviation over the last numGenerations points from the history",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static double genStDevDbl(PropertyAtomDouble doubleProp, int numGenerations) {
        double stDev = 0;
        double mean = genMeanDbl(doubleProp, numGenerations);
        Iterator iter = doubleProp.historyIterator();
        Object next;
        double numerator = 0;
        for (int i = 0; i < numGenerations; i++) {
            next = ((PropertyAtom.History) iter.next()).value;
            numerator = numerator + Math.pow((((Double) next).doubleValue() - mean), 2);
            System.out.println(mean + "mean");
            System.out.println(next + "next");
            System.out.println(numerator + "num");
        }
        stDev = Math.pow(numerator / (numGenerations - 1), 0.5);
        System.out.println(stDev + "stDev");
        return stDev;
    }
}
