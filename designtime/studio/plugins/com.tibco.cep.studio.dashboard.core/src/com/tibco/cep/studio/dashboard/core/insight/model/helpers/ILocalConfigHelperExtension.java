package com.tibco.cep.studio.dashboard.core.insight.model.helpers;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;

public interface ILocalConfigHelperExtension extends ILocalConfigHelper{

	/**
	 * TODO: Check if really needed.
	 * Used by the FieldFormatType to create MDConfig of proper type.
	 * @param parent
	 * @param propHelper
	 * @return
	 * @throws Exception
	 */
	public abstract EObject createChild(LocalConfig parent,
			LocalPropertyConfig propHelper) throws Exception;

	/**
	 * @param parent
	 * @param path
	 * @return
	 */
	public abstract void setInstanceReferenceChild(LocalConfig parent, EObject eObject, String propertyName, LocalConfig child) throws Exception;
	
	
	
}
