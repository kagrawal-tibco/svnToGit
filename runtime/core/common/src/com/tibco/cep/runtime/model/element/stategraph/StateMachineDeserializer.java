package com.tibco.cep.runtime.model.element.stategraph;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 8, 2006
 * Time: 8:59:40 AM
 * To change this template use File | Settings | File Templates.
 */
public interface StateMachineDeserializer {
    public final static int STATE_NEW = 0;
    public final static int STATE_MODIFIED = 1;
    public final static int STATE_DELETED = 2;

    public final static int TYPE_STREAM = 0;
    public final static int TYPE_NAMEVALUE = 1;

    /**
     *
     * @param clz
     * @return
     */
    public long startStateMachine();

   /**
    *
    */
    public void endStateMachine();

   /**
    *
    * @return
    */
    public int getType();

    /**
     *
     * @return
     */
    public int getMachineStatus();

    /**
     *
     * @return
     */
    public int startCurrentStates();


    /**
     *
     */
    public void endCurrentStates();

    /**
     *
     * @return
     */
    public String startState();

    /**
     *
     */
    public void endState();


}

