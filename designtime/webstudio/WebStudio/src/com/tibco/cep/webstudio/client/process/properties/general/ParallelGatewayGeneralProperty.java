package com.tibco.cep.webstudio.client.process.properties.general;

import com.tibco.cep.webstudio.client.process.properties.ParallelGatewayProperty;

/**
 * This class holds the properties of parallel gateways.
 * 
 * @author dijadhav
 * 
 */
public class ParallelGatewayGeneralProperty extends GatewayGeneralProperty implements ParallelGatewayProperty{
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -8545459957549541882L;
	/**
	 * Variable for merge expression.
	 */
	private String mergeExpression;

	/**
	 * Variable for join function.
	 */
	private String joinfunction;

	/**
	 * Variable for for function.
	 */
	private String forkFunction;	
	/**
	 * Incomming sequence separated by comma.
	 */
	private String incomming;
	

	/**
	 * @return the mergeExpression
	 */
	public String getMergeExpression() {
		return mergeExpression;
	}

	/**
	 * @param mergeExpression
	 *            the mergeExpression to set
	 */
	public void setMergeExpression(String mergeExpression) {
		this.mergeExpression = mergeExpression;
	}

	/**
	 * @return the joinfunction
	 */
	public String getJoinfunction() {
		return joinfunction;
	}

	/**
	 * @param joinfunction
	 *            the joinfunction to set
	 */
	public void setJoinfunction(String joinfunction) {
		this.joinfunction = joinfunction;
	}

	/**
	 * @return the forkFunction
	 */
	public String getForkFunction() {
		return forkFunction;
	}

	/**
	 * @param forkFunction
	 *            the forkFunction to set
	 */
	public void setForkFunction(String forkFunction) {
		this.forkFunction = forkFunction;
	}

/**
	 * @return the incomming
	 */
	public String getIncomming() {
		return incomming;
	}

	/**
	 * @param incomming the incomming to set
	 */
	public void setIncomming(String incomming) {
		this.incomming = incomming;
	}
}
