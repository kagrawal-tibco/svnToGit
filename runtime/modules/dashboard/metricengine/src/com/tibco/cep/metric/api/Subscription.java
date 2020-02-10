package com.tibco.cep.metric.api;

/**
 * Support for metric and filter Author: Anil Jeswani / Date: Mar 29, 2010 /
 * Time: 2:53:26 PM
 */
public class Subscription {

	/**
	 * Name of subscription
	 */
	private String subscriptionName;
	/**
	 * Ontology name of the metric
	 */
	private String metricName;
	/**
	 * Filter
	 */
	private String condition;

	/**
	 * Subscription class
	 * 
	 * @param name
	 *            name of subscription
	 * @param metricName
	 *            ontology name of metric
	 * @param condition
	 *            filter
	 */
	public Subscription(final String name, final String metricName,
			final String condition) {
		this.subscriptionName = name;
		this.metricName = metricName;
		this.condition = condition;
	}

	/**
	 * Returns subscription name
	 * 
	 * @return name
	 */
	public final String getName() {
		return subscriptionName;
	}

	/**
	 * Returns metric name
	 * 
	 * @return name
	 */
	public final String getMetricName() {
		return metricName;
	}

	/**
	 * Returns filter
	 * 
	 * @return condition
	 */
	public final String getCondition() {
		return condition;
	}

	/**
	 * Sets subscription name
	 * 
	 * @param name
	 *            name
	 */
	public final void setName(final String name) {
		this.subscriptionName = name;
	}

	/**
	 * Sets subscription name
	 * 
	 * @param metricName
	 *            name
	 */
	public final void setMetricName(final String metricName) {
		this.metricName = metricName;
	}

	/**
	 * Sets subscription condition
	 * 
	 * @param condition
	 *            condition
	 */
	public final void setCondition(final String condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Subscription [subscriptionName=");
		builder.append(subscriptionName);
		builder.append(", metricName=");
		builder.append(metricName);
		builder.append(", condition=");
		builder.append(condition);
		builder.append("]");
		return builder.toString();
	}
	
}
