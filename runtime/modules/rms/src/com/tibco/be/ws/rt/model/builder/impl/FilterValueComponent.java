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
public class FilterValueComponent implements IFilterComponent {

    private List<IFilterComponent> children = new ArrayList<IFilterComponent>();

    public FilterValueComponent(IFilterComponent...values) {
        //constant value or links will be children
        children.addAll(Arrays.asList(values));
    }

    @Override
    public List<IFilterComponent> getChildren() {
        return children;
    }

    /**
     * Get unique integer.
     *
     * @return
     */
    @Override
    public int getId() {
        return 2;
    }
}
