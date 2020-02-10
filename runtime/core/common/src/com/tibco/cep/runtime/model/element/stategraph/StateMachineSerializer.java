package com.tibco.cep.runtime.model.element.stategraph;


/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 8, 2006
 * Time: 8:52:33 AM
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
     * @param clz
     * @param key
     */
    public void startStateMachine(Class clz, long key);

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
     * @param status
     */
    public void setMachineStatus(int status);

    /**
     *
     * @param numberOfStates
     */
    public void startCurrentStates(int numberOfStates);


    /**
     *
     */
    public void endCurrentStates();

    /**
     *
     * @param state
     */
    public void startState(String state);

    /**
     *
     */
    public void endState();


}
