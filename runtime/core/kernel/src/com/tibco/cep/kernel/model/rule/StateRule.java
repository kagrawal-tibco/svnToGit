package com.tibco.cep.kernel.model.rule;

import com.tibco.cep.kernel.model.entity.Mutable;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Mar 31, 2009
 * Time: 10:16:47 AM
 * To change this template use File | Settings | File Templates.
 */
public interface StateRule extends Rule{
    public Identifier getTarget();
//    public RuleImpl.DependencyIndex[] getChildDependencies();
//    public RuleImpl.DependencyIndex[] getStateIndexDependencies();
    public boolean checkDependency(Mutable obj, int [] bitMap);
}
