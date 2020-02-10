package com.tibco.cep.bpmn.runtime.activity;

import com.tibco.cep.bpmn.runtime.agent.Job;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Nov 19, 2011
 * Time: 7:27:24 AM
 * To change this template use File | Settings | File Templates.
 */

public interface Transition {

    /**
     *
     * @return  the fully qualified name of the Transition
     */
    String getName();

    /**
     * evaluate the Transition
     * @param context
     * @return
     */
    boolean eval(Job context);

    /**
     * Task that the transition originated from.
     * @return
     */
    Task fromTask();

    /**
     * which task is the transition ending to
     * @return
     */
    Task toTask();

	/**
	 * @param ctx
	 * @param
	 * @param args
	 */
	void init(InitContext ctx,  Object...args) throws Exception;

    boolean isDefault();



}
