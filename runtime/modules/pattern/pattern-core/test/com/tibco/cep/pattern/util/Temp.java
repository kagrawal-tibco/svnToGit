package com.tibco.cep.pattern.util;

import com.tibco.cep.util.annotation.LogCategory;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
* Author: Ashwin Jayaprakash Date: Aug 26, 2009 Time: 2:01:24 PM
*/

@LogCategory("pattern.util.something")
public class Temp {
    public static void main(String[] args) {
        extractLogCategory(Temp.class);
    }

    private static LogCategory extractLogCategory(Class clazz) {
        LogCategory logCategory = (LogCategory) clazz.getAnnotation(LogCategory.class);

        if (logCategory == null) {
            Package pkg = clazz.getPackage();
            pkg.getAnnotation(LogCategory.class);
        }

        return null;
    }

    public static void main123(String[] args) {
        String s = "define pattern /Patterns/PatternAC \n" +
                " using /Ontology/EventA as a, /Ontology/EventC as c \n" +
                " with a.name and c.text = $nameOfC \n" +
                " starts with repeat $minRepeat to $maxRepeat times a \n" +
                "             then within $withinMillis milliseconds c";

        Logger logger = Logger.getLogger(Temp.class.getPackage().getName());
        logger.log(Level.SEVERE, s);
        logger.log(Level.WARNING, s);
        logger.log(Level.FINE, s);
    }
}
