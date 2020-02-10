package com.tibco.cep.sharedresource.jdbc;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;

import com.tibco.cep.sharedresource.jndi.JndiConfigModel;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.PersistenceUtil;

/*
@author ssailapp
@date Dec 29, 2009 11:37:02 PM
 */

public class JdbcConfigModelMgr extends SharedResModelMgr {

	private JdbcConfigModel model;
	private String resourceName;
	private String encodedKeys[] = new String[] { "password", "UserPassword" };
	
	public JdbcConfigModelMgr(IProject project, JdbcConfigEditor editor) {
		super(project, editor);
	}

	public JdbcConfigModelMgr(IResource resource) {
		super(resource);
		this.resourceName = resource.getName();
	}
	
	public String getDbPassword() {
		return (String) model.values.get("password");
	}
	
	public String getDefaultUrlForFactory(String factory) {
		return (JndiConfigModel.getFactoryUrls().get(factory));
	}

	public String[] getFactoryList() {
		return (JndiConfigModel.getFactoryUrls().keySet().toArray(new String[0]));
	}
	
	public String getJndiPassword() {
		return (String) model.values.get("UserPassword");
	}
	
	@Override
	public JdbcConfigModel getModel() {
		return model;
	}
	
	@Override
	public LinkedHashMap<String, String> getPropertyNames() {
		LinkedHashMap<String, String> propertyNames = new LinkedHashMap<String, String>();
		propertyNames.put("description", "Description");
		propertyNames.put("connectionType", "Connection Type");
		return propertyNames;
	}

	public void initModel() {
		String name = "";
		if (getEditor() != null) {
			name = getEditor().getEditorFileName();
		} else {
			name = resourceName;
		}
		name = name.replace(".sharedjdbc", "");
		model = new JdbcConfigModel(name);
		model.values.put("description", "");
		model.values.put("resourceType", "ae.shared.JDBCSharedResource");
		model.values.put("driver", "");
		model.values.put("maxConnections", "5");
		model.values.put("loginTimeout", "0");
		model.values.put("connectionType", "");
		model.values.put("UseSharedJndiConfig", "");
		model.values.put("location", "");
		model.values.put("user", "");
		model.values.put("password", "");
		model.values.put("ContextFactory", "");
		model.values.put("ProviderUrl", "");
		model.values.put("UserName", "");
		model.values.put("UserPassword", "");
		model.values.put("jndiDataSourceName", "");
		model.values.put("JndiSharedConfiguration", "");
		model.values.put("useSsl", "false");
		//model.values.put("xaDsClass", "");
	}
	
	
	@Override
	public void parseModel() {
		initModel();
		JdbcConfigModelParser parser = new JdbcConfigModelParser();
		if (getFilePath() != null) {
			parser.loadModel(getFilePath(), this);
		} else if (getEditor().getDocument() != null) {
			parser.loadModel(getEditor().getDocument(), this);
		}
		processModelElementTypes();
		decodeFields(encodedKeys);
	}
	
	public void processModelElementTypes() {
		String booleanKeys[] = new String[] { "UseSharedJndiConfig" };
		for (String key : booleanKeys) {
			String value = (String) model.values.get(key);
			model.values.put(key, new Boolean(value));
		}

		booleanKeys = new String[] { "useSsl" };
		for (String key : booleanKeys) {
			String value = (String) model.values.get(key);
			model.values.put(key, new Boolean(value));
		}
	}
	
	@Override
	public String saveModel() {
		encodeFields(encodedKeys);
		Document doc = JdbcConfigModelParser.getSaveDocument(getRootAttributes(), "BWSharedResource");
		new JdbcConfigModelParser().saveModelParts(doc, this);
		decodeFields(encodedKeys);	// do this, so that the model always contains the decoded version of the values
		String docString = PersistenceUtil.getDocumentString(doc);
		return docString;
	}
}
