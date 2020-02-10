package com.tibco.be.ws.rt.model.builder.impl;

import com.tibco.be.ws.rt.model.builder.IFilterComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/4/12
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class FilterLinkComponent implements IFilterComponent {
    
    private List<String> children = new ArrayList<String>();

    public FilterLinkComponent(String...values) {
        //link name and type will be children
        children.addAll(Arrays.asList(values));
    }

    @Override
    public List<String> getChildren() {
        return children;
    }

    /**
     * Get unique integer.
     *
     * @return
     */
    @Override
    public int getId() {
        return 1;
    }
}
