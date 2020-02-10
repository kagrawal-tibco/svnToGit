package com.tibco.be.ws.rt.model.builder;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/4/12
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IFilterComponent {

    /**
     * Get child elements
     * @param <T>
     * @return
     */
    public <T> List<T> getChildren();

    /**
     * Get unique integer.
     * @return
     */
    public int getId();
}
