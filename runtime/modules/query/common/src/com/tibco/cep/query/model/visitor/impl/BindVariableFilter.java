package com.tibco.cep.query.model.visitor.impl;

import com.tibco.cep.query.model.BindVariable;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.visitor.ContextFilter;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Feb 15, 2008
 * Time: 3:12:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class BindVariableFilter implements ContextFilter {


    public boolean canVisit(ModelContext node) {
       return ((node instanceof BindVariable) && !((BindVariable)node).isBindingResolved());
   }


}
