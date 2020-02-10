package com.tibco.cep.sharedresource.jndi;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.w3c.dom.Document;

import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.PersistenceUtil;

/*
@author ssailapp
@date Dec 29, 2009 8:18:34 PM
 */

public class JndiConfigModelMgr extends SharedResModelMgr {
	public static String keys[] = new String[] { "JNDIPropNameCol", "JNDIPropTypeCol", "JNDIPropValueCol" };
	private String encodedKeys[] = new String[] { "SecurityCredentials" };
	
	private JndiConfigModel model;
	private String resourceName;
	
	public JndiConfigModelMgr(IProject project, JndiConfigEditor editor) {
		super(project, editor);
	}
	
	public JndiConfigModelMgr(IResource resource) {
		super(resource);
		this.resourceName = resource.getName();
	}

	public String getDefaultUrlForFactory(String factory) {
		return (JndiConfigModel.factoryUrl.get(factory));
	}

	public String[] getFactoryList() {
		return (JndiConfigModel.factoryUrl.keySet().toArray(new String[0]));	
	}
	
	@Override
	public JndiConfigModel getModel() {
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
		name = name.replace(".sharedjndiconfig", "");
		model = new JndiConfigModel(name);
		model.values.put("resourceType", "ae.shared.JNDISharedConfiguration");
		model.values.put("description", "");			
		model.values.put("ValidateJndiSecurityContext", "");
		model.values.put("ContextFactory", "");
		model.values.put("ProviderUrl", "");
		model.values.put("SecurityPrincipal", "");
		model.values.put("SecurityCredentials", "");
	}
	
	@Override
	public void parseModel() {
		initModel();
		JndiConfigModelParser parser = new JndiConfigModelParser();
		if (getFilePath() != null) {
			parser.loadModel(getFilePath(), this);
		} else if (getEditor().getDocument() != null) {
			parser.loadModel(getEditor().getDocument(), this);	
		}
		processModelElementTypes();
		decodeFields(encodedKeys);
	}
	
	public void processModelElementTypes() {
		String booleanKeys[] = new String[] {"ValidateJndiSecurityContext"};
		for (String key: booleanKeys) {
			String value = (String) model.values.get(key);
			model.values.put(key, new Boolean(value));
		}
	}
	
	@Override
	public String saveModel() {
		encodeFields(encodedKeys);
		Document doc = JndiConfigModelParser.getSaveDocument(getRootAttributes(), "BWSharedResource");
		new JndiConfigModelParser().saveModelParts(doc, this);
		decodeFields(encodedKeys);	// do this, so that the model always contains the decoded version of the values
		String docString = PersistenceUtil.getDocumentString(doc);
		return docString;
	}

	public void updateJndiProperties(Table table) {
		if (table == null)
			return;
		model.jndiProps.clear();
		for (TableItem item: table.getItems()) {
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			for (int col=0; col<table.getColumnCount(); col++)
				map.put(keys[col], item.getText(col));
			model.jndiProps.add(map);
		}
		modified();
	}
	
}
