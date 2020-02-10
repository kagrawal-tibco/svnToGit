package com.tibco.cep.bemm.model.impl;

import com.tibco.cep.bemm.model.Summary;

/**
 * This method is used to get the summary details.
 * 
 * @author dijadhav
 *
 */
public class SummaryImpl implements Summary {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5004462580481952253L;
	/**
	 * Number of running instances
	 */
	private int upInstances = 0;
	/**
	 * Number of down instances.
	 */
	private int downInstances = 0;
	/**
	 * Number of intermediate state instances.
	 */
	private int intermediateStateInstances = 0;

	@Override
	public int getUpInstances() {
		return upInstances;
	}

	@Override
	public void setUpInstances(int upInstances) {
		this.upInstances = upInstances;
	}

	@Override
	public int getDownInstances() {
		return downInstances;
	}

	@Override
	public void setDownInstances(int downInstances) {
		this.downInstances = downInstances;

	}

	@Override
	public int getIntermediateStateInstances() {
		return intermediateStateInstances;
	}

	@Override
	public void setIntermediateStateInstances(int intermediateStateInstances) {
		this.intermediateStateInstances=intermediateStateInstances;
	}

}
