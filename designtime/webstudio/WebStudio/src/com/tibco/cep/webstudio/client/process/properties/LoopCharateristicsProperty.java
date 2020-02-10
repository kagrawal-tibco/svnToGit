package com.tibco.cep.webstudio.client.process.properties;

/**
 * This class is used to hold the loop characteristics of task
 * 
 * @author dijadhav
 * 
 */
public class LoopCharateristicsProperty extends Property {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5564751144784461070L;
	private boolean isSequential;
	private boolean testBefore;
	private String type;
	private String loopCount;
	private String behaviour;
	private String cardinalityBody;
	private String loopCondition;
	private String conditionBody;

	/**
	 * @return the isSequential
	 */
	public boolean isSequential() {
		return isSequential;
	}

	/**
	 * @param isSequential
	 *            the isSequential to set
	 */
	public void setSequential(boolean isSequential) {
		this.isSequential = isSequential;
	}

	/**
	 * @return the testBefore
	 */
	public boolean isTestBefore() {
		return testBefore;
	}

	/**
	 * @param testBefore
	 *            the testBefore to set
	 */
	public void setTestBefore(boolean testBefore) {
		this.testBefore = testBefore;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the loopCount
	 */
	public String getLoopCount() {
		return loopCount;
	}

	/**
	 * @param loopCount
	 *            the loopCount to set
	 */
	public void setLoopCount(String loopCount) {
		this.loopCount = loopCount;
	}

	/**
	 * @return the behaviour
	 */
	public String getBehaviour() {
		return behaviour;
	}

	/**
	 * @param behaviour
	 *            the behaviour to set
	 */
	public void setBehaviour(String behaviour) {
		this.behaviour = behaviour;
	}

	/**
	 * @return the cardinalityBody
	 */
	public String getCardinalityBody() {
		return cardinalityBody;
	}

	/**
	 * @param cardinalityBody
	 *            the cardinalityBody to set
	 */
	public void setCardinalityBody(String cardinalityBody) {
		this.cardinalityBody = cardinalityBody;
	}

	/**
	 * @return the loopCondition
	 */
	public String getLoopCondition() {
		return loopCondition;
	}

	/**
	 * @param loopCondition the loopCondition to set
	 */
	public void setLoopCondition(String loopCondition) {
		this.loopCondition = loopCondition;
	}

	/**
	 * @return the conditionBody
	 */
	public String getConditionBody() {
		return conditionBody;
	}

	/**
	 * @param conditionBody
	 *            the conditionBody to set
	 */
	public void setConditionBody(String conditionBody) {
		this.conditionBody = conditionBody;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
