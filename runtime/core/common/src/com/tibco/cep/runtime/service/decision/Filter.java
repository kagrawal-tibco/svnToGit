package com.tibco.cep.runtime.service.decision;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Aug 2, 2008
 * Time: 10:07:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Filter {
   void eval(Object o, HashSet result);
    void addCell(Cell c);

    int size();
}
