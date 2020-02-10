package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAtomicSimpleTypeDefinition;

/**
 */
public class SynXSDAtomicSimpleTypeDefinition extends SynXSDSimpleTypeDefinition implements ISynXSDAtomicSimpleTypeDefinition {

	private Class<?> javaType;

	public SynXSDAtomicSimpleTypeDefinition(Class<?> javaType) {
		super(javaType.getName());
		this.javaType = javaType;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	public boolean isValid() throws Exception {
		return true;
	}

	public Object cloneThis() throws Exception {
		SynXSDAtomicSimpleTypeDefinition clone = new SynXSDAtomicSimpleTypeDefinition(this.javaType);
		super.cloneThis(clone);
		return clone;
	}
}
