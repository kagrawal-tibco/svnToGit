/**
 * 
 */
package com.tibco.cep.webstudio.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vpatil
 */
public class WSDLEntity {

	protected String name;
	private List<Service> services;
	
	public WSDLEntity() {
		services = new ArrayList<Service>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addService(Service service) {
		services.add(service);
	}
	
	public List<Service> getServices() {
		return this.services;
	}
	
	public Service getServiceByName(String name) {
		for(Service service : services) {
			if (service.getName().equals(name)) {
				return service;
			}
		}
		
		return null;
	}
}
