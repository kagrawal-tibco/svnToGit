package com.tibco.cep.sharedresource.jms;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.w3c.dom.Document;

import com.tibco.cep.sharedresource.jndi.JndiConfigModel;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.util.PersistenceUtil;

/*
@author ssailapp
@date Dec 31, 2009 12:05:24 AM
 */

public class JmsConModelMgr extends SharedResModelMgr {

	private JmsConModel model;
	protected String resourceName;
	
	public static String keys[] = new String[] { "Name", "Type", "Value" };
	private String encodedKeys[] = new String[] { "password", "AdmFactorySslPassword", "NamingCredential" }; 
	
	public JmsConModelMgr(IProject project, JmsConEditor editor) {
		super(project, editor);
	}
	
	public JmsConModelMgr(IResource resource) {
		super(resource);
		this.resourceName = resource.getName();
	}

	public String getDefaultUrlForFactory(String factory) {
		String url = JndiConfigModel.getFactoryUrls().get(factory);
		if (url != null)
			return url;
		return ("");
	}

	public String[] getFactoryList() {
		//return (JndiConfigModel.getFactoryUrls().keySet().toArray(new String[0]));
		return new String[] { 
			"com.tibco.tibjms.naming.TibjmsInitialContextFactory",
			"com.ibm.websphere.naming.WsnInitialContextFactory",
			"weblogic.jndi.WLInitialContextFactory",
			"com.sun.jndi.fscontext.RefFSContextFactory"
		};
	}
	
	@Override
	public JmsConModel getModel() {
		return model;
	}
	
	@Override
	public LinkedHashMap<String, String> getPropertyNames() {
		LinkedHashMap<String, String> propertyNames = new LinkedHashMap<String, String>();
		propertyNames.put("description", "Description");
		propertyNames.put("ProviderURL", "Provider URL");
		propertyNames.put("NamingURL", "Naming URL");
		return propertyNames;
	}
	
	public void initModel() {
		String name = "";
		if (getEditor() != null) {
			name = getEditor().getEditorFileName();
		} else {
			name = resourceName;
		}
		name = name.replace(".sharedjmscon", "");
		model = new JmsConModel(name);
		model.values.put("description", "");
		model.values.put("resourceType", "ae.shared.JMSConnectionKey");
		model.values.put("UseJNDI", "true");
		model.values.put("ProviderURL", "tcp://localhost:7222");
		model.values.put("NamingURL", "tibjmsnaming://localhost:7222");
		model.values.put("NamingInitialContextFactory", "com.tibco.tibjms.naming.TibjmsInitialContextFactory");
		model.values.put("TopicFactoryName", "TopicConnectionFactory");
		model.values.put("QueueFactoryName", "QueueConnectionFactory");
		model.values.put("NamingPrincipal", "");
		model.values.put("NamingCredential", "");
		model.values.put("username", "");
		model.values.put("password", "");
		model.values.put("clientID", "");
		model.values.put("autoGenClientID", "true");
		model.values.put("UseXACF", "false");
		model.values.put("useSsl", "false");
		model.values.put("UseSharedJndiConfig", "false");
		model.values.put("AdmFactorySslPassword", "");
		model.values.put("JndiSharedConfiguration", "");
	}
	
	@Override
	public void parseModel() {
		initModel();
		JmsConModelParser parser = new JmsConModelParser();
		if (getFilePath() != null) {
			parser.loadModel(getFilePath(), this);
		} else if (getEditor().getDocument() != null) {
			parser.loadModel(getEditor().getDocument(), this);	
		}
		processModelElementTypes();
		decodeFields(encodedKeys);
	}

	public void processModelElementTypes() {
		String booleanKeys[] = new String[] {"autoGenClientID", "useSsl", "UseJNDI", "UseXACF", "UseSharedJndiConfig"};
		for (String key: booleanKeys) {
			String value = (String) model.values.get(key);
			if (!GvUtil.isGlobalVar(value)) {
				model.values.put(key, new Boolean(value));
			}
		}
	}

	@Override
	public String saveModel() {
		encodeFields(encodedKeys);
		Document doc = JmsConModelParser.getSaveDocument(getRootAttributes(), "BWSharedResource");
		new JmsConModelParser().saveModelParts(doc, this);
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
