package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.runtime.model.element.stategraph.CompositeStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.SimpleStateVertex;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Oct 17, 2004
 * Time: 1:48:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubMachineStateImpl extends CompositeStateVertexImpl {

    private String subMachineName;

    public SubMachineStateImpl(String name) {
        super(name);
        throw new RuntimeException("Cannot use this constructor");
    }
    public SubMachineStateImpl(String name, CompositeStateVertexImpl superState, boolean hasHistoryState, boolean isConcurrent) {
        super(name,superState,false,false);
//        isTop = true;
    }

    public boolean isSubMachine() {
        return true;
    }

    public void setSubMachineName(String name) {
        this.subMachineName = name;
    }

    public SimpleStateVertex getDefaultState() {
        return ((CompositeStateVertex)this.subStates[0]).getDefaultState();    
    }










}
