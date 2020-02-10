package com.tibco.cep.query.aggregate;

import java.util.Collection;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 11/16/12
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Aggregate {
    Map<Object, Object> aggregate() throws Exception;

    Map<Object, Object> reduce(Collection<Map<Object, Object>> intermediate) throws Exception;
}
