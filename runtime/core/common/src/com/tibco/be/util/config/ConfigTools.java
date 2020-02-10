package com.tibco.be.util.config;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;


public class ConfigTools implements Serializable {


    public static Properties replaceInKeys(
            Map<Object, Object> input,
            String keyword,
            String replacement) {

        final Properties output = new Properties();
        final String quotedKeyword = Pattern.quote(keyword);

        for (final Map.Entry<Object, Object> entry : input.entrySet()) {
            final Object k = entry.getKey();
            if (k instanceof String) {
                output.put(((String) k).replaceAll(quotedKeyword, replacement), entry.getValue());
            } else {
                output.put(k, entry.getValue());
            }
        }

        return output;
    }


    public static Properties addSuffixToKeys(
    		Map<Object, Object> input,
            String suffix) {

        final Properties output = new Properties();

        for (final Map.Entry<Object, Object> entry : input.entrySet()) {
            final Object k = entry.getKey();
            if (k instanceof String) { //TODO???
                output.put(k + suffix, entry.getValue());
            } else {
                output.put(k, entry.getValue());
            }
        }

        return output;
    }



}