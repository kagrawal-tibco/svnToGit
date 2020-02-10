package com.tibco.cep.util;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/24/11
 * Time: 5:56 PM
 */
public interface Filter<T> {

    boolean accepts(T testedObject);

}
