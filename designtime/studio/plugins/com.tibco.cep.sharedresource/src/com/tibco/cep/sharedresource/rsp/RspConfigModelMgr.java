package com.tibco.cep.sharedresource.rsp;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;

import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.PersistenceUtil;

/*
@author ssailapp
@date Feb 22, 2010 7:25:13 PM
 */

public class RspConfigModelMgr extends SharedResModelMgr {
	private RspConfigModel model;
	private String resourceName;
	
	public RspConfigModelMgr(IProject project, RspConfigEditor editor) {
		super(project, editor);
	}
	
	public RspConfigModelMgr(IResource resource) {
		super(resource);
		this.resourceName = resource.getName();
	}

	@Override
	public RspConfigModel getModel() {
		return model;
	}

	@Override
	public LinkedHashMap<String,String> getPropertyNames() {
		return new LinkedHashMap<String, String>();
	}

	public void initModel() {
		String name = "";
		if (getEditor() != null) {
			name = getEditor().getEditorFileName();
		} else {
			name = resourceName;
		}
		name = name.replace(".sharedrsp", "");
		model = new RspConfigModel(name);
		model.values.put("resourceType", "ae.sharedresource.BERuleServiceProvider");
		model.values.put("repourl", "");
		model.values.put("description", "");
	}
	
	@Override
	public void parseModel() {
		initModel();
		RspConfigModelParser parser = new RspConfigModelParser();
		if (getFilePath() != null) {
			parser.loadModel(getFilePath(), this);
		} else if (getEditor().getDocument() != null) {
			parser.loadModel(getEditor().getDocument(), this);
		}
	}
	
	@Override
	public String saveModel() {
		Document doc = RspConfigModelParser.getSaveDocument(getRootAttributes(), "BWSharedResource");
		new RspConfigModelParser().saveModelParts(doc, this);
		String docString = PersistenceUtil.getDocumentString(doc);
		return docString;
	}
}
