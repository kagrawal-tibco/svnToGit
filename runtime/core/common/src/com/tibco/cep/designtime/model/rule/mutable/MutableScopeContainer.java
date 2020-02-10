package com.tibco.cep.designtime.model.rule.mutable;

import com.tibco.cep.designtime.model.rule.ScopeContainer;
import com.tibco.cep.designtime.model.rule.Symbols;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Apr 7, 2006
 * Time: 3:52:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MutableScopeContainer extends ScopeContainer {


    void setScope(Symbols m);

}
