package com.tibco.cep.webstudio.client.process.properties.general;

/**
 * This class holds the properties of gateways.
 * 
 * @author dijadhav
 * 
 */
public class GatewayGeneralProperty extends GeneralProperty {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -1652120275761623857L;

	/**
	 * Outgoing sequence separated by comma.
	 */
	private String outgoing;

	/**
	 * Default sequence id.
	 */
	private String defaultSequenceId;

	public String getOutgoing() {
		return outgoing;
	}

	public void setOutgoing(String outgoing) {
		this.outgoing = outgoing;
	}

	public String getDefaultSequenceId() {
		return defaultSequenceId;
	}

	public void setDefaultSequenceId(String defaultSequenceId) {
		this.defaultSequenceId = defaultSequenceId;
	}
}
