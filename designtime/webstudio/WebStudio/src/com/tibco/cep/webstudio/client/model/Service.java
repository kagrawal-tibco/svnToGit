/**
 * 
 */
package com.tibco.cep.webstudio.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author vpatil
 */
public class Service extends WSDLEntity {

	private List<Port> ports;

	public Service() {
		super();
		ports = new ArrayList<Port>();
	}
	
	public void addPort(Port port) {
		ports.add(port);
	}
	
	public List<Port> getPorts() {
		return this.ports;
	}
	
	public Port getPortByName(String name) {
		for(Port port : ports) {
			if (port.getName().equals(name)) {
				return port;
			}
		}
		return null;
	}
}
