package com.tibco.cep.webstudio.client.model;

import java.util.ArrayList;

public class NotificationDetails {

	private ArrayList<EmailPreference> emailPreference;
	public ArrayList<EmailPreference> getEmailPreference() {
		return emailPreference;
	}
	public void setEmailPreference(ArrayList<EmailPreference> emailPreference) {
		this.emailPreference = emailPreference;
	}
}
