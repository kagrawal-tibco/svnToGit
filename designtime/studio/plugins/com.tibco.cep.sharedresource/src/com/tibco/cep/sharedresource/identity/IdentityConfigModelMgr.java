package com.tibco.cep.sharedresource.identity;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;

import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.PersistenceUtil;

/*
@author ssailapp
@date Dec 28, 2009 1:11:33 PM
 */

public class IdentityConfigModelMgr extends SharedResModelMgr {

	private IdentityConfigModel model;
	private String resourceName;

	private String[] encodedKeys = { "passPhrase", "password" };
	
	public IdentityConfigModelMgr(IProject project, IdentityConfigEditor editor) {
		super(project, editor);
	}
	
	public IdentityConfigModelMgr(IResource resource) {
		super(resource);
		this.resourceName = resource.getName();
	}
	
	@Override
	public IdentityConfigModel getModel() {
		return model;
	}
	
	@Override
	public LinkedHashMap<String, String> getPropertyNames() {
		LinkedHashMap<String, String> propertyNames = new LinkedHashMap<String, String>();
		propertyNames.put("description", "Description");
		propertyNames.put("objectType", "Type");
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
		
		model = new IdentityConfigModel();
		model.values.put("name", name);
		model.values.put("description", "");
		model.values.put("objectType", "usernamePassword");
		model.values.put("username", "");
		model.values.put("password", "");
		model.values.put("fileType", "");
		model.values.put("url", "");
		model.values.put("passPhrase", "");		
		model.values.put("certUrl", "");		
		model.values.put("privateKeyUrl", "");
	}
		
	@Override
	public void parseModel() {
		initModel();
		IdentityConfigModelParser parser = new IdentityConfigModelParser();
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
		Document doc = IdentityConfigModelParser.getSaveDocument(getRootAttributes(), 
				"repository", "http://www.tibco.com/xmlns/repo/types/2002", "Repository:");
		new IdentityConfigModelParser().saveModelParts(doc, this);
		String docString = PersistenceUtil.getDocumentString(doc);
		return docString;
	}
}
