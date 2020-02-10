/**
 * 
 */
package com.tibco.cep.studio.decision.table.utils;

/**
 * @author aathalye
 *
 */
public class RuleIDGeneratorKey {
	
	private String project;
	
	private String tablePath;
	
	public RuleIDGeneratorKey(String project, String tablePath) {
		this.project = project;
		this.tablePath = tablePath;
	}

	/**
	 * @return the project
	 */
	public final String getProject() {
		return project;
	}

	/**
	 * @return the tablePath
	 */
	public final String getTablePath() {
		return tablePath;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof RuleIDGeneratorKey)) {
			return false;
		}
		RuleIDGeneratorKey other = (RuleIDGeneratorKey)obj;
		if (!this.project.equals(other.getProject())) {
			return false;
		}
		if (!this.tablePath.equals(other.getTablePath())) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return project.hashCode() * tablePath.hashCode();
	}
}
