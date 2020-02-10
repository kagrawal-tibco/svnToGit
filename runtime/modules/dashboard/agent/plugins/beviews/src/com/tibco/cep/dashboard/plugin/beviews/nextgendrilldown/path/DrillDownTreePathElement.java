package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path;

public abstract class DrillDownTreePathElement {
	
	protected String token;
	
	protected DrillDownTreePathElement(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DrillDownTreePathElement other = (DrillDownTreePathElement) obj;
		if (token == null) {
			if (other.token != null) {
				return false;
			}
		} else if (!token.equals(other.token)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return token;
	}
	
}
