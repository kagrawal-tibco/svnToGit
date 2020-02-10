package com.tibco.cep.studio.sb.sharedresource;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;

import com.tibco.cep.driver.sb.SBConstants;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.PersistenceUtil;

/*
@author ssailapp
@date Dec 28, 2009 1:11:33 PM
 */

public class SBConfigModelMgr extends SharedResModelMgr {

	private SBConfigModel model;
	private String resourceName;

	private String[] encodedKeys = { SBConstants.CHANNEL_PROPERTY_AUTH_PASSWORD.localName, SBConstants.CHANNEL_PROPERTY_KEY_PASSWORD.localName, SBConstants.CHANNEL_PROPERTY_KEY_STORE_PASSWORD.localName, SBConstants.CHANNEL_PROPERTY_TRUST_STORE_PASSWORD.localName };
	
	public SBConfigModelMgr(IProject project, SBConfigEditor editor) {
		super(project, editor);
	}
	
	public SBConfigModelMgr(IResource resource) {
		super(resource);
		this.resourceName = resource.getName();
	}
	
	@Override
	public SBConfigModel getModel() {
		return model;
	}
	
	@Override
	public LinkedHashMap<String, String> getPropertyNames() {
		LinkedHashMap<String, String> propertyNames = new LinkedHashMap<String, String>();
		propertyNames.put("description", "Description");
		return propertyNames;
	}

	public void initModel() {
		String name = "";
		if (getEditor() != null) {
			name = getEditor().getEditorFileName();
		} else {
			name = resourceName;
		}
		name = name.replace(".id", "");
		
		model = new SBConfigModel();
		model.values.put("name", name);
		model.values.put("description", "");
		model.values.put(SBConstants.CHANNEL_PROPERTY_SERVER_URI.localName, "");
		model.values.put(SBConstants.CHANNEL_PROPERTY_AUTH_USERNAME.localName, "");
		model.values.put(SBConstants.CHANNEL_PROPERTY_AUTH_PASSWORD.localName, "");
	}
		
	@Override
	public void parseModel() {
		initModel();
		SBConfigModelParser parser = new SBConfigModelParser();
		if (getFilePath() != null) {
			parser.loadModel(getFilePath(), this);
		} else if (getEditor().getDocument() != null){
			parser.loadModel(getEditor().getDocument(), this);
		}
		decodeFields(encodedKeys);
	}
	
	@Override
	public String saveModel() {
		encodeFields(encodedKeys);
		Document doc = SBConfigModelParser.getSaveDocument(getRootAttributes(), 
				"repository", "http://www.tibco.com/xmlns/repo/types/2002", "Repository:");
		new SBConfigModelParser().saveModelParts(doc, this);
		String docString = PersistenceUtil.getDocumentString(doc);
		return docString;
	}
}
