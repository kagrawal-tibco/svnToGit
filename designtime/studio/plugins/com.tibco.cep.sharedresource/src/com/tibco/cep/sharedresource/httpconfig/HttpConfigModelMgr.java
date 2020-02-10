package com.tibco.cep.sharedresource.httpconfig;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;

import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.util.PersistenceUtil;

/*
@author ssailapp
@date Dec 22, 2009 4:01:17 AM
 */

public class HttpConfigModelMgr extends SharedResModelMgr {
	private HttpConfigModel model;
	
	public HttpConfigModelMgr(IProject project, HttpConfigEditor editor) {
		super(project, editor);
	}

	public HttpConfigModelMgr(IResource resource) {
		super(resource);
	}
	
	public HttpConfigModelMgr(String filePath) {
		super(filePath);
	}
	
	@Override
	public HttpConfigModel getModel() {
		return model;
	}

	@Override
	public LinkedHashMap<String, String> getPropertyNames() {
		LinkedHashMap<String, String> propertyNames = new LinkedHashMap<String, String>();
		propertyNames.put("description", "Description");
		propertyNames.put("Host", "Host");
		propertyNames.put("Port", "Port");
		return propertyNames;
	}

	public void initModel() {
		model = new HttpConfigModel();
		model.values.put("description", "");
		model.values.put("Host", "");
		model.values.put("Port", "");
		model.values.put("useSsl", "false");
	}
	
	@Override
	public void parseModel() {
		initModel();
		HttpConfigModelParser parser = new HttpConfigModelParser();
		if (getFilePath() != null) {
			parser.loadModel(getFilePath(), this);
		} else if (getEditor().getDocument() != null) {
			parser.loadModel(getEditor().getDocument(), this);
		}
		processModelElementTypes();
	}
	
	public void processModelElementTypes() {
		String booleanKeys[] = new String[] {"useSsl"};
		for (String key: booleanKeys) {
			String value = (String) model.values.get(key);
			if(!GvUtil.isGlobalVar(value)){
				model.values.put(key, new Boolean(value));
			}
		}
	}

	@Override
	public String saveModel() {
		Document doc = HttpConfigModelParser.getSaveDocument(getRootAttributes(), 
				"httpSharedResource", "www.tibco.com/shared/HTTPConnection", "ns0:");
		new HttpConfigModelParser().saveModelParts(doc, this);
		String docString = PersistenceUtil.getDocumentString(doc);
		return docString;
	}
}
