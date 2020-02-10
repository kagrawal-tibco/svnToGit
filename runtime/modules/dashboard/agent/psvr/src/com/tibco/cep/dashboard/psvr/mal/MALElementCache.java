package com.tibco.cep.dashboard.psvr.mal;

import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.psvr.mal.model.MALElement;

/**
 * @author apatil
 *
 */
public abstract class MALElementCache<ME extends MALElement> extends CacheController<ME> {

	protected MALSession malsession;

	protected MALElementCache(String name, String descriptiveName) {
		super(name, descriptiveName);
	}

	protected void setMALSession(MALSession malsession) {
		this.malsession = malsession;
	}

	/**
	 * @param componentType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<ME> getAllSpecificComponentConfigs(String componentType) throws MALException {
		if (componentType == null) {
			throw new IllegalArgumentException("component type cannot be null");
		}
		if (malsession == null) {
			throw new IllegalStateException("malsession cannot be null");
		}
		return (List<ME>) malsession.getElementsByType(componentType);
	}

	/**
	 * @param componentType
	 * @return
	 * @throws Exception
	 */
	protected List<ME> getAllSpecificComponentConfigs(List<String> componentTypes) throws MALException {
		if (componentTypes == null) {
			throw new IllegalArgumentException("component types cannot be null");
		}
		if (malsession == null) {
			throw new IllegalStateException("malsession cannot be null");
		}
		List<ME> elements = new LinkedList<ME>();
		for (String componentType : componentTypes) {
			elements.addAll(getAllSpecificComponentConfigs(componentType));
		}
		return elements;
	}

}