package com.tibco.cep.webstudio.client.request.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.cep.webstudio.client.request.model.ISerializableObject;

public abstract class DefaultSerializableObject<S extends ISerializableObject> {
	
	protected List<S> children = new ArrayList<S>();

		
	public List<S> getChildren() {
		return Collections.unmodifiableList(children);
	}
}
