package com.tibco.cep.security.tokens.impl;

import java.util.Date;

import com.tibco.cep.security.tokens.Authen;
import com.tibco.cep.security.tokens.User;

public class AuthenImpl implements Authen {

	private static final long serialVersionUID = 4363968831361095153L;

	private User user;
	private Date authInstant;
	private String authenticatedBy;
	private String sessionToken;
	private int timeToLive;

	protected AuthenImpl() {
		timeToLive = -1;
	}

	@Override
	public void setUser(User value) {
		this.user = value;
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public void setAuthInstant(Date value) {
		this.authInstant = value;
	}

	@Override
	public Date getAuthInstant() {
		return authInstant;
	}

	@Override
	public void setAuthenticatedBy(String value) {
		this.authenticatedBy = value;
	}

	@Override
	public String getAuthenticatedBy() {
		return authenticatedBy;
	}

	@Override
	public void setSessionToken(String value) {
		this.sessionToken = value;
	}

	@Override
	public String getSessionToken() {
		return sessionToken;
	}

	@Override
	public void setTimeToLive(int value) {
		this.timeToLive = value;
	}

	@Override
	public int getTimeToLive() {
		return timeToLive;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthenImpl other = (AuthenImpl) obj;
		if (authInstant == null) {
			if (other.authInstant != null)
				return false;
		} else if (!authInstant.equals(other.authInstant))
			return false;
		if (authenticatedBy == null) {
			if (other.authenticatedBy != null)
				return false;
		} else if (!authenticatedBy.equals(other.authenticatedBy))
			return false;
		if (sessionToken == null) {
			if (other.sessionToken != null)
				return false;
		} else if (!sessionToken.equals(other.sessionToken))
			return false;
		if (timeToLive != other.timeToLive)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(Authen.class.getSimpleName());
		sb.append("[");
		sb.append("authInstant="+authInstant);
		sb.append(",authenticatedBy="+authenticatedBy);
		sb.append(",sessionToken="+sessionToken);
		sb.append(",timeToLive="+timeToLive);
		sb.append(",user="+user);
		sb.append("]");
		return sb.toString();
	}
}
