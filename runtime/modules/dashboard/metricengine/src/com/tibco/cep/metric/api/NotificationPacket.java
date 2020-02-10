package com.tibco.cep.metric.api;

import java.util.List;

import com.tibco.cep.runtime.model.element.Concept;

public class NotificationPacket {

	public static enum NotificationType {
		CREATE, UPDATE, DELETE, UNUSABLE
	};

	private NotificationType type;

	private List<String> subscriptions;

	//following variables are for CREATE/UPDATE/UNUSABLE
	private Concept concept;

	//following variables are for DELETE
	private String uri;
	private long id;
	private String extId;

	public NotificationPacket(NotificationType type, List<String> subscriptions, String uri, Concept concept) {
		super();
		if (type.compareTo(NotificationType.DELETE) == 0) {
			throw new IllegalArgumentException(type.toString());
		}
		this.type = type;
		this.subscriptions = subscriptions;
		this.concept = concept;
		this.uri = uri;
	}

	public NotificationPacket(List<String> subscriptions, String uri, long id, String extId) {
		super();
		this.type = NotificationType.DELETE;
		this.subscriptions = subscriptions;
		this.uri = uri;
		this.id = id;
		this.extId = extId;
	}

	public NotificationType getType() {
		return type;
	}

	public List<String> getSubscriptions() {
		return subscriptions;
	}

	public Concept getConcept() {
		return concept;
	}

	public String getURI() {
		return uri;
	}

	public long getId() {
		if (concept != null) {
			return concept.getId();
		}
		return id;
	}

	public String getExtId() {
		if (concept != null) {
			return concept.getExtId();
		}
		return extId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("NotificationPacket[");
		sb.append("type=");
		sb.append(type);
		sb.append(",subscriptions=");
		sb.append(subscriptions);
		if (concept != null) {
			sb.append(",uri=");
			sb.append(uri);
			sb.append(",concept=");
			sb.append(concept);
		}
		else {
			sb.append(",uri=");
			sb.append(uri);
			sb.append(",id=");
			sb.append(id);
			sb.append(",extId=");
			sb.append(extId);
		}
		return sb.toString();
	}

}
