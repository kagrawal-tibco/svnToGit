package com.tibco.cep.bemm.model.impl;

import com.tibco.cep.bemm.model.BE;

/**
 * Implementation of functionality defined in BE interface
 * 
 * @author dijadhav
 *
 */
public class BEImpl implements BE {
	/**
	 * BE home path
	 */
	private String id;
	/**
	 * BE home path
	 */
	private String beHome;
	/**
	 * BE tra path
	 */
	private String beTra;

	/**
	 * BE Version
	 */
	private String version;

	/**
	 * Default Model
	 */
	public BEImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.BE#getBeHome()
	 */
	@Override
	public String getBeHome() {
		return beHome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.BE#setBeHome(java.lang.String)
	 */
	@Override
	public void setBeHome(String beHome) {
		this.beHome = beHome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.BE#getBeTra()
	 */
	@Override
	public String getBeTra() {
		return beTra;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.BE#setBeTra(java.lang.String)
	 */
	@Override
	public void setBeTra(String beTra) {
		this.beTra = beTra;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void setVersion(String version) {
		this.version = version;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beHome == null) ? 0 : beHome.hashCode());
		result = prime * result + ((beTra == null) ? 0 : beTra.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BEImpl other = (BEImpl) obj;
		if (beHome == null) {
			if (other.beHome != null)
				return false;
		} else if (!beHome.equals(other.beHome))
			return false;
		if (beTra == null) {
			if (other.beTra != null)
				return false;
		} else if (!beTra.equals(other.beTra))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}
