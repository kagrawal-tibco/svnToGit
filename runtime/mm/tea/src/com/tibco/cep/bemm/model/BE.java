package com.tibco.cep.bemm.model;

/**
 * Provides contract for BE home and tra
 * 
 * @author dijadhav
 *
 */
public interface BE {
	/**
	 * Get id of BE home
	 * 
	 * @return the id
	 */
	String getId();

	/**
	 * Set id of BE home
	 * 
	 * @param id
	 *            Id of BE home
	 */
	void setId(String id);

	/**
	 * @return the beHome
	 */
	String getBeHome();

	/**
	 * @param beHome
	 *            the beHome to set
	 */
	void setBeHome(String beHome);

	/**
	 * @return the beTra
	 */
	String getBeTra();

	/**
	 * @param beTra
	 *            the beTra to set
	 */
	void setBeTra(String beTra);

	/**
	 * @return the Version
	 */
	String getVersion();

	/**
	 * @param version
	 *            the version to set
	 */
	void setVersion(String version);

}
