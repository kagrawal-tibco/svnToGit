package com.tibco.cep.runtime.hbase.cacheaside.Interfaces;

import com.tibco.cep.runtime.model.element.Concept;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 7/2/13
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Adaptor {

    public void insertConcepts(String className, Map<Long, Concept> entries) throws Exception;

    public void deleteAll() throws Exception;
}
