package com.tibco.cep.releases.bom.impl;

import com.tibco.cep.releases.bom.AddOn;
import com.tibco.cep.releases.bom.AddOns;

import java.util.Map;
import java.util.SortedMap;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/28/11
 * Time: 5:58 PM
 */
public class AddOnsImpl
        extends AbstractPathContainer<AddOn>
        implements AddOns {


    public AddOnsImpl(
            Map<String, AddOn> map) {

        super(map);
    }


    public AddOnsImpl(
            SortedMap<String, AddOn> map) {

        super(map);
    }

}
