package com.tibco.cep.runtime.model.element;

import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 7, 2009
 * Time: 9:34:07 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StateMachineDeserializer {
     public final static int STATE_NEW = 0;
     public final static int STATE_MODIFIED = 1;
     public final static int STATE_DELETED = 2;

     public final static int TYPE_STREAM = 0;
     public final static int TYPE_NAMEVALUE = 1;


    public boolean isDeleted();
    /**
     *
     * @return
     */
    public long startStateMachine();

    public void endStateMachine();
    /**
     *
     * @return
     */
    public String getExtId();

    /**
     *
     * @return
     */
    public long getId();

    /**
     *
     * @return
     */
    public int getVersion();

    /**
     *
     * @return parent
     */
    public ConceptOrReference getParentConcept();


    public int nextStateIndex();

    public int nextStateValue();

}


