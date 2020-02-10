package com.tibco.cep.decisionproject.persistence.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLResource.ResourceHandler;

import com.tibco.cep.decision.table.model.dtmodel.Table;

public class DecisionTableResourceHandler implements ResourceHandler {

	public static DecisionTableResourceHandler INSTANCE = new DecisionTableResourceHandler();
	
	public void postLoad(XMLResource resource, InputStream inputStream,
			Map<?, ?> options) {
		// TODO Auto-generated method stub

	}

	public void postSave(XMLResource resource, OutputStream outputStream,
			Map<?, ?> options) {
		// TODO Auto-generated method stub

	}

	public void preLoad(XMLResource resource, InputStream inputStream,
			Map<?, ?> options) {
		// TODO Auto-generated method stub

	}

	/*
	 * Before we save to an outputStream, attempt to demand load the decision tables
	 * for any Table objects
	 */
	public void preSave(XMLResource resource, OutputStream outputStream,
			Map<?, ?> options) {
		EList<EObject> contents = resource.getContents();
		for (EObject object : contents) {
			if (object instanceof Table) {
				((Table)object).getDecisionTable();
			}
		}
	}

}
