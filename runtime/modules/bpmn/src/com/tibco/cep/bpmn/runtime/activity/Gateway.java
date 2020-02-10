package com.tibco.cep.bpmn.runtime.activity;


/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Nov 19, 2011
 * Time: 7:41:30 AM
 * To change this template use File | Settings | File Templates.
 */

public interface Gateway extends Task {
	public static final int CONVERGING = 1;
	public static final int DIVERGING = 2;
	public static final int MIXED = CONVERGING | DIVERGING ;

	int getDirection();
	
	boolean isConverging();
	
	boolean isDiverging();
	
	boolean isMixed();

    boolean isEventBased();

    short getActivationCount();

    short getOutputTokenCount();

    /**
     * Return a XPath String on which token can be merged. Exclusive gateway returns null.
     * @return
     */
    String getJoinExpression();






    
    
}
