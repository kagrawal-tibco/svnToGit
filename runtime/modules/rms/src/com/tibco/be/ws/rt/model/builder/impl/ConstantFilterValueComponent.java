package com.tibco.be.ws.rt.model.builder.impl;

import com.tibco.be.ws.rt.model.builder.IFilterComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/4/12
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConstantFilterValueComponent implements IFilterComponent {
    
    private List<Object> children = new ArrayList<Object>();

    public ConstantFilterValueComponent(Object... values) {
        children.addAll(Arrays.asList(values));
    }


    public List<Object> getChildren() {
        return children;
    }

    /**
     * Get unique integer.
     *
     * @return
     */
    @Override
    public int getId() {
        return 0;
    }
}
