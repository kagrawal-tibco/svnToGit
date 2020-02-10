package com.tibco.cep.studio.wizard.ftl.model;

import java.util.Properties;

public class FTLDataModel {
	private String appName;
	
	private String endpointName;
	private String instanceName;
//	private String messageType;
//	private String buildInFormatType;
	private String formatName;
	private String durableName;
//	private Map<String, Properties> formatMap = new HashMap<String, Properties>();
	private Properties formatProp; 
	
	private String transport;
	
//	private boolean isReceiveVisible,  isSendVisible,  isReceiveInboxVisible,  isSendInboxVisible;
	private String destName;
	private String eventFolderName;
	private String recvEventName;
	private String sendEventName;
//	private String receiveInboxEventName;
//	private String sendInboxEventName;
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getEndpointName() {
		return endpointName;
	}
	public void setEndpointName(String endpointName) {
		this.endpointName = endpointName;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
//	public String getMessageType() {
//		return messageType;
//	}
//	public void setMessageType(String messageType) {
//		this.messageType = messageType;
//	}
	public String getFormatName() {
		return formatName;
	}
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}
	public String getDurableName() {
		return durableName;
	}
	public void setDurableName(String durableName) {
		this.durableName = durableName;
	}
	public Properties getFormatProp() {
		return formatProp;
	}
	public void setFormatProp(Properties formatProp) {
		this.formatProp = formatProp;
	}
	public String getTransport() {
		return transport;
	}
	public void setTransport(String transport) {
		this.transport = transport;
	}
	public String getDestName() {
		return destName;
	}
	public void setDestName(String destName) {
		this.destName = destName;
	}
	public String getEventFolderName() {
		return eventFolderName;
	}
	public void setEventFolderName(String eventFolderName) {
		this.eventFolderName = eventFolderName;
	}
	public String getRecvEventName() {
		return recvEventName;
	}
	public void setRecvEventName(String recvEventName) {
		this.recvEventName = recvEventName;
	}
	public String getSendEventName() {
		return sendEventName;
	}
	public void setSendEventName(String sendEventName) {
		this.sendEventName = sendEventName;
	}
//	public String getReceiveInboxEventName() {
//		return receiveInboxEventName;
//	}
//	public void setReceiveInboxEventName(String receiveInboxEventName) {
//		this.receiveInboxEventName = receiveInboxEventName;
//	}
//	public String getSendInboxEventName() {
//		return sendInboxEventName;
//	}
//	public void setSendInboxEventName(String sendInboxEventName) {
//		this.sendInboxEventName = sendInboxEventName;
//	}
//	public boolean isReceiveVisible() {
//		return isReceiveVisible;
//	}
//	public void setReceiveVisible(boolean isReceiveVisible) {
//		this.isReceiveVisible = isReceiveVisible;
//	}
//	public boolean isSendVisible() {
//		return isSendVisible;
//	}
//	public void setSendVisible(boolean isSendVisible) {
//		this.isSendVisible = isSendVisible;
//	}
//	public boolean isReceiveInboxVisible() {
//		return isReceiveInboxVisible;
//	}
//	public void setReceiveInboxVisible(boolean isReceiveInboxVisible) {
//		this.isReceiveInboxVisible = isReceiveInboxVisible;
//	}
//	public boolean isSendInboxVisible() {
//		return isSendInboxVisible;
//	}
//	public void setSendInboxVisible(boolean isSendInboxVisible) {
//		this.isSendInboxVisible = isSendInboxVisible;
//	}
//	
//	public String getBuildInFormatType() {
//		return buildInFormatType;
//	}
//	public void setBuildInFormatType(String buildInFormatType) {
//		this.buildInFormatType = buildInFormatType;
//	}

	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appName == null) ? 0 : appName.hashCode());
		result = prime * result
				+ ((destName == null) ? 0 : destName.hashCode());
		result = prime * result
				+ ((endpointName == null) ? 0 : endpointName.hashCode());
		result = prime * result
				+ ((eventFolderName == null) ? 0 : eventFolderName.hashCode());
		result = prime * result
				+ ((formatName == null) ? 0 : formatName.hashCode());
		result = prime * result
				+ ((formatProp == null) ? 0 : formatProp.hashCode());
		result = prime * result
				+ ((instanceName == null) ? 0 : instanceName.hashCode());
		result = prime * result
				+ ((recvEventName == null) ? 0 : recvEventName.hashCode());
		result = prime * result
				+ ((sendEventName == null) ? 0 : sendEventName.hashCode());
		result = prime * result
				+ ((transport == null) ? 0 : transport.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FTLDataModel other = (FTLDataModel) obj;
		if (appName == null) {
			if (other.appName != null)
				return false;
		} else if (!appName.equals(other.appName))
			return false;

		if (destName == null) {
			if (other.destName != null)
				return false;
		} else if (!destName.equals(other.destName))
			return false;
		if (endpointName == null) {
			if (other.endpointName != null)
				return false;
		} else if (!endpointName.equals(other.endpointName))
			return false;
		if (eventFolderName == null) {
			if (other.eventFolderName != null)
				return false;
		} else if (!eventFolderName.equals(other.eventFolderName))
			return false;
		if (formatName == null) {
			if (other.formatName != null)
				return false;
		} else if (!formatName.equals(other.formatName))
			return false;
		if (durableName == null) {
			if (other.durableName != null)
				return false;
		} else if (!durableName.equals(other.durableName))
			return false;
		if (formatProp == null) {
			if (other.formatProp != null)
				return false;
		} else if (!formatProp.equals(other.formatProp))
			return false;
		if (instanceName == null) {
			if (other.instanceName != null)
				return false;
		} else if (!instanceName.equals(other.instanceName))
		
		if (recvEventName == null) {
			if (other.recvEventName != null)
				return false;
		} else if (!recvEventName.equals(other.recvEventName))
			return false;
		if (sendEventName == null) {
			if (other.sendEventName != null)
				return false;
		} else if (!sendEventName.equals(other.sendEventName))
			return false;
		if (transport == null) {
			if (other.transport != null)
				return false;
		} else if (!transport.equals(other.transport))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "FTLDataModel [appName=" + appName + ", endpointName="
				+ endpointName + ", instanceName=" + instanceName
				+ ", formatName=" + formatName
				+ "]";
	}
	
	
	
	
	
	
	
	
	

}
