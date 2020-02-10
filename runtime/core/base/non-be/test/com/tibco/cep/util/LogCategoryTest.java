package com.tibco.cep.util;

import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash Date: Aug 26, 2009 Time: 2:01:24 PM
*/

@LogCategory("pattern.util.something")
public class LogCategoryTest {
    public static void main(String[] args) {
        extractLogCategory(LogCategoryTest.class);
    }

    private static void extractLogCategory(Class clazz) {
        LogCategory logCategory = LogCategoryTest.class.getAnnotation(LogCategory.class);

        Package pkg = clazz.getPackage();
        logCategory = pkg.getAnnotation(LogCategory.class);

        System.out.println("Pkg: " + logCategory.value());
    }
}