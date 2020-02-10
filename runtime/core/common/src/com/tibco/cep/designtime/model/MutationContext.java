package com.tibco.cep.designtime.model;


import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 6, 2006
 * Time: 4:45:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MutationContext {


    public static byte ADD = 0;
    public static byte DELETE = 1;
    public static byte MODIFY = 2;
    public static byte RENAME = 3;


    Object getMutatedObject();


    byte getMutationType();


    Map getChanges();


}