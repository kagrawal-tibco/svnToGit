package com.tibco.cep.webstudio.client.process.properties.general;

import com.tibco.cep.webstudio.client.process.properties.SubProcessProperty;

/**
 * This class holds the general properties of subprocess.
 * 
 * @author dijadhav
 * 
 */
public class SubprocessGeneralProperty extends GeneralProperty implements SubProcessProperty{
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 4967716436212165454L;
	/**
	 * Boolean value which indicates the checkpoint is enable of not.
	 */
	private boolean isCheckPoint;

	/**
	 * Boolean value which indicates trigger by event is enabled or not.
	 */
	private boolean triggerByEvent;

	/**
	 * @return the isCheckPoint
	 */
	public boolean isCheckPoint() {
		return isCheckPoint;
	}

	/**
	 * @param isCheckPoint the isCheckPoint to set
	 */
	public void setCheckPoint(boolean isCheckPoint) {
		this.isCheckPoint = isCheckPoint;
	}

	/**
	 * @return the triggerByEvent
	 */
	public boolean isTriggerByEvent() {
		return triggerByEvent;
	}

	/**
	 * @param triggerByEvent the triggerByEvent to set
	 */
	public void setTriggerByEvent(boolean triggerByEvent) {
		this.triggerByEvent = triggerByEvent;
	}
}
