package com.tibco.cep.annotations;

/**
 * User: nprade
 * Date: 4/27/12
 * Time: 1:57 PM
 */
public class AnnotationHelper {


    static String escape(
            String text) {
        if (null == text) {
            return "";
        }
        else {
            return text.trim().replaceAll("([\"\\\\])", "\\$1").replaceAll("\n", "\\\\n");
        }
    }
}
