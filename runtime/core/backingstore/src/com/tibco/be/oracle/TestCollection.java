package com.tibco.be.oracle;

import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Mar 27, 2007
 * Time: 8:45:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestCollection {

    public static void main (String args[]) {
        TreeMap tm = new TreeMap();
        long m1= Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long t1=System.currentTimeMillis();
        for (int i=0; i < 3000000; i++) {
            tm.put(new Long(1000000-i), new Integer(i*10));
        }
        System.out.println("Time taken =" + (System.currentTimeMillis() - t1)/1000.00);
        long m2=Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Memory Used =" + (m2-m1)/(1024*1024) + " MB");
    }
}
