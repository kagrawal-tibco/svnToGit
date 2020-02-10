package com.tibco.cep.designtime.model.rule;


import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 11:11:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Symbols extends Map {


    boolean containsType(String typePath);


    String getAvailableIdentifier(String baseName);


    String getType(String identifier);


    LinkedHashSet getTypes();


    List getSymbolsList();


    Symbol getSymbol(String idName);
}
