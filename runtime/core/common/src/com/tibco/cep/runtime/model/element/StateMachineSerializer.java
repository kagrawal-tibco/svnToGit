package com.tibco.cep.runtime.model.element;

import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 6, 2009
 * Time: 5:52:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StateMachineSerializer {
     public final static int STATE_NEW = 0;
     public final static int STATE_MODIFIED = 1;
     public final static int STATE_DELETED = 2;

     public final static int TYPE_STREAM = 0;
     public final static int TYPE_NAMEVALUE = 1;

    /**
     *
     * @param key
     * @param extKey
     */
    public void startStateMachine(Class clz, long key, String extKey, int state, int version);

    public void startParent(ConceptOrReference parent);

    /**
     *
     */
    public void endStateMachine();

    /**
     *
     * @return
     */
    public int getType();

    public void addState(int stateIndex, int stateValue);

}

