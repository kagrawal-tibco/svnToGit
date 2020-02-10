package com.tibco.cep.studio.mapper.core;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.xml.mapperui.emfapi.EMapperControlArgs;

public class BEMapperControlArgs extends EMapperControlArgs {

	private List<EObject> sourceElements;
	private Entity targetElement;
	private MapperInvocationContext mContext;

	public BEMapperControlArgs() {
		super();
	}

	public void setSourceElements(List<EObject> sourceElements) {
		this.sourceElements = sourceElements;
	}

	public void setTargetElement(Entity targetEntity) {
		this.targetElement = targetEntity;
	}

	public List<EObject> getSourceElements() {
		return sourceElements;
	}

	public Entity getTargetElement() {
		return targetElement;
	}

	public void setInvocationContext(MapperInvocationContext ctx) {
		this.mContext =ctx;
	}

	public MapperInvocationContext getContext() {
		return mContext;
	}

}
