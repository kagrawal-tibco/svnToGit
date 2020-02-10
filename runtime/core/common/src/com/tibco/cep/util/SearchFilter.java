package com.tibco.cep.util;




/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Apr 10, 2006
 * Time: 1:35:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SearchFilter {


    public static final int NOT_ACCEPTED = 0;
    public static final int NOT_ACCEPTED_AND_SEARCH_ONLY_THIS_LEVEL = 100;
    public static final int ACCEPTED_AND_SEARCH_ONLY_THIS_LEVEL = 200;
    public static final int ACCEPTED_AND_KEEP_SEARCHING = 300;
    public static final int ACCEPTED_AND_BREAK = 400;
    public static final int STOP = 999;


    int accept(Node n);
}
