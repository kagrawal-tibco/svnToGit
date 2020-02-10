package com.tibco.cep.loadbalancer.util;

import java.io.IOException;
import java.io.Writer;

/*
* Author: Ashwin Jayaprakash / Date: Apr 2, 2010 / Time: 3:46:02 PM
*/
public class XmlHelper {
    public static void toXml(Object object, Writer writer) {
        String placeHolder = object.toString();
        try {
            writer.append(placeHolder);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}