package com.tibco.cep.studio.core.domain.importHandler;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;

/**
 * Value object representing various 
 * basic domain parameters.
 * <p>
 * Used for importing domain model from external source of data.
 * </p>
 * @author aathalye
 *
 */
public class DomainConfiguration {
	
	/**
	 * The data type to be used for importing domain model.
	 */
	private DOMAIN_DATA_TYPES domainDataType;
	
	/**
	 * The name of the domain (Existing/New)
	 */
	private String domainName;
	
	/**
	 * Optional domain description
	 */
	private String domainDescription;
	
	/**
	 * The root project directory under which to create
	 * this new domain or import into existing one.
	 */
	private String projectDirectoryPath;
	
	/**
	 * Folder path in project under which this domain will exist after import
	 */
	private String domainFolderPath;

	/**
	 * @return the domainDataType
	 */
	public final DOMAIN_DATA_TYPES getDomainDataType() {
		return domainDataType;
	}

	/**
	 * @param domainDataType the domainDataType to set
	 */
	public final void setDomainDataType(DOMAIN_DATA_TYPES domainDataType) {
		this.domainDataType = domainDataType;
	}

	/**
	 * @return the domainName
	 */
	public final String getDomainName() {
		return domainName;
	}

	/**
	 * @param domainName the domainName to set
	 */
	public final void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	/**
	 * @return the domainDescription
	 */
	public final String getDomainDescription() {
		return domainDescription;
	}

	/**
	 * @param domainDescription the domainDescription to set
	 */
	public final void setDomainDescription(String domainDescription) {
		this.domainDescription = domainDescription;
	}

	/**
	 * @return the projectDirectoryPath
	 */
	public final String getProjectDirectoryPath() {
		return projectDirectoryPath;
	}

	/**
	 * @param projectDirectoryPath the projectDirectoryPath to set
	 */
	public final void setProjectDirectoryPath(String projectDirectoryPath) {
		this.projectDirectoryPath = projectDirectoryPath;
	}

	/**
	 * @return the domainFolderPath
	 */
	public final String getDomainFolderPath() {
		return domainFolderPath;
	}

	/**
	 * @param domainFolderPath the domainFolderPath to set
	 */
	public final void setDomainFolderPath(String domainFolderPath) {
		this.domainFolderPath = domainFolderPath;
	}
}
