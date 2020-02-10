package com.tibco.cep.releases.bom.impl;

import com.tibco.cep.releases.bom.Assemblies;
import com.tibco.cep.releases.bom.Assembly;

import java.util.Map;
import java.util.SortedMap;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/28/11
 * Time: 5:58 PM
 */
public class AssembliesImpl
        extends AbstractPathContainer<Assembly>
        implements Assemblies {


    public AssembliesImpl(
            Map<String, Assembly> stringAssemblyMap) {

        super(stringAssemblyMap);
    }


    public AssembliesImpl(
            SortedMap<String, Assembly> stringAssemblySortedMap) {

        super(stringAssemblySortedMap);
    }

}
