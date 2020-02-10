package com.tibco.be.parser.tree;

import java.util.List;


/**
 * User: nprade
 * Date: 2/6/12
 * Time: 4:00 PM
 */
public interface TemplatedNode
        extends Node {


    List<String> getAccessibleSymbolNames();


    void setAccessibleSymbolNames(
            List<String> accessibleSymbolNames);

}
