package com.tibco.tea.agent.be.ui.model.impl;

import com.tibco.tea.agent.be.ui.model.BE;

/**
 * Implementation of functionality defined in interface
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
	 * BE Home path
	 */
	private String beHome;

	/**
	 * BE TRA path
	 */
	private String beTRA;
	
	/**
	 * BE Version
	 */
	private String version;

	/**
	 * Default contrucutor
	 */
	public BEImpl() {
	}

	@Override
	public String getBeHome() {
		return beHome;
	}

	@Override
	public void setBeHome(String beHome) {
		this.beHome = beHome;
	}

	@Override
	public String getBeTra() {
		return beTRA;
	}

	@Override
	public void setBeTra(String beTra) {
		this.beTRA = beTra;
	}


	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id=id;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void setVersion(String version) {
		this.version=version;
	}
}
